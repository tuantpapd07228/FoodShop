package com.example.asmand103.request

import java.text.SimpleDateFormat
import java.util.Date
import kotlin.random.Random

data class RequestAddOrder(
    val order_code: String = generateRandomOrderCode(),
    val fruit: String,
    val quantity: Int,
    val date: String = getCurrentDate()
)

fun generateRandomOrderCode(): String {
    val random = Random(System.currentTimeMillis())
    return random.nextInt(100000, 999999).toString() // Mã đơn hàng gồm 6 chữ số
}

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val currentDate = Date()
    return dateFormat.format(currentDate)
}