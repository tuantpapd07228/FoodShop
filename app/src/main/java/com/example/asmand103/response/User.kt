package com.example.asmand103.response

data class User(
    val __v: Int = 0,
    val _id: String,
    val age: Int,
    val available: Boolean = false,
    val avatar: String,
    val createdAt: String = "",
    val email: String,
    val name: String,
    val password: String = "",
    val updatedAt: String = "",
    val username: String = "",
    val phone :String ="",
    val address: String ="",
)