package com.example.catatanapps

import android.content.ContentValues
import android.content.Intent
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
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import com.example.catatanapps.databinding.FragmentNoteAddUpdateBinding
import com.example.catatanapps.db.DatabaseContract
import com.example.catatanapps.db.NoteHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteAddUpdateFragment : Fragment(), View.OnClickListener {

    private var _binding : FragmentNoteAddUpdateBinding ?= null
    private val binding get() = _binding!!

    private var isEdit = false
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

        noteHelper = NoteHelper.getInstance(requireContext())
        noteHelper.open()

        notes = arguments?.getParcelable(EXTRA_NOTE)

        if (notes != null) {
            position = arguments?.getInt(EXTRA_POSITION, 0) ?: 0
            isEdit = true
        } else {
            notes = Notes()
        }

        val actionBarTitle : String
        var btnTitle : String

        if (isEdit) {
            actionBarTitle = "Ubah"
            btnTitle = "Update"

            notes?.let {
                binding.edtTitle.setText(it.title)
                binding.edtDescription.setText(it.description)
            }

        } else {
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
        }

        binding.btnSubmit.text = btnTitle

        requireActivity().title = actionBarTitle

//      Digunakan untuk Menu
        requireActivity().addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                if (isEdit) {
                    menuInflater.inflate(R.menu.menu_form, menu)
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {

                    R.id.acDelete -> {
                        val result = noteHelper.deleteById(notes?.id.toString())
                        if (result > 0) {
                            val bundle = Bundle().apply {
                                putParcelable(EXTRA_NOTE, notes)
                                putInt(EXTRA_POSITION, position)
                                putInt("type", RESULT_DELETE)
                            }

                            parentFragmentManager.setFragmentResult("note_result", bundle)
                            parentFragmentManager.popBackStack()
                        }
                        true
                    }

                    else -> false
                }
            }

        }, viewLifecycleOwner)

//        Digunakan untuk onBackPressed
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showAlertDialog(ALERT_DIALOG_CLOSE)
                }

                private fun showAlertDialog(type: Int) {
                    val isDialogClose = type == ALERT_DIALOG_CLOSE
                    val dialogTitle : String
                    val dialogMessage : String

                    if (isDialogClose) {
                        dialogTitle = "Batal"
                        dialogMessage = "Apakah anda ingin membatalkan ?"
                    } else {
                        dialogMessage = "Apakah anda ingin menghapus item ?"
                        dialogTitle = "Hapus Note"
                    }

                    val alertDialogBuilder = AlertDialog.Builder(requireContext())

                    alertDialogBuilder.setTitle(dialogTitle)
                    alertDialogBuilder
                        .setMessage(dialogMessage)
                        .setCancelable(false)
                        .setPositiveButton("Ya") { _, _ ->
                            if (isDialogClose) {
                                parentFragmentManager.popBackStack()
                            } else {
                                val result = noteHelper.deleteById(notes?.id.toString()).toLong()
                                if (result > 0) {
                                    val bundle = Bundle().apply {
                                        putParcelable(EXTRA_NOTE, notes)
                                        putInt(EXTRA_POSITION, position)
                                        putInt("type", RESULT_DELETE)
                                    }

                                    parentFragmentManager.setFragmentResult("note_result", bundle)
                                    parentFragmentManager.popBackStack()

                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Gagal menghapus data",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                        .setNegativeButton("Tidak") { dialog, _ ->
                            dialog.dismiss()
                        }
                    alertDialogBuilder.create().show()
                }
            }
        )
    }

    override fun onClick(view: View?) {
        val title = binding.edtTitle.text.toString().trim()
        val description = binding.edtDescription.text.toString().trim()

        if (title.isEmpty()) {
            binding.edtTitle.error = "Title tidak boleh kosong"
            return
        }

        notes?.title = title
        notes?.description = description

        val values = ContentValues().apply {
            put(DatabaseContract.NoteColums.TITLE, title)
            put(DatabaseContract.NoteColums.DESCRIPTION, description)
        }

        val bundle = Bundle()
        if (isEdit) {
            val result = noteHelper.update(notes?.id.toString(), values)
            if (result > 0 ) {
                bundle.putParcelable(EXTRA_NOTE, notes)
                bundle.putInt(EXTRA_POSITION, position)
                bundle.putInt("type", RESULT_UPDATE)
            }
        }  else {

            val currentDate = getCurrentDate()
            notes?.date = currentDate
            values.put(DatabaseContract.NoteColums.DATE, currentDate)

            val result = noteHelper.insert(values)

            if (result > 0) {
                notes?.id = result.toInt()
                notes?.let {
                    bundle.putParcelable(EXTRA_NOTE, it)
                }
                bundle.putInt("type", RESULT_ADD)
            }
        }

        parentFragmentManager.setFragmentResult("note_result", bundle)
        parentFragmentManager.popBackStack()
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        return dateFormat.format(Date())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}