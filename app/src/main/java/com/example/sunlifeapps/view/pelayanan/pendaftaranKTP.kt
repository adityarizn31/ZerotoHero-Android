    package com.example.sunlifeapps.view.pelayanan

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sunlifeapps.R
import com.example.sunlifeapps.databinding.ActivityPendaftaranKtpBinding

    class pendaftaranKTP : AppCompatActivity() {

    private lateinit var binding : ActivityPendaftaranKtpBinding

    private lateinit var imgPreview : ImageView
    private var imageUri : Uri ?= null

        private val galleryLaucher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                imageUri = uri
                imgPreview.setImageURI(uri)
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

        val etTanggal = binding.etTanggalLahir
        val btnUpload = binding.btnUpload
        val btnSubmit = binding.btnSubmit

        imgPreview = binding.imgPreview

        etTanggal.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(this,
                { _, year, month, day ->
                    etTanggal.setText("$day-${month + 1}-$year")
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        btnUpload.setOnClickListener {
            galleryLaucher.launch("image/*")
        }

        btnSubmit.setOnClickListener {
            val nama = binding.etNama.text.toString()
            val nik = binding.etNik.text.toString()

            if (nama.isEmpty()) {
                toast("Nama wajib diisi")
            } else if (nik.length != 16) {
                toast("NIK harus 16 digit")
            } else if (imageUri == null) {
                toast("Foto KTP wajib diupload")
            } else {
                toast("Data KTP valid 🚀")
            }
        }
    }

        private fun toast(msg: String) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
}