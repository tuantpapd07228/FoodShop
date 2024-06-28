package com.example.asmand103.response

data class ResponseSearchFruit(
    val fruits: List<FruitItem>,
    val message: String,
    val status: Int
)