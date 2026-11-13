package com.makuta.simplenotes.fragment.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.makuta.simplenotes.MainActivity
import com.makuta.simplenotes.NotesApp.Companion.BUNDLE_FILE
import com.makuta.simplenotes.R
import com.makuta.simplenotes.Utils.gone
import com.makuta.simplenotes.Utils.visible
import com.makuta.simplenotes.adapter.NotesAdapter
import com.makuta.simplenotes.databinding.DRenameBinding
import com.makuta.simplenotes.databinding.FMainBinding
import com.makuta.simplenotes.db.Note

class FMain : Fragment(), NotesAdapter.OnNoteActionListener, PopupMenu.OnMenuItemClickListener {

    private val vm: MainVM by viewModels()
    private lateinit var binding: FMainBinding
    private lateinit var notesAdapter: NotesAdapter
    private var currentFilename: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar?.setTitle(R.string.app_name)
        notesAdapter = NotesAdapter(this)

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = notesAdapter

        binding.fabNew.setOnClickListener {
            findNavController().navigate(
                R.id.action_FMain_to_FEditor,
                bundleOf(BUNDLE_FILE to "")
            )
        }
    }

    override fun onStart() {
        super.onStart()

        vm.notes.observe(viewLifecycleOwner) { notes ->
            if (!notes.isNullOrEmpty()) {
                notesAdapter.addData(notes)
                binding.list.visible()
                binding.empty.gone()
            } else {
                notesAdapter.clear()
                binding.list.gone()
                binding.empty.visible()
            }
        }

        vm.sharedNote.observe(viewLifecycleOwner) { shared ->
            if (shared != null) {
                vm.consumeSharedNote()
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, shared.title)
                    putExtra(Intent.EXTRA_TEXT, shared.content)
                }
                startActivity(Intent.createChooser(intent, getString(R.string.share_using)))
            }
        }

        vm.shareError.observe(viewLifecycleOwner) { failed ->
            if (failed == true) {
                Toast.makeText(requireContext(), R.string.err_share, Toast.LENGTH_SHORT).show()
            }
        }

        vm.load()
    }

    override fun onStop() {
        super.onStop()
        vm.notes.removeObservers(viewLifecycleOwner)
        vm.sharedNote.removeObservers(viewLifecycleOwner)
        vm.shareError.removeObservers(viewLifecycleOwner)
    }

    override fun onClick(note: Note) {
        findNavController().navigate(
            R.id.action_FMain_to_FEditor,
            bundleOf(BUNDLE_FILE to note.filename)
        )
    }

    override fun onLongClick(note: Note, view: View) {
        currentFilename = note.filename
        val popupMenu = PopupMenu(requireContext(), view)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true)
        }
        popupMenu.inflate(R.menu.menu_popup)
        popupMenu.setOnMenuItemClickListener(this)
        popupMenu.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        val filename = currentFilename ?: return false

        when (item?.itemId) {
            R.id.menu_del -> {
                vm.delete(filename)
                notesAdapter.del(filename)
                return true
            }

            R.id.menu_share -> {
                vm.share(filename)
                return true
            }

            R.id.menu_rename -> {
                val dialogBinding = DRenameBinding.inflate(requireActivity().layoutInflater)
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(R.string.title_rename)
                    .setView(dialogBinding.root)
                    .setPositiveButton(R.string.dialog_rename) { dialog, _ ->
                        val title = dialogBinding.editor.text?.toString()?.trim().orEmpty()
                        if (title.isNotEmpty()) {
                            vm.rename(filename, title)
                            dialog.dismiss()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                R.string.err_empty,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    .setNegativeButton(R.string.dialog_cancel) { dialog, _ -> dialog.dismiss() }
                    .show()
                return true
            }
        }
        return false
    }
}
