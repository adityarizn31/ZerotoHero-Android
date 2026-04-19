package com.example.sunlifeapps.view.login

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sunlifeapps.R
import com.example.sunlifeapps.data.pref.UserModel
import com.example.sunlifeapps.databinding.FragmentLoginBinding
import com.example.sunlifeapps.view.ViewModelFactory

class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding ?= null
    private val binding get() = _binding !!

    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupAction()
    }

    private fun setupView() {
        val activity = requireActivity()

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        (activity as? androidx.appcompat.app.AppCompatActivity)
            ?.supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()

            viewModel.saveSession(UserModel(email, "sample_token"))

            AlertDialog.Builder(requireContext()).apply {
                setTitle("Yeah!")
                setMessage("Anda berhasil login. Sudah tidak sabar untuk belajar ya?")
                setPositiveButton("Lanjut") { _, _ ->
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                create()
                show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}