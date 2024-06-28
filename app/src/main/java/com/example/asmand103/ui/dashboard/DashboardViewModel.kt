package com.example.asmand103.ui.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asmand103.constants.Constants.TAG
import com.example.asmand103.repository.Repository
import com.example.asmand103.request.GHNRequest
import com.example.asmand103.request.RequestAddOrder
import com.example.asmand103.request.RequestUpdateQuantityInCart
import com.example.asmand103.response.Cart
import com.example.asmand103.response.GHNPostOrderResponse
import com.example.asmand103.response.ResponCart
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Objects
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor( private val repository : Repository) : ViewModel() {

    private val _listCart : MutableLiveData<List<Cart>> = MutableLiveData<List<Cart>>()
    val listCart : MutableLiveData<List<Cart>> = _listCart
    private val _status : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val status :MutableLiveData<Boolean>  get() = _status
    private val _statusDelItem : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val statusDelItem :MutableLiveData<Boolean>  get() = _statusDelItem

    private val _addCartSucc : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val addCartSucc :MutableLiveData<Boolean>  get() = _addCartSucc

    private val _orderSuccsess = MutableLiveData<Boolean>()
    val orderSuccess : MutableLiveData<Boolean>
        get() = _orderSuccsess
    fun getListCart() {
        repository.callCartList().enqueue(object : Callback<ResponCart>{
            override fun onResponse(p0: Call<ResponCart>, p1: Response<ResponCart>) {
                if (p1.isSuccessful){
                    _listCart.postValue(p1.body()!!.Carts)
                }else{
                    Log.e(TAG, "onResponse: getListCart ${p1.code()}")
                }
            }

            override fun onFailure(p0: Call<ResponCart>, p1: Throwable) {
                Log.e(TAG, "onFailure: ${p1.message}", )
            }

        })
    }

    fun delAllCart(){
        repository.delCart().enqueue(object : Callback<String>{
            override fun onResponse(p0: Call<String>, p1: Response<String>) {
                if (p1.body() == "200"){
                    _status.postValue(true)
                    getListCart()
                }else{
                    Log.e(TAG, "onResponse:delAllCart ${p1.code()}", )
                }
            }
            override fun onFailure(p0: Call<String>, p1: Throwable) {
                Log.e(TAG, "onFailure: delAllCart $p1", )
            }

        })
    }
    fun addOrder(idFruit:String, quantity:Int){
        Log.e(TAG, "addOrder: $idFruit", )
            repository.addOrder(RequestAddOrder(fruit = idFruit, quantity = quantity, )).enqueue(object : Callback<String>{
                override fun onResponse(p0: Call<String>, p1: Response<String>) {
                    if (p1.body() == "200"){
                        _addCartSucc.postValue(true)
                    }
                }

                override fun onFailure(p0: Call<String>, p1: Throwable) {
                    Log.e(TAG, "onFailure: ${p1.message}", )
                }

            })
    }

    fun createOrder(ghnRequest: GHNRequest){
        Log.e(TAG, "createOrder: $ghnRequest", )
        repository.createOrder(ghnRequest).enqueue(object : Callback<GHNPostOrderResponse>{
            override fun onResponse(
                p0: Call<GHNPostOrderResponse>,
                p1: Response<GHNPostOrderResponse>
            ) {
                if (p1.isSuccessful){
                    Log.e(TAG, "onResponse: createOrder${p1.body()}", )
                    _orderSuccsess.postValue(true)
                    p1.body()!!.data.order_code.let {
//                        addOrder(it.toString())
                    }
                }else{
                    Log.e(TAG, "onResponse: createOrder${p1.code()} ${p1.body()} ${p1.errorBody()}", )
                }
            }

            override fun onFailure(p0: Call<GHNPostOrderResponse>, p1: Throwable) {
                Log.e(TAG, "onFailure: createOrder$p1", )
            }

        })
    }

    fun updateQuantityInCart(id :String, quantity: Int){
        repository.updateQuantityFruitInCart(RequestUpdateQuantityInCart(id,quantity)).enqueue(object : Callback<String>{
            override fun onResponse(p0: Call<String>, p1: Response<String>) {
                if (p1.isSuccessful){
                    if (p1.body() == "200"){
                        
                    }
                }else{
                    Log.e(TAG, "onResponse: updateQuantityInCart ${p1.body()} ${p1.errorBody()}")
                }
            }

            override fun onFailure(p0: Call<String>, p1: Throwable) {
                Log.e(TAG, "onFailure: $p1", )
            }

        })
    }

}