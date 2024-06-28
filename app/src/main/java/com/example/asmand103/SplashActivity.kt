package com.example.asmand103

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.asmand103.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding :ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val timer = Timer()
        val task = object : TimerTask(){
            override fun run() {
                startActivity(Intent(this@SplashActivity, CreateAccountActivity::class.java))
                finish()
            }

        }

        binding.lavSpl.setAnimation(R.raw.splash_lottie)


        timer.schedule(task , 3000)

    }
}