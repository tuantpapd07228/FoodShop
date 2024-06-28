package com.example.asmand103.response

import java.io.Serializable

data class IdDistributor(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val name: String,
    val updatedAt: String
) : Serializable