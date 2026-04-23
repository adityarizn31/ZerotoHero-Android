package com.example.sunlifeapps.view.pelayanan

import android.app.DatePickerDialog
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
import com.example.sunlifeapps.databinding.ActivityPendaftaranKkBinding
import com.example.sunlifeapps.view.utils.KKValidation
import java.util.Calendar
import kotlin.toString

class PendaftaranKK : AppCompatActivity() {

    private lateinit var binding : ActivityPendaftaranKkBinding

    private var imageUri : Uri?= null

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            imageUri = uri
            binding.imgPreview.setImageURI(uri)
            updateButtonState()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        binding = ActivityPendaftaranKkBinding.inflate(layoutInflater)
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

    private fun isFormValid() : Boolean {
        val nama = binding.etNama.text.toString()
        val nik = binding.etNik.text.toString()
        val tempat = binding.etTempatLahir.text.toString()
        val tanggal = binding.etTanggalLahir.text.toString()
        val alamat = binding.etAlamat.text.toString()
        val rt = binding.etRT.text.toString()
        val rw = binding.etRW.text.toString()
        val desa = binding.etDesa.text.toString()
        val kecamatan = binding.etKecamatan.text.toString()
        val kabupaten = binding.etKabupaten.text.toString()

        val error = KKValidation.validateAll(nama, nik, tempat, tanggal, alamat, rt, rw, desa, kecamatan, kabupaten)

        return error == null && imageUri != null
    }

    private fun setupAction() {
        binding.btnUpload.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        binding.btnSubmit.setOnClickListener {
            val nama = binding.etNama.text.toString()
            val nik = binding.etNik.text.toString()
            val tempat = binding.etTempatLahir.text.toString()
            val tanggal = binding.etTanggalLahir.text.toString()
            val alamat = binding.etAlamat.text.toString()
            val rt = binding.etRT.text.toString()
            val rw = binding.etRW.text.toString()
            val desa = binding.etDesa.text.toString()
            val kecamatan = binding.etKecamatan.text.toString()
            val kabupaten = binding.etKabupaten.text.toString()

            val error = KKValidation.validateAll(nama, nik, tempat, tanggal, alamat, rt, rw, desa, kecamatan, kabupaten)

            when {
                error != null -> toast(error)
                imageUri == null -> toast("Foto KTP wajib diupload")
                else -> toast("Data KTP valid 🚀")
            }
        }
    }

    private fun setupValidation() {
        val watcher = object :  TextWatcher {
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
        binding.etRT.addTextChangedListener(watcher)
        binding.etRW.addTextChangedListener(watcher)
        binding.etDesa.addTextChangedListener(watcher)
        binding.etKecamatan.addTextChangedListener(watcher)
        binding.etKabupaten.addTextChangedListener(watcher)
    }

    private fun setupDatePicker() {
        binding.etTanggalLahir.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(this,
                { _, year, month, day ->
                    binding.etTanggalLahir.setText("$day-${month-1}-$year")
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH),
            ).show()
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}


