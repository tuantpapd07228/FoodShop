package com.example.asmand103.ui.fruit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asmand103.R
import com.example.asmand103.adapter.Fruitadapter
import com.example.asmand103.databinding.FragmentAllBinding
import com.example.asmand103.databinding.FragmentFruitBinding
import com.example.asmand103.response.FruitItem
import com.example.asmand103.ui.all.AllFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FruitFragment : Fragment() {
    private lateinit var binding: FragmentFruitBinding

    @Inject
    lateinit var fruitadapter: Fruitadapter
    lateinit var viewModel: FruitFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(FruitFragmentViewModel::class.java)
        binding = FragmentFruitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                    if (it.type == "fruit") listfruit.add(it)
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