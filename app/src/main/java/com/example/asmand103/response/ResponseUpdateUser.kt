package com.example.asmand103.response

data class ResponseUpdateUser(
    val message: String,
    val status: Int,
    val userAfterUpdate: UserAfterUpdate
)