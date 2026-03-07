package com.example.roomapps.ui.insert

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.roomapps.R
import com.example.roomapps.database.Note
import com.example.roomapps.databinding.FragmentNoteAddUpdateBinding
import com.example.roomapps.helper.DateHelper
import com.example.roomapps.helper.ViewModelFactory

class NoteAddUpdateFragment : Fragment() {

    private var _binding : FragmentNoteAddUpdateBinding ?= null
    private val binding get() = _binding !!

    lateinit var noteAddUpdateViewModel : NoteAddUpdateViewModel

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 10
    }

    private var isEdit = false
    private var note : Note?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteAddUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteAddUpdateViewModel = obtainViewModel(requireContext())

        note = arguments?.getParcelable(EXTRA_NOTE)

        if (note != null) {
            isEdit = true
        } else {
            note = Note()
        }

        val actionBarTitle : String
        val btnTitle : String

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)

            note?.let { note ->
                binding.edtTitle.setText(note.title)
                binding.edtDescription.setText(note.description)
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        activity?.title = actionBarTitle
        binding.btnSubmit.text = btnTitle

        binding?.btnSubmit?.setOnClickListener {
            val title = binding?.edtTitle?.text.toString    ().trim()
            val description = binding?.edtDescription?.text.toString().trim()

            when {

                title.isEmpty() -> {
                    binding?.edtTitle?.error = getString(R.string.empty)
                }

                description.isEmpty() -> {
                    binding?.edtDescription?.error = getString(R.string.empty)
                }

                else -> {
                    note?.let {
                        it.title = title
                        it.description = description
                    }

                    if (isEdit) {
                        noteAddUpdateViewModel.update(note as Note)
                        showToast(getString(R.string.changed))
                    } else {
                        note?.date = DateHelper.getCurrentDate()
                        noteAddUpdateViewModel.insert(note as Note)
                        showToast(getString(R.string.added))
                    }

                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(requireContext: Context): NoteAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        return ViewModelProvider(this, factory).get(NoteAddUpdateViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}