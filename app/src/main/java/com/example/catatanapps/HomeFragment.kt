package com.example.catatanapps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catatanapps.adapterrecyclerview.NoteAdapter
import com.example.catatanapps.databinding.FragmentHomeBinding
import com.example.catatanapps.db.NoteHelper
import com.example.catatanapps.helper.MappingHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NoteAdapter

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = "Notes"

        setupRecyclerView()
        setupFab()
        setupFragmentResult()

        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            val list =
                savedInstanceState.getParcelableArrayList<Notes>(EXTRA_STATE)
            if (list != null) {
                adapter.listNotes = list
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = NoteAdapter(object : NoteAdapter.OnItemClickCallBack {
            override fun onItemClicked(selectedNote: Notes?, position: Int?) {

                val bundle = Bundle().apply {
                    putParcelable(
                        NoteAddUpdateFragment.EXTRA_NOTE,
                        selectedNote
                    )
                    putInt(
                        NoteAddUpdateFragment.EXTRA_POSITION,
                        position ?: 0
                    )
                }

                findNavController().navigate(
                    R.id.action_homeFragment_to_noteAddUpdateFragment,
                    bundle
                )
            }
        })

        binding.rvNotes.layoutManager =
            LinearLayoutManager(requireContext())
        binding.rvNotes.setHasFixedSize(true)
        binding.rvNotes.adapter = adapter
    }

    private fun setupFab() {
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_noteAddUpdateFragment
            )
        }
    }

    private fun setupFragmentResult() {
        parentFragmentManager.setFragmentResultListener(
            "note_result",
            viewLifecycleOwner
        ) { _, bundle ->

            val type = bundle.getInt("type")

            when (type) {

                NoteAddUpdateFragment.RESULT_ADD -> {
                    val note = bundle.getParcelable<Notes>(
                        NoteAddUpdateFragment.EXTRA_NOTE
                    )!!
                    adapter.addItem(note)
                    binding.rvNotes.smoothScrollToPosition(
                        adapter.itemCount - 1
                    )
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }

                NoteAddUpdateFragment.RESULT_UPDATE -> {
                    val note = bundle.getParcelable<Notes>(
                        NoteAddUpdateFragment.EXTRA_NOTE
                    )!!
                    val position = bundle.getInt(
                        NoteAddUpdateFragment.EXTRA_POSITION
                    )
                    adapter.updateItem(position, note)
                    binding.rvNotes.smoothScrollToPosition(position)
                    showSnackbarMessage("Satu item berhasil diubah")
                }

                NoteAddUpdateFragment.RESULT_DELETE -> {
                    val position = bundle.getInt(
                        NoteAddUpdateFragment.EXTRA_POSITION
                    )
                    adapter.removeItem(position)
                    showSnackbarMessage("Satu item berhasil dihapus")
                }
            }
        }
    }

    private fun loadNotesAsync() {
        viewLifecycleOwner.lifecycleScope.launch {

            binding.progressBar.visibility = View.VISIBLE

            val noteHelper = NoteHelper.getInstance(requireContext())
            noteHelper.open()

            val notes = withContext(Dispatchers.IO) {
                val cursor = noteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }

            binding.progressBar.visibility = View.GONE

            if (notes.isNotEmpty()) {
                adapter.listNotes = notes
            } else {
                adapter.listNotes = ArrayList()
                showSnackbarMessage("Tidak ada data !!")
            }

            noteHelper.close()
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvNotes, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listNotes)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

