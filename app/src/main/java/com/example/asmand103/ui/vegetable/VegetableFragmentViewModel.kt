package com.example.asmand103.ui.vegetable

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asmand103.constants.Constants
import com.example.asmand103.repository.Repository
import com.example.asmand103.response.FruitItem
import com.example.asmand103.response.FruitResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class VegetableFragmentViewModel @Inject constructor(private val repository: Repository) :ViewModel() {
    private val _listFruit : MutableLiveData<List<FruitItem>> = MutableLiveData()
    val listFruit : MutableLiveData<List<FruitItem>> =  _listFruit

    fun callFruitList(){
        repository.callFruitList().enqueue(object : Callback<FruitResponse> {
            override fun onResponse(p0: Call<FruitResponse>, p1: Response<FruitResponse>) {
                if (p1.isSuccessful){
                    _listFruit.postValue(p1.body()!!.FruitItem)
                    Log.e(Constants.TAG, "onResponse:callFruitList ${p1.code()}", )
                }else{
                    Log.e(Constants.TAG, "onResponse:callFruitList ${p1.code()}", )
                }
            }
            override fun onFailure(p0: Call<FruitResponse>, p1: Throwable) {
                Log.e(Constants.TAG, "onFailure: callFruitList ${p1.message}", )
            }

        })
    }

}