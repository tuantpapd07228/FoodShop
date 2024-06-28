package com.example.asmand103.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.asmand103.PersonActivity
import com.example.asmand103.R
import com.example.asmand103.SearchActivity
import com.example.asmand103.adapter.NavAdapter
import com.example.asmand103.adapter.OnPositionChangeListener
import com.example.asmand103.constants.Constants.TAG
import com.example.asmand103.databinding.FragmentHomeBinding
import com.example.asmand103.response.NavItem
import com.example.asmand103.ui.all.AllFragment
import com.example.asmand103.ui.food.FoodFragment
import com.example.asmand103.ui.fruit.FruitFragment
import com.example.asmand103.ui.vegetable.VegetableFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), OnPositionChangeListener {

    private var _binding: FragmentHomeBinding? = null

    lateinit var homeViewModel : HomeViewModel
    @Inject
    lateinit var adapter: NavAdapter
    val binding get() = _binding!!

    val listNavItem = mutableListOf<NavItem>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setView()

        return root
    }

    private fun setView() {
        setFragment(0)
        listNavItem.addAll(listOf(
            NavItem(R.drawable.baseline_border_all_24, "All"),
            NavItem(R.drawable.baseline_food_bank_24, "Fruit"),
            NavItem(R.drawable.baseline_food_bank_24, "Food"),
            NavItem(R.drawable.baseline_invert_colors_24, "Vegetable"),
        ))
        binding.apply {
            btnSearch.setOnClickListener {
                startActivity(Intent(context, SearchActivity::class.java))
            }
            adapter.submitList(listNavItem)
            rcvNav.adapter = adapter
            adapter.setPositionChangeListener(object : OnPositionChangeListener{
                override fun onPositionChanged(position: Int) {
                    Log.e(TAG, "onPositionChanged: $position")
                    setFragment(position)
                }
            })
            btnperson.setOnClickListener {
                startActivity(Intent(context, PersonActivity::class.java ))
            }
            lnloBeeAction.setOnClickListener {
                lavbee.setAnimation(R.raw.bee)
                lavbee.playAnimation()
                lavbee.visibility = View.VISIBLE
                Handler().postDelayed({
                                        lavbee.visibility = View.GONE
                                      lavbee.pauseAnimation()
                                      },3000)
            }
        }
    }

    fun setFragment(pos: Int) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (pos == 0) {
            val fragment = AllFragment()
            fragmentTransaction.replace(R.id.homefragment, fragment)
            fragmentTransaction.commit()
        } else if (pos == 1) {
            val fragment = FruitFragment()
            fragmentTransaction.replace(R.id.homefragment, fragment)
            fragmentTransaction.commit()
        } else if (pos == 2) {
            val fragment = FoodFragment()
            fragmentTransaction.replace(R.id.homefragment, fragment)
            fragmentTransaction.commit()
        } else if (pos == 3) {
            val fragment = VegetableFragment()
            fragmentTransaction.replace(R.id.homefragment, fragment)
            fragmentTransaction.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPositionChanged(position: Int) {
        TODO("Not yet implemented")
    }
}