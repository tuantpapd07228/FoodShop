package com.example.asmand103.response

data class GHNPostOrderResponse(
    val code: Int,
    val code_message_value: String,
    val `data`: DataXXXX,
    val message: String,
    val message_display: String
)