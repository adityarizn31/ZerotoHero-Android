package com.example.sunlifeapps.view.pelayanan.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sunlifeapps.database.SiLancarDatabase
import com.example.sunlifeapps.database.dao.Ktp
import com.example.sunlifeapps.database.repository.KtpRepository
import kotlinx.coroutines.launch

class KtpViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: KtpRepository

    init {
        val dao = SiLancarDatabase.getDatabase(application).ktpDao()
        repo = KtpRepository(dao)
    }

    fun insert(data: Ktp) = viewModelScope.launch {
        repo.insert(data)
    }

    fun getAll() = repo.getAll()
}