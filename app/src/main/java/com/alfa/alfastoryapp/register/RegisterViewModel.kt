package com.alfa.alfastoryapp.register

import androidx.lifecycle.ViewModel
import com.alfa.alfastoryapp.repository.Repository

class RegisterViewModel constructor(private val repository: Repository): ViewModel() {
    suspend fun register(name: String, email: String, password: String) = repository.register(name, email, password)
}