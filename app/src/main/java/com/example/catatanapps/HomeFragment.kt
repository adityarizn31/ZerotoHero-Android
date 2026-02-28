package com.example.catatanapps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catatanapps.adapterrecyclerview.NoteAdapter
import com.example.catatanapps.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding ?= null
    private val binding get() = _binding!!

    private lateinit var adapter : NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = "Notes"
        binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotes.setHasFixedSize(true)

        parentFragmentManager.setFragmentResultListener(
            "note_result",
            viewLifecycleOwner
        ) { _, bundle ->
            val type = bundle.getInt("type")
            when (type) {

                NoteAddUpdateFragment.RESULT_ADD -> {
                    val note = bundle.getParcelable<Notes>(NoteAddUpdateFragment.EXTRA_NOTE)!!
                    adapter.addItem(note)
                    binding.rvNotes.smoothScrollToPosition(adapter.itemCount -1)
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }

                NoteAddUpdateFragment.RESULT_UPDATE -> {
                    val note = bundle.getParcelable<Notes>(NoteAddUpdateFragment.EXTRA_NOTE)!!
                    val position = bundle.getInt(NoteAddUpdateFragment.EXTRA_POSITION)
                    adapter.updateItem(position, note)
                    binding.rvNotes.smoothScrollToPosition(position)
                    showSnackbarMessage("Satu item berhasil diubah")
                }

                NoteAddUpdateFragment.RESULT_DELETE -> {
                    val position = bundle.getInt(NoteAddUpdateFragment.EXTRA_POSITION)
                    adapter.removeItem(position)
                    showSnackbarMessage("Satu item berhasil dihapus")
                }

            }

//            for add item only
            binding.fabAdd.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, NoteAddUpdateFragment())
                    .addToBackStack(null)
                    .commit()
            }

//            for edit item only
            adapter = NoteAdapter(object : NoteAdapter.OnItemClickCallBack {
                override fun onItemClicked(
                    selectedNote: Notes?,
                    position: Int?
                ) {
                    val fragment = NoteAddUpdateFragment().apply {
                        arguments = Bundle().apply {
                            putParcelable(NoteAddUpdateFragment.EXTRA_NOTE, selectedNote)
                            putInt(NoteAddUpdateFragment.EXTRA_POSITION, position ?: 0)
                        }
                    }

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment)
                        .addToBackStack(null)
                        .commit()
                }

            })

        }
    }

    private fun HomeFragment.showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvNotes, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

