package com.alfa.alfastoryapp.login

import androidx.lifecycle.ViewModel
import com.alfa.alfastoryapp.repository.Repository

class LoginViewModel constructor(private val repository: Repository): ViewModel() {

    suspend fun login(email: String, password: String) = repository.login(email, password)
}