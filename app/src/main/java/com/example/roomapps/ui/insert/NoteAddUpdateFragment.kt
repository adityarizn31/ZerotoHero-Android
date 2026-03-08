package com.example.roomapps.ui.insert

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.example.roomapps.R
import com.example.roomapps.database.Note
import com.example.roomapps.databinding.FragmentNoteAddUpdateBinding
import com.example.roomapps.helper.DateHelper
import com.example.roomapps.helper.ViewModelFactory

class NoteAddUpdateFragment : Fragment() {

    private var _binding: FragmentNoteAddUpdateBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteAddUpdateViewModel: NoteAddUpdateViewModel

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    private var isEdit = false
    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)

            note?.let {
                binding.edtTitle.setText(it.title)
                binding.edtDescription.setText(it.description)
            }

        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        activity?.title = actionBarTitle
        binding.btnSubmit.text = btnTitle

        binding.btnSubmit.setOnClickListener {

            val title = binding.edtTitle.text.toString().trim()
            val description = binding.edtDescription.text.toString().trim()

            when {

                title.isEmpty() -> {
                    binding.edtTitle.error = getString(R.string.empty)
                }

                description.isEmpty() -> {
                    binding.edtDescription.error = getString(R.string.empty)
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

        // Handle tombol back HP
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showAlertDialog(ALERT_DIALOG_CLOSE)
                }
            }
        )

        // Aktifkan menu di Fragment
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (isEdit) {
            inflater.inflate(R.menu.main_menu, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.acDelete -> {
                showAlertDialog(ALERT_DIALOG_DELETE)
            }

            android.R.id.home -> {
                showAlertDialog(ALERT_DIALOG_CLOSE)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(type: Int) {

        val isDialogClose = type == ALERT_DIALOG_CLOSE

        val dialogTitle: String
        val dialogMessage: String

        if (isDialogClose) {

            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)

        } else {

            dialogTitle = getString(R.string.delete)
            dialogMessage = getString(R.string.message_delete)
        }

        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        with(alertDialogBuilder) {

            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)

            setPositiveButton(getString(R.string.yes)) { _, _ ->

                if (!isDialogClose) {

                    noteAddUpdateViewModel.delete(note as Note)
                    showToast(getString(R.string.deleted))
                }

                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

            setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.cancel()
            }
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(context: Context): NoteAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        return ViewModelProvider(this, factory)[NoteAddUpdateViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}