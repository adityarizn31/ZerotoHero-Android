    package com.example.sunlifeapps.view.pelayanan

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import java.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sunlifeapps.databinding.ActivityPendaftaranKtpBinding
import com.example.sunlifeapps.view.utils.KtpValidation

class PendaftaranKTP : AppCompatActivity() {

    private lateinit var binding : ActivityPendaftaranKtpBinding

    private var imageUri : Uri ?= null

    private val galleryLaucher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            imageUri = uri
            binding.imgPreview.setImageURI(uri)
            updateButtonState()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPendaftaranKtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupDatePicker()
        setupValidation()
        setupAction()
    }

        private fun updateButtonState() {
            val isValid = isFormValid()
            binding.btnSubmit.isEnabled = isValid
            binding.btnSubmit.alpha = if (isValid) 1f else 0.5f
        }

        private fun isFormValid(): Boolean {

            val nama = binding.etNama.text.toString()
            val nik = binding.etNik.text.toString()
            val tempat = binding.etTempatLahir.text.toString()
            val tanggal = binding.etTanggalLahir.text.toString()
            val alamat = binding.etAlamat.text.toString()

            val error = KtpValidation.validateAll(nama, nik, tempat, tanggal, alamat)

            return error == null && imageUri != null
        }

        private fun setupAction() {
            binding.btnUpload.setOnClickListener {
                galleryLaucher.launch("image/*")
            }

            binding.btnSubmit.setOnClickListener {

                val nama = binding.etNama.text.toString()
                val nik = binding.etNik.text.toString()
                val tempat = binding.etTempatLahir.text.toString()
                val tanggal = binding.etTanggalLahir.text.toString()
                val alamat = binding.etAlamat.text.toString()

                val error = KtpValidation.validateAll(nama, nik, tempat, tanggal, alamat)

                when {
                    error != null -> toast(error)
                    imageUri == null -> toast("Foto KTP wajib diupload")
                    else -> toast("Data KTP valid 🚀")
                }
            }
        }

        private fun setupValidation() {
            val watcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    updateButtonState()
                }

                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            }

            binding.etNama.addTextChangedListener(watcher)
            binding.etNik.addTextChangedListener(watcher)
            binding.etTempatLahir.addTextChangedListener(watcher)
            binding.etTanggalLahir.addTextChangedListener(watcher)
            binding.etAlamat.addTextChangedListener(watcher)
        }

        @SuppressLint("SetTextI18n")
        private fun setupDatePicker() {
            binding.etTanggalLahir.setOnClickListener {
                val cal = Calendar.getInstance()
                DatePickerDialog(this,
                    { _, year, month, day ->
                        binding.etTanggalLahir.setText("$day-${month + 1}-$year")
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }

        private fun toast(msg: String) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
}