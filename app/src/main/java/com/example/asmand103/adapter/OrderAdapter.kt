package com.example.asmand103.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.asmand103.R
import com.example.asmand103.constants.Constants.BASE_URL_IMG
import com.example.asmand103.databinding.ItemCartBinding
import com.example.asmand103.databinding.ItemOrderBinding
import com.example.asmand103.repository.Repository
import com.example.asmand103.response.Cart
import com.example.asmand103.response.Order
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class OrderAdapter @Inject constructor(private val repository: Repository): RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class ViewHolder (private val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Order){
            var quantity = item.quantity
            binding.apply{
                Glide.with(context)
                    .load(BASE_URL_IMG + item.fruit.images[0])
                    .error(R.drawable.errorimg)
                    .fitCenter()
                    .into(img)
                tvname.text = item.fruit.name
                tvprice.text = "Price: "  + item.fruit.price
                tvtotalprice.text =  (item.fruit.price * quantity).toString() + "VND"
                tvquantity.text = "Quantity: " + quantity
                tvdate.text = item.date
            }
        }
    }
    private val diffCallback = object : DiffUtil.ItemCallback<Order>(){
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffCallback)


}