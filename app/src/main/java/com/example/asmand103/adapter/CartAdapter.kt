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
import com.example.asmand103.repository.Repository
import com.example.asmand103.response.Cart
import com.example.asmand103.ui.dashboard.DashboardFragment
import com.example.asmand103.ui.dashboard.DashboardViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
interface CartAdapterListener1 {
    fun onItemClicked()
    fun updateQuantity(id:String, quantity: Int)
}


class CartAdapter @Inject constructor(private val repository: Repository): RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    lateinit var context: Context

    var listener: CartAdapterListener1? = null

    fun setItemListener(listener: CartAdapterListener1) {
        this.listener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class ViewHolder (private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Cart){
            var quantity = item.quantity
            binding.apply{
                Glide.with(context)
                    .load(BASE_URL_IMG + item.images[0])
                    .error(R.drawable.errorimg)
                    .fitCenter()
                    .into(img)
                tvname.text = item.name
                tvprice.text = item.price.toString()
                tvtotalprice.text = (item.price * quantity).toString()
                tvquantity.text = quantity.toString()

                btnsub.setOnClickListener {
                    if (quantity >= 1){
                        quantity --
                        tvtotalprice.text = (item.price * quantity).toString()
                        tvquantity.text = quantity.toString()
                        listener?.updateQuantity(item._id, quantity)

                    }
                    if (quantity == 0){
                        GlobalScope.launch (Dispatchers.IO) {
                            val isDeleted = repository.delItemCart(item._id)
                            GlobalScope.launch(Dispatchers.Main) {
                                if (isDeleted){
                                    Snackbar.make(root, "Đã xóa thực phẩm khỏi giỏ hàng", Snackbar.LENGTH_LONG).show()
                                    val mutableList = differ.currentList.toMutableList()
                                    mutableList.removeAt(adapterPosition)
                                    differ.submitList(mutableList)
                                    listener?.onItemClicked()
                                }
                            }
                        }
                    }


                }
                btnplus.setOnClickListener {
                    quantity ++
                    tvtotalprice.text = (item.price * quantity).toString()
                    tvquantity.text = quantity.toString()
                    listener?.updateQuantity(item._id, quantity)
                }
                tvdesc.text =item.description
                tvtype.text = item.type

            }
        }
    }
    private val diffCallback = object : DiffUtil.ItemCallback<Cart>(){
        override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffCallback)
}