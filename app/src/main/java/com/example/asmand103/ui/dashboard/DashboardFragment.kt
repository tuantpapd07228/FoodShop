package com.example.asmand103.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.asmand103.R
import com.example.asmand103.adapter.CartAdapter
import com.example.asmand103.adapter.CartAdapterListener1
import com.example.asmand103.databinding.FragmentDashboardBinding
import com.example.asmand103.request.GHNRequest
import com.example.asmand103.response.Cart
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment(), CartAdapterListener1 {

    private var _binding: FragmentDashboardBinding? = null
    @Inject
    lateinit var adapter: CartAdapter
    lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var listFruit : MutableList<Cart>
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        dashboardViewModel.getListCart()
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setView(dashboardViewModel)
        adapter.setItemListener(this@DashboardFragment)
        return root
    }

    private fun setView(dashboardViewModel : DashboardViewModel) {
        binding.apply {
            rcv.adapter = adapter
            dashboardViewModel.listCart.observe(viewLifecycleOwner, Observer {
                listFruit = it as MutableList<Cart>
                adapter.differ.submitList(it)
                adapter.notifyDataSetChanged()
                if (it.isEmpty()){
                    lottie.visibility = View.VISIBLE
                    tvemty.visibility = View.VISIBLE
                }else{
                    lottie.visibility = View.GONE
                    tvemty.visibility = View.GONE
                }
            })
            dashboardViewModel.status.observe(viewLifecycleOwner, Observer {
                if (it){
                    Snackbar.make(root, "Đã đặt hàng thành công", Snackbar.LENGTH_LONG).show()
                }
            })
            dashboardViewModel.statusDelItem.observe(viewLifecycleOwner, Observer {
                if (it){
                    Snackbar.make(root, "Gỡ sản phẩm khỏi giỏ hàng", Snackbar.LENGTH_LONG).show()
                }
            })
            btnpayment.setOnClickListener {

                val jsonString = """
    {
        "payment_type_id": 2,
        "note": "Hàng dễ vở",
        "required_note": "KHONGCHOXEMHANG",
        "from_name": "Tuan TPA",
        "from_phone": "0987654321",
        "from_address": "72 Thành Thái, Hoa Minh, Thanh Khe, Da Nang, Vietnam",
        "from_ward_name": "Phường 14",
        "from_district_name": "Quận 10",
        "from_province_name": "HCM",
        "return_phone": "0332190444",
        "return_address": "39 NTT",
        "return_district_id": null,
        "return_ward_code": "",
        "client_order_code": "",
        "to_name": "Nguyen Van Hung",
        "to_phone": "0987654321",
        "to_address": "150 Thành Thái, Phường 14, Quận 10, Hồ Chí Minh, Vietnam",
        "to_ward_code": "20308",
        "to_district_id": 1444,
        "cod_amount": 175000,
        "content": "Theo New York Times",
        "weight": 200,
        "length": 1,
        "width": 19,
        "height": 10,
        "pick_station_id": 1444,
        "deliver_station_id": null,
        "insurance_value": 10000000,
        "service_id": 0,
        "service_type_id": 2,
        "coupon": null,
        "pick_shift": [
            2
        ],
        "items": [
            {
                "name": "Thực phẩm",
                "code": "Polo123",
                "quantity": 1,
                "price": 195000,
                "length": 12,
                "width": 12,
                "height": 12,
                "weight": 1200,
                "category": {
                    "level1": "thực phẩm"
                }
            }
        ]
    }
    """

                val ghnRequest = Gson().fromJson(jsonString, GHNRequest::class.java)



                dashboardViewModel.createOrder(ghnRequest = ghnRequest)
                listFruit.map {
                    dashboardViewModel.addOrder(it.fruit, it.quantity)
                }
                dashboardViewModel.addCartSucc.observe(viewLifecycleOwner, Observer {
                    if (it){
                        dashboardViewModel.delAllCart()
                    }
                })

            }
            lottie.setAnimation(R.raw.nothing)
            lottie.playAnimation()
            dashboardViewModel.orderSuccess.observe(viewLifecycleOwner, Observer {
                if (it){
//                    Snackbar.make(root, "Order thành công", Snackbar.LENGTH_LONG).show()
                }
            })
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onItemClicked() {
        dashboardViewModel.getListCart()

    }

    override fun updateQuantity(id: String, quantity: Int) {
        dashboardViewModel.updateQuantityInCart(id,quantity)
    }


}