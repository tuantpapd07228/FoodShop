package com.example.asmand103.response

data class ResponseUser(
    val message: String,
    val refreshToken: String,
    val status: Int,
    val token: String,
    val user: User
)