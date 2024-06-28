package com.example.asmand103.response


data class FruitResponse(
    val FruitItem: List<FruitItem>,
    val message: String,
    val status: Int
)