package com.example.sunlifeapps.view.utils

object KKValidation {

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

    fun validateRT(rt : String) : String ? {
        return when {
            rt.trim().isEmpty() -> "RT tidak boleh kosong !!"
            else -> null
        }
    }

    fun validateRW(rw : String) : String ? {
        return when {
            rw.trim().isEmpty() -> "RT tidak boleh kosong !!"
            else -> null
        }
    }

    fun validateDesa(desa : String) : String ? {
        return when {
            desa.trim().isEmpty() -> "Desa tidak boleh kosong !!"
            else -> null
        }
    }

    fun validateKecamatan(kecamatan : String) : String ? {
        return when {
            kecamatan.trim().isEmpty() -> "Desa tidak boleh kosong !!"
            else -> null
        }
    }

    fun validateKabupaten(kabupaten : String) : String ? {
        return when {
            kabupaten.trim().isEmpty() -> "Desa tidak boleh kosong !!"
            else -> null
        }
    }

    fun validateAll(
        nama : String,
        nik : String,
        tempatLahir : String,
        tanggalLahir : String,
        alamat : String,
        rt : String,
        rw : String,
        desa : String,
        kecamatan : String,
        kabupaten : String,
    ) : String ? {
        return validateNama(nama)
            ?: validateNik(nik)
            ?: validateTempatLahir(tempatLahir)
            ?: validateTanggalLahir(tanggalLahir)
            ?: validateAlamat(alamat)
            ?: validateAlamat(rt)
            ?: validateAlamat(rw)
            ?: validateAlamat(kecamatan)
            ?: validateAlamat(kabupaten)
    }

}