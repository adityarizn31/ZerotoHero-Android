package com.example.myapplication

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.text.isDigitsOnly
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentInputDatasBinding

class InputDatasFragment : Fragment() {

    // =========================
    // Properties
    // =========================

    private var _binding: FragmentInputDatasBinding? = null
    private val binding get() = _binding!!

    private lateinit var userModel: UserModel

    // =========================
    // Constants
    // =========================

    companion object {
        const val EXTRA_TYPE_FORM = "extra_type_form"
        const val EXTRA_RESULT = "extra_result"

        const val TYPE_ADD = 1
        const val TYPE_EDIT = 2

        private const val FIELD_REQUIRED = "Field tidak boleh kosong"
        private const val FIELD_DIGIT_ONLY = "Hanya boleh terisi angka numerik"
        private const val FIELD_IS_NOT_VALID = "Email tidak valid"
    }

    // =========================
    // Lifecycle
    // =========================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputDatasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userModel = arguments?.getParcelable("USER") ?: UserModel()
        val formType = arguments?.getInt(EXTRA_TYPE_FORM, 0)

        setupFormType(formType)
        setupClickListener()
        setupValidationListener()

        binding.btnSave.isEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // =========================
    // Setup Section
    // =========================

    private fun setupFormType(formType: Int?) {
        when (formType) {
            TYPE_ADD -> {
                requireActivity().title = "Tambah Baru"
                binding.btnSave.text = "Simpan"
            }
            TYPE_EDIT -> {
                requireActivity().title = "Ubah"
                binding.btnSave.text = "Update"
                showPreferenceInForm()
            }
        }
    }

    private fun setupClickListener() {

        binding.btnSave.setOnClickListener {
            handleSave()
        }

        binding.btnReset.setOnClickListener {
            resetForm()
        }
    }

    private fun setupValidationListener() {

        binding.edtName.addTextChangedListener { validateForm() }
        binding.edtEmail.addTextChangedListener { validateForm() }
        binding.edtAge.addTextChangedListener { validateForm() }

        binding.rgLoveNzull.setOnCheckedChangeListener { _, _ ->
            validateForm()
        }
    }

    // =========================
    // Save Logic
    // =========================

    private fun handleSave() {

        val name = binding.edtName.text.toString().trim()
        val email = binding.edtEmail.text.toString().trim()
        val age = binding.edtAge.text.toString().trim()
        val phoneNo = binding.edtPhone.text.toString().trim()
        val isLoveNzul = binding.rgLoveNzull.checkedRadioButtonId == R.id.rbYes

        if (!validateInput(name, email, age, phoneNo)) return

        saveUser(name, email, age, phoneNo, isLoveNzul)

        parentFragmentManager.setFragmentResult(
            EXTRA_RESULT,
            bundleOf(EXTRA_RESULT to userModel)
        )

        findNavController().popBackStack()
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
        userModel.age = age.toInt()
        userModel.phoneNumber = phoneNo
        userModel.isLove = loveNzul

        userPreference.setUser(userModel)

        Toast.makeText(requireContext(), "Data Tersimpan", Toast.LENGTH_SHORT).show()
    }

    // =========================
    // Validation Section
    // =========================

    private fun validateInput(
        name: String,
        email: String,
        age: String,
        phoneNo: String
    ): Boolean {

        if (name.isEmpty()) {
            binding.edtName.error = FIELD_REQUIRED
            return false
        }

        if (email.isEmpty()) {
            binding.edtEmail.error = FIELD_REQUIRED
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.error = FIELD_IS_NOT_VALID
            return false
        }

        if (age.isEmpty()) {
            binding.edtAge.error = FIELD_REQUIRED
            return false
        }

        if (phoneNo.isEmpty()) {
            binding.edtPhone.error = FIELD_REQUIRED
            return false
        }

        if (!phoneNo.isDigitsOnly()) {
            binding.edtPhone.error = FIELD_DIGIT_ONLY
            return false
        }

        return true
    }

    private fun validateForm() {

        val name = binding.edtName.text.toString().trim()
        val email = binding.edtEmail.text.toString().trim()
        val age = binding.edtAge.text.toString().trim()
        val selectedRadio = binding.rgLoveNzull.checkedRadioButtonId

        val isFormValid =
            name.isNotEmpty() &&
                    email.isNotEmpty() &&
                    age.isNotEmpty() &&
                    age.toIntOrNull() != null &&
                    selectedRadio != -1

        binding.btnSave.isEnabled = isFormValid
    }

    // =========================
    // Helper
    // =========================

    private fun showPreferenceInForm() {
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

    private fun resetForm() {
        val pref = UserPreference(requireContext())
        pref.clearUser()

        binding.edtName.setText("")
        binding.edtEmail.setText("")
        binding.edtAge.setText("")
        binding.edtPhone.setText("")
        binding.rgLoveNzull.clearCheck()

        binding.btnSave.text = "Simpan"
        binding.btnSave.isEnabled = false
    }
}