package com.example.asmand103

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asmand103.constants.Constants.TAG
import com.example.asmand103.repository.Repository
import com.example.asmand103.request.RequestUpdateUser
import com.example.asmand103.response.ResponseUpdateUser
import com.example.asmand103.response.User
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject
@HiltViewModel
class PersonActivityViewModel @Inject constructor(private val repository: Repository)  :ViewModel(){

    private val _updateSuccess = MutableLiveData<Boolean>()
    val updateSuccess : LiveData<Boolean>
        get() = _updateSuccess

    fun updateUser(context: Context, requestUpdateUser: RequestUpdateUser, imageUri: Uri) {
        try {
            // Lấy đường dẫn thực tế của tệp từ Uri
            val file = getFileFromUri(context, imageUri)

            // Kiểm tra xem file có null không
            if (file != null) {
                // Định dạng kiểu MIME cho tệp (ở đây là hình ảnh JPEG)
                val mediaType = context.contentResolver.getType(imageUri)?.toMediaTypeOrNull()

                // Tạo RequestBody từ File
                val requestBody = file.asRequestBody(mediaType)

                // Tạo MultipartBody.Part từ RequestBody
                requestUpdateUser.avatar = MultipartBody.Part.createFormData("avatar", file.name, requestBody)

                // Gọi phương thức register từ repository
                repository.updateUser(requestUpdateUser.id, requestUpdateUser,
                    requestUpdateUser.avatar!!
                ).enqueue(object :
                    Callback<ResponseUpdateUser> {
                    override fun onResponse(
                        p0: Call<ResponseUpdateUser>,
                        response: Response<ResponseUpdateUser>
                    ) {
                        if (response.isSuccessful) {
                           if (response.code() == 200){
                               _updateSuccess.postValue(true)
                           }else{
                               Log.e(TAG, "onResponse: update ${response.code()} ${response.message()}")
                           }
                        } else {
                            // Xử lý lỗi khi không thành công
                            Log.e(TAG, "update failed with code ${response.code()}: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(p0: Call<ResponseUpdateUser>, p1: Throwable) {
                        Log.e(TAG, "update failed with code $p1 67")

                    }

                })
            } else {
                // Xử lý khi không tìm thấy file từ Uri
                Log.e(TAG, "File not found from Uri: $imageUri")
            }
        } catch (e: Exception) {
            // Xử lý ngoại lệ khi có lỗi xảy ra
            Log.e(TAG, "Error registering user: $e", e)
        }
    }

    private fun getFileFromUri(context: Context, uri: Uri): File? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, filePathColumn, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndex(filePathColumn[0])
                return File(it.getString(columnIndex))
            }
        }
        return null
    }
}