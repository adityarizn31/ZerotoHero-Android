package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentDatasBinding

class DatasFragment : Fragment() {

    private var _binding : FragmentDatasBinding ?= null
    private val binding = _binding!!

    private lateinit var mUserPreference : UserPreference
    private var isPreferenceEmpty = false
    private lateinit var userModel: UserModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDatasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = "My User Preference"
        mUserPreference = UserPreference(requireContext())
        showExistingPreference()
    }

    override fun onResume() {
        super.onResume()
        showExistingPreference()
    }

    private fun showExistingPreference() {
        userModel = mUserPreference.getUser()
        populateView(userModel)
        checkForm(userModel)
    }

    private fun populateView(userModel: UserModel) {
        binding.tvName.text =
            if (userModel.name.isNullOrEmpty()) "Tidak ada" else userModel.name

        binding.tvEmail.text =
            if (userModel.email.isNullOrEmpty()) "Tidak ada" else userModel.email

        binding.tvAge.text =
            if (userModel.age == 0) "Tidak ada" else userModel.age.toString()

        binding.tvNoHp.text =
            if (userModel.phoneNumber.isNullOrEmpty()) "Tidak ada" else userModel.phoneNumber

        binding.tvNzul.text =
            if (userModel.isLove) "Ya" else "Tidak"

    }

    private fun checkForm(userModel: UserModel) {
        if (!userModel.name.isNullOrEmpty()) {
            binding.btnSimpan.text = requireContext().getString(R.string.change)
            isPreferenceEmpty = false
        } else {
            binding.btnSimpan.text = requireContext().getString(R.string.save)
            isPreferenceEmpty = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}