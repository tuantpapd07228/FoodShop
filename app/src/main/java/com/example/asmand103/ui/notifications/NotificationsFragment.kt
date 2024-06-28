package com.example.asmand103.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.asmand103.adapter.OrderAdapter
import com.example.asmand103.databinding.FragmentNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: OrderAdapter

    lateinit var viewModel : NotificationsViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setView()
       viewModel.getOrder()
        return root
    }

    private fun setView() {
        binding.apply {
            rcv.adapter = adapter
            viewModel.listResponseOrder.observe(viewLifecycleOwner, Observer {
                if (it != null){
                    adapter.differ.submitList(it.order)
                    rcv.adapter =adapter
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}