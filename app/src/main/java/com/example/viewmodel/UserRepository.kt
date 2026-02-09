package com.example.viewmodel

class UserRepository {
    suspend fun getUsers(page : Int) : List<User> {
        return RetrofitClient.apiService.getUsers(page).data
    }
}