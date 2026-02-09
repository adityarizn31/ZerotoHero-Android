package com.example.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val repository = UserRepository()

    private val _users = MutableLiveData<List<User>>()
    val users : LiveData<List<User>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun fetchUsers() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val result = repository.getUsers(1)
                _users.value = result
            } catch (e : Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}