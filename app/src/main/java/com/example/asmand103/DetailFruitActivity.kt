package com.example.asmand103

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.asmand103.constants.Constants.BASE_URL_IMG
import com.example.asmand103.constants.Constants.TAG
import com.example.asmand103.databinding.ActivityDetailFruitBinding
import com.example.asmand103.request.RequestCart
import com.example.asmand103.response.FruitItem
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFruitActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailFruitBinding
    private lateinit var fruitItem: FruitItem
    lateinit var viewModel : DetailViewModel
    private var quantity = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFruitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        if (intent.hasExtra("fruit")) {
            try {
                fruitItem = intent.getSerializableExtra("fruit") as FruitItem
                setView()
            } catch (e: Exception) {
                Log.e(TAG, "Error parsing FruitItem: $e")
            }
        } else {
            Log.e(TAG, "No 'fruit' data attached to Intent")
        }
    }

    private fun setView() {
        binding.apply {
            Glide.with(this@DetailFruitActivity)
                .load(BASE_URL_IMG + fruitItem.images[0])
                .error(R.drawable.errorimg)
                .fitCenter()
                .into(img)
            tvname.text = fruitItem.name
            tvprice.text = "${fruitItem.price} VND"
            tvquatity.text = quantity.toString()
            tvdesc.text = fruitItem.description
            btnadd.setOnClickListener {
                quantity++
                tvquatity.text = quantity.toString()
            }
            btnsub.setOnClickListener {
                if (quantity < 2){
                    quantity = 1
                }else{
                    quantity --
                }
                tvquatity.text = quantity.toString()
            }
            btnaddcart.setOnClickListener {
                viewModel.addFruitToCart(RequestCart(fruitItem.description, listOf(fruitItem.images[0]) , fruitItem.name, fruitItem._id, fruitItem.price,quantity, fruitItem.type))
                viewModel.addCartSucc.observe(this@DetailFruitActivity, Observer {
                    if (it) Snackbar.make(root, "Đã thêm vào giỏ hàng", Snackbar.LENGTH_SHORT).show()
                })
            }
        }
    }


}