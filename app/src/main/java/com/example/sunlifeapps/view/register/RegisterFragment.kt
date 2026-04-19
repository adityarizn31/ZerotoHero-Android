@file:Suppress("DEPRECATION")

package com.example.sunlifeapps.view.register


import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.sunlifeapps.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding : FragmentRegisterBinding ?= null
    private val binding get() = _binding !!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
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
        binding.signupButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()

            AlertDialog.Builder(requireActivity()).apply {
                setTitle("Skut")
                setMessage("Akun dengan $email sudah jadi nich. Skut Login")
                setPositiveButton("Gaskeun") { _, _ ->
                    requireActivity().onBackPressedDispatcher.onBackPressed()
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