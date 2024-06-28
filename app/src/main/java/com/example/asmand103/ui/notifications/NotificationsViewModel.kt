package com.example.asmand103.ui.notifications

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asmand103.constants.Constants.TAG
import com.example.asmand103.repository.Repository
import com.example.asmand103.response.ResponseOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel  @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _listResponseOrder : MutableLiveData<ResponseOrder> = MutableLiveData<ResponseOrder>()
    val listResponseOrder : MutableLiveData<ResponseOrder> get() = _listResponseOrder

    fun getOrder (){
        repository.getOrder().enqueue(object : Callback<ResponseOrder>{
            override fun onResponse(p0: Call<ResponseOrder>, p1: Response<ResponseOrder>) {
                if (p1.isSuccessful){
                    _listResponseOrder.postValue(p1.body())
                    Log.e(TAG, "onResponse: getOrder ${p1.body()}")
                }else{
                    Log.e(TAG, "onResponse: ${p1.code()}", )
                }
            }

            override fun onFailure(p0: Call<ResponseOrder>, p1: Throwable) {
                Log.e(TAG, "onFailure: ${p1.message}", )
            }

        })
    }
}