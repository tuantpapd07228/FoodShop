package com.example.asmand103

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.asmand103.adapter.Fruitadapter
import com.example.asmand103.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    lateinit var viewModel:SearchActivityViewModel
    @Inject
    lateinit var adapter: Fruitadapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(SearchActivityViewModel::class.java)
        setView()
        listenData()
    }

    private fun listenData() {
        viewModel.listFruitFound.observe(this, Observer {
            if (it.isNotEmpty()){
                adapter.differ.submitList(it)
                binding.searchrcv.adapter = adapter
            }
        })
    }

    private fun setView() {
        binding.apply {
            searchrcv.layoutManager = GridLayoutManager(this@SearchActivity, 2)
            btnSerach.setOnClickListener{
                val keyWord = edtsearchword.text.toString()
                if (keyWord != "" ){
                    viewModel.searchFruitWithKeyWord(keyWord)
                }
            }
        }
    }


}