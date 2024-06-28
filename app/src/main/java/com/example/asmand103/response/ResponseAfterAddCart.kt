package com.example.asmand103.response

data class ResponseAfterAddCart(
    val cart: Cart,
    val message: String,
    val status: Int
)