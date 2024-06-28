package com.example.asmand103

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asmand103.constants.Constants.TAG
import com.example.asmand103.repository.Repository
import com.example.asmand103.request.RequestCart
import com.example.asmand103.response.ResponseAfterAddCart
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _addCartSucc : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val addCartSucc : MutableLiveData<Boolean> =_addCartSucc

    fun addFruitToCart(requestCart :RequestCart){
        repository.addCart(requestCart).enqueue(object : Callback<ResponseAfterAddCart>{
            override fun onResponse(
                p0: Call<ResponseAfterAddCart>,
                p1: Response<ResponseAfterAddCart>
            ) {
                if (p1.isSuccessful){
                    _addCartSucc.postValue(true)
                }else{
                    Log.e(TAG, "onResponse: ${p1.code()}")
                }
            }

            override fun onFailure(p0: Call<ResponseAfterAddCart>, p1: Throwable) {
                Log.e(TAG, "onFailure: ${p1.message}", )
            }

        })
    }


}