package com.makuta.simplenotes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makuta.simplenotes.db.Note
import com.makuta.simplenotes.databinding.INoteBinding

class NotesAdapter(
    private val listener: OnNoteActionListener? = null
) : RecyclerView.Adapter<NotesAdapter.NotesHolder>() {

    private val data = ArrayList<Note>()

    fun addData(items: List<Note>) {
        data.clear()
        data.addAll(items)
        // The list may grow from zero items. A range-changed notification does
        // not announce newly inserted rows, so refresh the adapter's data set.
        notifyDataSetChanged()
    }

    fun del(filename: String) {
        val index = data.indexOfFirst { it.filename == filename }
        if (index >= 0) {
            data.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun clear() {
        val count = data.size
        data.clear()
        if (count > 0) notifyItemRangeRemoved(0, count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NotesHolder(INoteBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        val note = data[position]
        holder.text.text = note.title

        if (listener != null) {
            holder.root.setOnClickListener {
                val current = data.getOrNull(holder.bindingAdapterPosition) ?: return@setOnClickListener
                listener.onClick(current)
            }
            holder.root.setOnLongClickListener {
                val current = data.getOrNull(holder.bindingAdapterPosition) ?: return@setOnLongClickListener true
                listener.onLongClick(current, holder.root)
                true
            }
        } else {
            holder.root.setOnClickListener(null)
            holder.root.setOnLongClickListener(null)
        }
    }

    override fun getItemCount(): Int = data.size

    class NotesHolder(binding: INoteBinding) : RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val text = binding.title
    }

    interface OnNoteActionListener {
        fun onClick(note: Note)
        fun onLongClick(note: Note, view: View)
    }
}
