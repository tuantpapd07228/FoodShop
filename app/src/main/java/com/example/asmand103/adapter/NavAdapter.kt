package com.example.asmand103.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.asmand103.R
import com.example.asmand103.constants.Constants.TAG
import com.example.asmand103.databinding.ItemNavBinding
import com.example.asmand103.response.NavItem
import javax.inject.Inject

interface OnPositionChangeListener {
    fun onPositionChanged(position: Int)
}

class NavAdapter @Inject constructor() : RecyclerView.Adapter<NavAdapter.ViewModel>() {
    private var items: MutableList<NavItem> = mutableListOf()
    private lateinit var context: Context
    private var positionChangeListener: OnPositionChangeListener? = null
    var positionNav = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNavBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewModel(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewModel, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            positionChangeListener?.onPositionChanged(holder.adapterPosition)
        }
    }

    inner class ViewModel(private val binding: ItemNavBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NavItem) {
            binding.apply {
                imgItem.setImageResource(item.icon)
                tvTitle.text = item.title

            }
        }
    }


    fun submitList(newList: List<NavItem>) {
        val diffCallback = NavDiffCallback(items, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = newList as MutableList<NavItem>
        diffResult.dispatchUpdatesTo(this)
    }

    private class NavDiffCallback(
        private val oldList: List<NavItem>,
        private val newList: List<NavItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].title == newList[newItemPosition].title
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

//    fun setSelectedItemPosition(position: Int) {
//        val previousSelectedItemPosition = selectedItemPosition
//        selectedItemPosition = position
//        notifyItemChanged(previousSelectedItemPosition)
//        notifyItemChanged(selectedItemPosition)
//    }
    fun setPositionChangeListener(listener: OnPositionChangeListener) {
        this.positionChangeListener = listener
    }
}

