package com.alfa.alfastoryapp.api

import com.alfa.alfastoryapp.login.LoginResponse
import com.alfa.alfastoryapp.main.MainResponse
import com.alfa.alfastoryapp.register.RegisterResponse
import com.alfa.alfastoryapp.story.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @POST("register")
    @FormUrlEncoded
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") auth: String
    ): MainResponse

    @Multipart
    @POST("stories")
    suspend fun uploadStories(
        @Header("Authorization") auth: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): StoryResponse
}