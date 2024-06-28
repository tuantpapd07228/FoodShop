package com.example.asmand103

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asmand103.constants.Constants.TAG
import com.example.asmand103.repository.Repository
import com.example.asmand103.request.RequestUser
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class CreateAccountViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _registerSucc : MutableLiveData<Boolean> = MutableLiveData(false)
    val registerSucc : MutableLiveData<Boolean> get() = _registerSucc
    private val _registerFail : MutableLiveData<Boolean> = MutableLiveData(false)
    val registerFail : MutableLiveData<Boolean> get() = _registerFail


    fun register (email: String ,us :String, pw:String){
        val emptyRequestBody: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "")
        val multipartBodyPart: MultipartBody.Part = MultipartBody.Part.createFormData("images", "", emptyRequestBody)

        repository.register(RequestUser(email,pw,us,multipartBodyPart)).enqueue(object : Callback<String>{
            override fun onResponse(p0: Call<String>, p1: Response<String>) {
                if (p1.body() == "200"){
                    _registerSucc.postValue(true)
                }else{
                    Log.e(TAG, "onResponse: ${p1.code()}", )
                    _registerFail.postValue(true)
                }
            }
            override fun onFailure(p0: Call<String>, p1: Throwable) {
                Log.e(TAG, "onFailure: $p1", )
            }

        })
    }


}