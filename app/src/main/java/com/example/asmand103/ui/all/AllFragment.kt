package com.example.asmand103.ui.all

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.asmand103.adapter.Fruitadapter
import com.example.asmand103.adapter.SetOnClickAddCart
import com.example.asmand103.databinding.FragmentAllBinding
import com.example.asmand103.request.RequestCart
import com.example.asmand103.response.FruitItem
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class AllFragment : Fragment() {
    private lateinit var binding: FragmentAllBinding

    @Inject
    lateinit var fruitadapter: Fruitadapter
    lateinit var viewModel: AllFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(AllFragmentViewModel::class.java)
        binding = FragmentAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        binding.rcvItem.adapter = fruitadapter
        fruitadapter.setOnFruitClickListenerAdd(object : SetOnClickAddCart{
            override fun click(fruitItem: FruitItem) {
                viewModel.addFruitToCart(RequestCart(fruitItem.description, listOf(fruitItem.images[0]) , fruitItem.name, fruitItem._id, fruitItem.price,1, fruitItem.type))
            }
        })
    }

    private fun observeViewModel() {
        viewModel.listFruit.observe(viewLifecycleOwner, Observer {
            fruitadapter.differ.submitList(it)
        })
        viewModel.addCartSucc.observe(viewLifecycleOwner, Observer {
            if (it){
                Snackbar.make(binding.root, "Đã thêm vào giỏ hàng", Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.callFruitList()
    }




}
