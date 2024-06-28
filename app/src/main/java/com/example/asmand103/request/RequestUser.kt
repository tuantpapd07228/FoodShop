package com.example.asmand103.request

import okhttp3.MultipartBody

data class RequestUser(
    val email: String,
    val password: String,
    val username: String,
    val images : MultipartBody.Part?
)