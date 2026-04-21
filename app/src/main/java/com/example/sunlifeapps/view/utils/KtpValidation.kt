package com.example.sunlifeapps.view.utils

object KtpValidation {

    fun validateNama(nama : String) : String ? {
        return when {
            nama.trim().isEmpty() -> "Nama tidak boleh kosong !!"
            else -> null
        }
    }

    fun validateNik(nik : String) : String ? {
        return when {
            nik.trim().isEmpty() -> "NIK tidak boleh kosong !!"
            nik.length != 16 -> "NIK harus 16 digit"
            else -> null
        }
    }

    fun validateTempatLahir(tempatLahir : String) : String ? {
        return when {
            tempatLahir.trim().isEmpty() -> "Tempat lahir tidak boleh kosong !!"
            else -> null
        }
    }

    fun validateTanggalLahir(tanggalLahir : String) : String ? {
        return when {
            tanggalLahir.trim().isEmpty() -> "Tanggal Lahir tidak boleh kosong !!"
            else -> null
        }
    }

    fun validateAlamat(alamat : String) : String ? {
        return when {
            alamat.trim().isEmpty() -> "Alamat tidak boleh kosong !!"
            else -> null
        }
    }

    fun validateAll(
        nama: String,
        nik: String,
        tempat: String,
        tanggal: String,
        alamat: String
    ): String? {
        return validateNama(nama)
            ?: validateNik(nik)
            ?: validateTempatLahir(tempat)
            ?: validateTanggalLahir(tanggal)
            ?: validateAlamat(alamat)
    }

}