package com.example.asmand103.repository

import android.util.Log
import com.example.asmand103.api.ApiGhnServer
import com.example.asmand103.api.ApiServer
import com.example.asmand103.constants.Constants
import com.example.asmand103.request.GHNRequest
import com.example.asmand103.request.RequestAddOrder
import com.example.asmand103.request.RequestCart
import com.example.asmand103.request.RequestLogin
import com.example.asmand103.request.RequestUpdateQuantityInCart
import com.example.asmand103.request.RequestUpdateUser
import com.example.asmand103.request.RequestUser
import com.example.asmand103.response.FruitResponse
import com.example.asmand103.response.GHNPostOrderResponse
import com.example.asmand103.response.ResponCart
import com.example.asmand103.response.ResponseAfterAddCart
import com.example.asmand103.response.ResponseOrder
import com.example.asmand103.response.ResponseSearchFruit
import com.example.asmand103.response.ResponseUpdateUser
import com.example.asmand103.response.ResponseUser
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import javax.inject.Inject

class Repository @Inject constructor(private val apiServer: ApiServer, private val ghnServer: ApiGhnServer) {
    fun callFruitList () : Call<FruitResponse> = apiServer.callFruitList()
    fun callCartList () : Call<ResponCart> = apiServer.callCartList()
    fun addCart(requestCart: RequestCart) : Call<ResponseAfterAddCart> = apiServer.addCart(requestCart)
    fun delCart() : Call<String> = apiServer.delAllCart()
    fun delItemCart(id :String) : Boolean {
        var delsucc = false
        try {
            val response = apiServer.delItemCart(id).execute()
            if (response.isSuccessful && response.body() == "200") {
                delsucc = true
            }
            Log.e("TAG", "onResponse: ${response.body()} ${response.code()}", )
        } catch (e: Exception) {
            Log.e(Constants.TAG, "onFailure: del item $e", )
        }
        return delsucc
    }
    fun login(requestLogin: RequestLogin) : Call<ResponseUser> = apiServer.login(requestLogin)

    fun register(requestUser: RequestUser) : Call<String> = apiServer.register(requestUser)

    fun getOrder( ) :Call<ResponseOrder> = apiServer.getOrders()

    fun addOrder(requestAddOrder: RequestAddOrder) : Call<String> = apiServer.addOrder(requestAddOrder)

    fun createOrder(ghnRequest: GHNRequest): Call<GHNPostOrderResponse> = ghnServer.createOrder(ghnRequest)

    fun updateUser(id: String, requestUpdateUser: RequestUpdateUser, avatarPart: MultipartBody.Part): Call<ResponseUpdateUser> {
        val usernamePart = requestUpdateUser.username.toRequestBody(MultipartBody.FORM)
        val passwordPart = requestUpdateUser.password.toRequestBody(MultipartBody.FORM)
        val emailPart = requestUpdateUser.email.toRequestBody(MultipartBody.FORM)
        val namePart = requestUpdateUser.name.toRequestBody(MultipartBody.FORM)
        val agePart = requestUpdateUser.age.toString().toRequestBody(MultipartBody.FORM)
        val addressPart = requestUpdateUser.address.toRequestBody(MultipartBody.FORM)
        val phonePart = requestUpdateUser.phone.toRequestBody(MultipartBody.FORM)

        return apiServer.updateUser(
            id,
            usernamePart,
            passwordPart,
            emailPart,
            namePart,
            agePart,
            addressPart,
            phonePart,
            avatarPart
        )
    }

    fun updateQuantityFruitInCart(requestUpdateQuantityInCart: RequestUpdateQuantityInCart) : Call<String> = apiServer.updateQuantityInCart(requestUpdateQuantityInCart)

    fun searchFruitWithKeyWord(keyword:String) :Call<ResponseSearchFruit> = apiServer.searchFruitWithKeyWord(keyword)
}