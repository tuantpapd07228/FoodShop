package com.example.asmand103.api

import com.example.asmand103.request.RequestAddOrder
import com.example.asmand103.response.ResponseAfterAddCart
import com.example.asmand103.request.RequestCart
import com.example.asmand103.request.RequestLogin
import com.example.asmand103.request.RequestUpdateQuantityInCart
import com.example.asmand103.request.RequestUpdateUser
import com.example.asmand103.request.RequestUser
import com.example.asmand103.response.FruitResponse
import com.example.asmand103.response.ResponCart
import com.example.asmand103.response.ResponseOrder
import com.example.asmand103.response.ResponseSearchFruit
import com.example.asmand103.response.ResponseUpdateUser
import com.example.asmand103.response.ResponseUser
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServer {
    @GET("fruit/list")
    fun callFruitList() : Call<FruitResponse>

    @GET("carts/list")
    fun callCartList() : Call<ResponCart>

    @POST("carts/add")
    fun addCart(@Body requestCart: RequestCart) : Call<ResponseAfterAddCart>

    @DELETE("carts/deleteAllItem/")
    fun delAllCart() : Call<String>

    @DELETE("carts/delItemById/{id}")
    fun delItemCart(@Path("id") id:String) : Call<String>

    @POST("users/login")
    fun login(@Body requestLogin: RequestLogin) :Call<ResponseUser>

    @POST("users/register")
    fun register(@Body requestUser: RequestUser) : Call<String>

    @GET("orders/list")
    fun getOrders() :Call<ResponseOrder>

    @POST("orders/add")
    fun addOrder(@Body requestAddOrder: RequestAddOrder) : Call<String>


    @Multipart
    @PATCH("users/update/{id}")
    fun updateUser(
        @Path("id") id: String,
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody,
        @Part("email") email: RequestBody,
        @Part("name") name: RequestBody,
        @Part("age") age: RequestBody,
        @Part("address") address: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part avatar: MultipartBody.Part
    ): Call<ResponseUpdateUser>

    @POST("carts/updateQuantity")
    fun updateQuantityInCart(@Body requestUpdateQuantityInCart: RequestUpdateQuantityInCart) : Call<String>

    @GET("fruit/search")
    fun searchFruitWithKeyWord(@Query("query") key:String) : Call<ResponseSearchFruit>
}