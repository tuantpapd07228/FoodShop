package com.example.asmand103.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.asmand103.DetailFruitActivity
import com.example.asmand103.R
import com.example.asmand103.constants.Constants.BASE_URL_IMG
import com.example.asmand103.databinding.ItemFruitBinding
import com.example.asmand103.response.FruitItem
import com.example.asmand103.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
interface SetOnClickAddCart{
    fun click(fruitItem: FruitItem)
}

class Fruitadapter @Inject constructor(): RecyclerView.Adapter<Fruitadapter.ViewHolder>() {
    var setOnClickAddCart : SetOnClickAddCart? = null
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFruitBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class ViewHolder (private val binding: ItemFruitBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FruitItem){
            binding.apply{
                Glide.with(context)
                    .load(BASE_URL_IMG + item.images[0])
                    .error(R.drawable.errorimg)
                    .fitCenter()
                    .into(img)
                tvname.text = item.name
                tvprice.text = item.price.toString()
                btnadd.setOnClickListener {
                    setOnClickAddCart?.click(fruitItem = item)
                }
                root.setOnClickListener{
                   val intent = Intent(context, DetailFruitActivity::class.java)
                    val bundle = Bundle()
                    bundle.putSerializable("fruit", item)// Sử dụng putSerializable để đính kèm đối tượng vào Bundle
                    intent.putExtras(bundle) // Đính kèm Bundle vào Intent
                    context.startActivity(intent)
                }

            }
        }
    }
    private val diffCallback = object : DiffUtil.ItemCallback<FruitItem>(){
        override fun areItemsTheSame(oldItem: FruitItem, newItem: FruitItem): Boolean {
           return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: FruitItem, newItem: FruitItem): Boolean {
           return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffCallback)
    fun setOnFruitClickListenerAdd(listener: SetOnClickAddCart) {
        setOnClickAddCart = listener
    }

}