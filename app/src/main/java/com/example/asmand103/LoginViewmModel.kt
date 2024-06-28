package com.example.asmand103

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asmand103.constants.Constants.TAG
import com.example.asmand103.repository.Repository
import com.example.asmand103.request.RequestLogin
import com.example.asmand103.response.ResponseUser
import com.example.asmand103.response.User
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class LoginViewmModel @Inject constructor(private val repository: Repository) :ViewModel() {

    private val _loginSucc :MutableLiveData<Boolean> = MutableLiveData(false)
    val loginSucc : MutableLiveData<Boolean> get() = _loginSucc
    private val _loginFail :MutableLiveData<Boolean> = MutableLiveData(false)
    val loginFail : MutableLiveData<Boolean> get() = _loginFail
    fun saveUser(user: User,context: Context, token:String){
        val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("username", user.username)
        editor.putString("id", user._id)
        editor.putString("email", user.email)
        editor.putString("name", user.name)
        editor.putString("avatar", user.avatar)
        editor.putInt("age", user.age)
        editor.putString("phone", user.phone)
        editor.putString("address", user.address)
        editor.putString("token", token)
        editor.putString("password", user.password)
        editor.apply()

    }
    fun login(us :String, pw: String, context: Context){
        repository.login(RequestLogin(us,pw)).enqueue(object : Callback<ResponseUser>{
            override fun onResponse(p0: Call<ResponseUser>, p1: Response<ResponseUser>) {
                if (p1.isSuccessful){
                    _loginSucc.postValue(true)
                    saveUser(p1.body()!!.user,context, p1.body()!!.token.toString())
                }else{
                    Log.e(TAG, "onResponse: login ${p1.code()}")
                    _loginFail.postValue(true)
                }
            }
            override fun onFailure(p0: Call<ResponseUser>, p1: Throwable) {
                Log.e(TAG, "onFailure: login $p1", )
                _loginFail.postValue(true)
            }

        })
    }
}