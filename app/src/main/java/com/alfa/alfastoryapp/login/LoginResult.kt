package com.alfa.alfastoryapp.login

import com.google.gson.annotations.SerializedName

data class LoginResult(
    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("token")
    val token: String,
)
