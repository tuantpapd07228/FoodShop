package com.example.asmand103.ui.food

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.asmand103.adapter.Fruitadapter
import com.example.asmand103.databinding.FragmentFoodBinding
import com.example.asmand103.response.FruitItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FoodFragment : Fragment() {
    private lateinit var binding: FragmentFoodBinding

    @Inject
    lateinit var fruitadapter: Fruitadapter
    lateinit var viewModel: FoodFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(FoodFragmentViewModel::class.java)
        binding = FragmentFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        binding.rcv.layoutManager = GridLayoutManager(context, 2)
        binding.rcv.adapter = fruitadapter
    }

    private fun observeViewModel() {
        val listfruit = mutableListOf<FruitItem>()
        viewModel.listFruit.observe(viewLifecycleOwner, Observer {
            it.map {
                it.let {
                    if (it.type == "food") listfruit.add(it)
                }
            }
            fruitadapter.differ.submitList(listfruit)
            setupViews()
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.callFruitList()
    }



}