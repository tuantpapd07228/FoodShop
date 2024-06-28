package com.example.asmand103.ui.vegetable

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.asmand103.R
import com.example.asmand103.adapter.Fruitadapter
import com.example.asmand103.databinding.FragmentFoodBinding
import com.example.asmand103.databinding.FragmentVegetableBinding
import com.example.asmand103.response.FruitItem
import com.example.asmand103.ui.food.FoodFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VegetableFragment : Fragment() {

    private lateinit var binding: FragmentVegetableBinding

    @Inject
    lateinit var fruitadapter: Fruitadapter
    lateinit var viewModel: VegetableFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(VegetableFragmentViewModel::class.java)
        binding = FragmentVegetableBinding.inflate(inflater, container, false)
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
                    if (it.type == "vegetable") listfruit.add(it)
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