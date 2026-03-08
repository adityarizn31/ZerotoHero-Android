package com.example.roomapps.ui.insert

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomapps.R
import com.example.roomapps.databinding.FragmentHomeBinding
import com.example.roomapps.helper.ViewModelFactory
import com.example.roomapps.ui.main.MainViewModel
import com.example.roomapps.ui.main.NoteAdapter

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding ?= null
    private val binding get() = _binding!!

    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NoteAdapter()

        binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotes.setHasFixedSize(true)
        binding.rvNotes.adapter = adapter

        val mainViewModel = obtainViewModel()
        mainViewModel.getAllNotes().observe(viewLifecycleOwner) { noteList ->
            if (noteList != null) {
                adapter.setListNotes(noteList)
            }
        }

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_noteAddUpdateFragment
            )
        }
    }

    private fun obtainViewModel() : MainViewModel {
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        return ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}