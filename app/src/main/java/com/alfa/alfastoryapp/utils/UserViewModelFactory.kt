package com.alfa.alfastoryapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alfa.alfastoryapp.login.LoginViewModel
import com.alfa.alfastoryapp.main.MainViewModel
import com.alfa.alfastoryapp.register.RegisterViewModel
import com.alfa.alfastoryapp.repository.Repository
import com.alfa.alfastoryapp.story.StoryViewModel
import kotlin.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory constructor(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->{
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) ->{
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) ->{
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(StoryViewModel::class.java) ->{
                StoryViewModel(repository) as T
            }
            else ->{
                throw IllegalArgumentException("ViewModel not Implement")
            }

        }
    }
}