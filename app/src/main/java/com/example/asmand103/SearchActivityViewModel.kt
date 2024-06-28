package com.example.asmand103

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asmand103.constants.Constants.TAG
import com.example.asmand103.repository.Repository
import com.example.asmand103.response.Fruit
import com.example.asmand103.response.FruitItem
import com.example.asmand103.response.ResponseSearchFruit
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchActivityViewModel @Inject constructor(private val repository: Repository) :ViewModel() {

    private val _listFruitFound : MutableLiveData<List<FruitItem>> = MutableLiveData<List<FruitItem>>()
    val listFruitFound :  MutableLiveData<List<FruitItem>>
        get() = _listFruitFound

    fun searchFruitWithKeyWord(keyWord:String) {
        repository.searchFruitWithKeyWord(keyWord).enqueue(object : Callback<ResponseSearchFruit>{
            override fun onResponse(
                p0: Call<ResponseSearchFruit>,
                p1: Response<ResponseSearchFruit>
            ) {
                if (p1.isSuccessful){
                   val data = p1.body()?.fruits
                    data.let {
                        _listFruitFound.postValue(listOf())
                        _listFruitFound.postValue(it)
                    }
                }else{
                    Log.e(TAG, "onResponse: ${p1.errorBody()}", )
                }
            }

            override fun onFailure(p0: Call<ResponseSearchFruit>, p1: Throwable) {
                Log.e(TAG, "onFailure: ${p1.message}")
            }

        })
    }


}