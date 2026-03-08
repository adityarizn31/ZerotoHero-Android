package com.example.roomapps.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapps.R
import com.example.roomapps.database.Note
import com.example.roomapps.databinding.ItemNoteBinding
import com.example.roomapps.helper.NoteDiffCallback
import com.example.roomapps.ui.insert.NoteAddUpdateFragment

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){

    private val listNotes = ArrayList<Note>()

    fun setListNotes(ListNotes : List<Note>) {
        val diffCallback = NoteDiffCallback(this.listNotes, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            with(binding) {
                tvTitle.text = note.title
                tvDate.text = note.date
                tvDesc.text = note.description
                cvItemNote.setOnClickListener {
                    val fragment = NoteAddUpdateFragment()

                    val bundle = Bundle()
                    bundle.putParcelable(NoteAddUpdateFragment.EXTRA_NOTE, note)

                    fragment.arguments = bundle

                    val fragmentManager = (it.context as AppCompatActivity).supportFragmentManager

                    fragmentManager.beginTransaction()
                        .replace(R.id.main_navigation, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }
}