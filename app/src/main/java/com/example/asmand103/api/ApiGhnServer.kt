package com.example.asmand103.api


import com.example.asmand103.request.GHNRequest
import com.example.asmand103.response.GHNPostOrderResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiGhnServer {

    @Headers("Token: 8e253393-f3cb-11ee-a6e6-e60958111f48", "ShopId: 191696")
    @POST("v2/shipping-order/create")
    fun createOrder(@Body body: GHNRequest) : Call<GHNPostOrderResponse>

}