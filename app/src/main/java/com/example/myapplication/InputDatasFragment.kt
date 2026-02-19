package com.example.myapplication

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentInputDatasBinding
import androidx.core.text.isDigitsOnly

class InputDatasFragment : Fragment(), View.OnClickListener {

    private var _binding : FragmentInputDatasBinding ?= null
    private val binding get() = _binding!!

    private lateinit var userModel: UserModel

    companion object {

        const val EXTRA_TYPE_FORM = "extra_type_form"
        const val EXTRA_RESULT = "extra_result"
        const val RESULT_CODE = 101

        const val TYPE_ADD = 1
        const val TYPE_EDIT = 2

        private const val FIELD_REQUIRED = "Field tidak boleh kosong"
        private const val FIELD_DIGIT_ONLY = "Hanya boleh terisi angka numerik"
        private const val FIELD_IS_NOT_VALID = "Email tidak valid"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInputDatasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener (this)

        userModel = arguments?.getParcelable("USER") ?: UserModel()
        val formType = arguments?.getInt("extra_type_form", 0)

        var actionBarTitle = ""
        var btnTitle = ""

        when (formType) {
            TYPE_ADD -> {
                actionBarTitle = "Tambah Baru"
                btnTitle = "Simpan"
            }
            TYPE_EDIT -> {
                actionBarTitle = "Ubah"
                btnTitle = "Update"
                showPreferenInForm()
            }
        }

        requireActivity().title = actionBarTitle
        binding.btnSave.text = btnTitle

        binding.btnReset.setOnClickListener {
            val pref = UserPreference(requireContext())
            pref.clearUser()

            binding.edtName.setText("")
            binding.edtEmail.setText("")
            binding.edtAge.setText("")
            binding.edtPhone.setText("")
            binding.rgLoveNzull.clearCheck()

            binding.btnSave.text = getString(R.string.save  )
        }
    }

    private fun showPreferenInForm() {
        binding.edtName.setText(userModel.name)
        binding.edtEmail.setText(userModel.email)
        binding.edtAge.setText(userModel.age.toString())
        binding.edtPhone.setText(userModel.phoneNumber)

        if (userModel.isLove) {
            binding.rbYes.isChecked = true
        } else {
            binding.rbNo.isChecked = true
        }
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btnSave) {
            val name = binding.edtName.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val age = binding.edtAge.text.toString().trim()
            val phoneNo = binding.edtPhone.text.toString().trim()
            val isLoveNzul = binding.rgLoveNzull.checkedRadioButtonId == R.id.rbYes

            if (name.isEmpty()) {
                binding.edtName.error = FIELD_REQUIRED
                return
            }

            if (email.isEmpty()) {
                binding.edtEmail.error = FIELD_REQUIRED
                return
            }

            if (age.isEmpty()) {
                binding.edtAge.error = FIELD_REQUIRED
                return
            }

            if (phoneNo.isEmpty()) {
                binding.edtPhone.error = FIELD_REQUIRED
                return
            }
            if (!phoneNo.isDigitsOnly()) {
                binding.edtPhone.error = FIELD_REQUIRED
                return
            }

            saveUser(name, email, age, phoneNo, isLoveNzul)

            parentFragmentManager.setFragmentResult(EXTRA_RESULT, bundleOf(EXTRA_RESULT to userModel))
            findNavController().popBackStack()
        }
    }

    private fun saveUser(
        name: String,
        email: String,
        age: String,
        phoneNo: String,
        loveNzul: Boolean
    ) {
        val userPreference = UserPreference(requireContext())

        userModel.name = name
        userModel.email = email
        userModel.age = Integer.parseInt(age)
        userModel.phoneNumber = phoneNo
        userModel.isLove = loveNzul

        userPreference.setUser(userModel)
        Toast.makeText(requireContext(), "Data Tersimpan", Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(email : CharSequence) : Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}