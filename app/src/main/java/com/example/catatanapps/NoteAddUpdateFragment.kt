package com.example.catatanapps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.catatanapps.databinding.FragmentNoteAddUpdateBinding
import com.example.catatanapps.db.NoteHelper

class NoteAddUpdateFragment : Fragment() {

    private var _binding : FragmentNoteAddUpdateBinding ?= null
    private val binding get() = _binding!!

    private var ieEdit = false
    private var notes : Notes ?= null
    private var position : Int = 0
    private lateinit var noteHelper: NoteHelper

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteAddUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}