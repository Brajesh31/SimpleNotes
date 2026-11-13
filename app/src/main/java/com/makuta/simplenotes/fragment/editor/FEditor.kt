package com.makuta.simplenotes.fragment.editor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.makuta.simplenotes.MainActivity
import com.makuta.simplenotes.NotesApp.Companion.BUNDLE_FILE
import com.makuta.simplenotes.R
import com.makuta.simplenotes.databinding.DRenameBinding
import com.makuta.simplenotes.databinding.FEditBinding

class FEditor : Fragment(), MenuProvider {

    private val vm: EditorVM by viewModels()
    private var noteFilename = ""
    private lateinit var binding: FEditBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
        noteFilename = requireArguments().getString(BUNDLE_FILE, "")
        if (noteFilename.isEmpty()) {
            (requireActivity() as MainActivity).supportActionBar?.title = getString(R.string.title_new)
        } else {
            vm.load(noteFilename)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_edit, menu)
        if (noteFilename.isEmpty()) {
            menu.findItem(R.id.menu_share).isVisible = false
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_share -> {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, vm.note.value?.title.orEmpty())
                    putExtra(Intent.EXTRA_TEXT, binding.content.text.toString())
                }
                startActivity(Intent.createChooser(intent, getString(R.string.share_using)))
                return true
            }

            R.id.menu_save -> {
                if (noteFilename.isNotEmpty()) {
                    vm.update(noteFilename, binding.content.text.toString())
                } else {
                    val dialogBinding = DRenameBinding.inflate(requireActivity().layoutInflater)
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(R.string.title_save_as)
                        .setView(dialogBinding.root)
                        .setPositiveButton(R.string.dialog_save) { dialog, _ ->
                            val title = dialogBinding.editor.text?.toString()?.trim().orEmpty()
                            if (title.isNotEmpty()) {
                                vm.create(title, binding.content.text.toString())
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
                }
                return true
            }
        }
        return false
    }

    override fun onStart() {
        super.onStart()

        vm.note.observe(viewLifecycleOwner) { note ->
            if (note != null) {
                (requireActivity() as MainActivity).supportActionBar?.title = note.title
            }
        }

        vm.content.observe(viewLifecycleOwner) { content ->
            if (content != null) {
                binding.content.setText(content)
            }
        }

        vm.loadError.observe(viewLifecycleOwner) { failed ->
            if (failed == true) {
                Toast.makeText(requireContext(), R.string.err_load_note, Toast.LENGTH_SHORT).show()
            }
        }

        vm.operation.observe(viewLifecycleOwner) { succeeded ->
            if (succeeded == true) {
                findNavController().popBackStack()
            } else if (succeeded == false) {
                Toast.makeText(requireContext(), R.string.err_save_note, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        vm.note.removeObservers(viewLifecycleOwner)
        vm.content.removeObservers(viewLifecycleOwner)
        vm.loadError.removeObservers(viewLifecycleOwner)
        vm.operation.removeObservers(viewLifecycleOwner)
    }
}
