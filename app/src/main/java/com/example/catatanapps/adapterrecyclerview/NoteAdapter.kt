package com.example.catatanapps.adapterrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catatanapps.Notes
import com.example.catatanapps.R
import com.example.catatanapps.adapterrecyclerview.NoteAdapter.NoteViewHolder
import com.example.catatanapps.databinding.ItemNoteBinding
import java.security.interfaces.RSAPrivateCrtKey

class NoteAdapter (private val onItemCallback: OnItemClickCallBack) : RecyclerView.Adapter<NoteViewHolder>() {

    var listNotes = ArrayList<Notes>()
        set(listNotes) {
            if (listNotes.size > 0) {
                this.listNotes.clear()
            }
            this.listNotes.addAll(listNotes)
        }

    inner class NoteViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNoteBinding.bind(itemView)
        fun bind(notes: Notes) {
            binding.tvItemTitle.text = notes.title
            binding.tvItemDescription.text = notes.description
            binding.tvItemDate.text = notes.date

            binding.cvItemNote.setOnClickListener {
                onItemCallback.onItemClicked(notes, adapterPosition)
            }
        }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(selectedNote : Notes?, position: Int?)
    }

    fun addItem(notes: Notes) {
        this.listNotes.add(notes)
        notifyItemInserted(this.listNotes.size -1)
    }

    fun updateItem(position: Int, notes: Notes) {
        this.listNotes[position] = notes
        notifyItemChanged(position, notes)
    }

    fun removeItem(position: Int) {
        this.listNotes.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listNotes.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int = this.listNotes.size

}