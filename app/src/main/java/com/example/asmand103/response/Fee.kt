package com.example.asmand103.response

data class Fee(
    val cod_failed_fee: Int,
    val cod_fee: Int,
    val coupon: Int,
    val deliver_remote_areas_fee: Int,
    val deliver_remote_areas_fee_return: Int,
    val document_return: Int,
    val double_check: Int,
    val double_check_deliver: Int,
    val insurance: Int,
    val main_service: Int,
    val pick_remote_areas_fee: Int,
    val pick_remote_areas_fee_return: Int,
    val r2s: Int,
    val `return`: Int,
    val return_again: Int,
    val station_do: Int,
    val station_pu: Int
)