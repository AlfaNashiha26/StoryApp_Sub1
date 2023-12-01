package com.alfa.alfastoryapp.story

import androidx.lifecycle.ViewModel
import com.alfa.alfastoryapp.repository.Repository
import java.io.File

class StoryViewModel constructor(private val repository: Repository): ViewModel() {

    suspend fun uploadStories(authorization: String, description: String, file: File) = repository.uploadStory(authorization, description, file)
}