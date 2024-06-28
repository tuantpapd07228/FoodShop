package com.example.asmand103

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.asmand103.constants.Constants.BASE_URL_IMG
import com.example.asmand103.constants.Constants.TAG
import com.example.asmand103.databinding.ActivityPersonBinding
import com.example.asmand103.request.RequestUpdateUser
import com.example.asmand103.response.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonBinding
    private lateinit var user: User
    lateinit var selectedImageUri: Uri
    val REQUEST_CODE_PICK_IMG = 123
    lateinit var viewModel: PersonActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(PersonActivityViewModel::class.java)
        if (getUser() != null){
            user = getUser()!!
        }else{
            Log.e(TAG, "onCreate: user null", )
        }
        setView()

    }

    private fun setView() {
        binding.apply {
            if (user.name !="" && user.age != 0){
                edtname.setText(user.name)
                edtage.setText(user.age.toString())
                edtpw.setText(user.password)
                edtphone.setText(user.phone)
                edtaddress.setText(user.address)
            }
            Glide.with(this@PersonActivity)
                .load(BASE_URL_IMG + user.avatar)
                .error(R.drawable.baseline_add_a_photo_24)
                .fitCenter()
                .centerCrop()
                .into(btnaddphoto)

            btnback.setOnClickListener { 
                finish()
            }
            btnaddphoto.setOnClickListener {
                choiceImg()
            }
            btncomplete.setOnClickListener {
                val passwordUpdate = edtpw.text.toString().ifEmpty { getUser()!!.password }
                val addressUpdate = edtaddress.text.toString().ifEmpty { getUser()!!.address }
                val phoneUpdate = edtphone.text.toString().ifEmpty { getUser()!!.phone }
                val ageUpdate = edtage.text.toString().ifEmpty { "18" }
                val nameUpdate = edtname.text.toString().ifEmpty { getUser()!!.name }

                val avatarUri: Uri? = if (selectedImageUri != null) {
                    selectedImageUri
                } else {
                    getUser()!!.avatar.toUri()
                }

                val requestUpdateUser  = RequestUpdateUser(
                    user._id,
                    user.username,
                    passwordUpdate,
                    user.email,
                    nameUpdate,
                    ageUpdate.toInt() ,
                    addressUpdate,
                    phoneUpdate,
                    null)
                viewModel.updateUser(this@PersonActivity, requestUpdateUser, avatarUri!!)

            }
            viewModel.updateSuccess.observe(this@PersonActivity, Observer {
                if (it){
                    Toast.makeText(this@PersonActivity, "Update Success fully", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }




    fun getUser(): User? {
        val sharedPreferencesName = "user_prefs"

        // Khởi tạo SharedPreferences
        val sharedPreferences = getSharedPreferences(sharedPreferencesName, MODE_PRIVATE)

        // Lấy dữ liệu từ SharedPreferences
        val username = sharedPreferences.getString("username", "")
        val id = sharedPreferences.getString("id", "")
        val email = sharedPreferences.getString("email", "")
        val name = sharedPreferences.getString("name", "")
        val avatar = sharedPreferences.getString("avatar", "")
        val age = sharedPreferences.getInt("age", 0)
        val phone = sharedPreferences.getString("phone", "")
        val address = sharedPreferences.getString("address", "")
        val password = sharedPreferences.getString("password", "")


        return User(
            username = username ?: "",
            _id = id ?: "",
            email = email ?: "",
            name = name ?: "",
            avatar = avatar ?: "",
            age = age,
            phone = phone ?: "",
            address = address ?: "",
            password = password ?: ""
        )
    }

    private fun choiceImg(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_PICK_IMG)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMG && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.data!!

            // Load the selected image into the btnaddphoto button using Glide
            selectedImageUri?.let { uri ->
                Glide.with(this)
                    .load(uri)
                    .fitCenter()
                    .error(R.drawable.baseline_add_a_photo_24)
                    .into(binding.btnaddphoto)
            }
            Log.e(TAG, "onActivityResult: $selectedImageUri", )
        }else{
            Log.e(TAG, "onActivityResult: fail when pick img", )
        }
    }

}