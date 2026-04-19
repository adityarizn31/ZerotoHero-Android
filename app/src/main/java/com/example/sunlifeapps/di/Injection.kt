package com.example.sunlifeapps.di

import android.content.Context
import com.example.sunlifeapps.data.UserRepository
import com.example.sunlifeapps.data.pref.UserPreference
import com.example.sunlifeapps.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context) : UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}