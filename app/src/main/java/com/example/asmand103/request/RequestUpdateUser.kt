package com.example.asmand103.request

import okhttp3.MultipartBody

data class RequestUpdateUser(
    val id: String,
    val username: String,
    val password: String,
    val email: String,
    val name: String,
    val age: Int,
    val address: String,
    val phone:String,
    var avatar: MultipartBody.Part?,

    )
