package com.example.asmand103

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.asmand103.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var viewmModel: LoginViewmModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmModel = ViewModelProvider(this).get(LoginViewmModel::class.java)
        setView()
        listenEvent()
    }

    private fun setView() {
        binding.apply {
            btnback.setOnClickListener {
                startActivity(Intent(this@LoginActivity, CreateAccountActivity::class.java))
                finish()
            }

            tvtoregister.setOnClickListener{
                startActivity(Intent(this@LoginActivity, CreateAccountActivity::class.java))
                finish()
            }

            btnLogin.setOnClickListener {
                val us = edtusername.text.toString()
                val pw = edtpassword.text.toString()
                viewmModel.login(us, pw, this@LoginActivity)
            }
        }
    }
    private fun listenEvent(){
        viewmModel.loginFail.observe(this, Observer {
            if (it){
                Snackbar.make(binding.root, "Sai tài khoản hoặc mật khẩu", Snackbar.LENGTH_LONG).show()
            }
        })
        viewmModel.loginSucc.observe(this, Observer {
            if (it){
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_LONG).show()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        })
    }
}