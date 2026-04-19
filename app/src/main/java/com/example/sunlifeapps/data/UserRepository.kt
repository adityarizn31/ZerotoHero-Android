package com.example.sunlifeapps.data

import com.example.sunlifeapps.data.pref.UserModel
import com.example.sunlifeapps.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor( private val userPreference: UserPreference) {

    companion object {
        @Volatile
        private var instance : UserRepository ?= null
        fun getInstance(userPreference: UserPreference) : UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(userPreference)
        } . also { instance = it }
    }

    suspend fun saveSession (user : UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession() : Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }


}