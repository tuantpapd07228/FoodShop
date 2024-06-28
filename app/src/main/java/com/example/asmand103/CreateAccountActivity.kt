package com.example.asmand103

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.asmand103.databinding.ActivityCreateAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountActivity : AppCompatActivity() {
    private var binding : ActivityCreateAccountBinding? = null
    lateinit var viewModel: CreateAccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        viewModel = ViewModelProvider(this).get(CreateAccountViewModel::class.java)
        setView()
    }

    private fun setView() {
        viewModel.registerSucc.observe(this@CreateAccountActivity, Observer {
            if (it){
                Toast.makeText(this@CreateAccountActivity, "Đăng ký thành công", Toast.LENGTH_LONG).show()
            }
        })
        viewModel.registerFail.observe(this@CreateAccountActivity, Observer {
            if (it){
                Toast.makeText(this@CreateAccountActivity, "Đăng ký không thành công \n email hoặc username đã đuợc dùng", Toast.LENGTH_LONG).show()
            }
        })
        binding?.apply {
            tvtologin.setOnClickListener{
                startActivity(Intent(this@CreateAccountActivity, LoginActivity::class.java))
                finish()
            }
            btnRegister.setOnClickListener {
                val email = edtemail.text.toString()
                val us = edtusername.text.toString()
                val pw = edtpassword.text.toString()
                viewModel.register(email, us, pw)
            }
        }
    }
}