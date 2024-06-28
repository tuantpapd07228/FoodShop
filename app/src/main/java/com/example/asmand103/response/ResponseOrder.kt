package com.example.asmand103.response

data class ResponseOrder(
    val message: String,
    val order: List<Order>,
    val status: Int
)