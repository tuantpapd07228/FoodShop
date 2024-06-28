package com.example.asmand103.response

data class DataXXXX(
    val district_encode: String,
    val expected_delivery_time: String,
    val fee: Fee,
    val operation_partner: String,
    val order_code: String,
    val sort_code: String,
    val total_fee: Int,
    val trans_type: String,
    val ward_encode: String
)