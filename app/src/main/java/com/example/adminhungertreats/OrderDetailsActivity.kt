 package com.example.adminhungertreats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminhungertreats.adapter.OrderDetailsAdapter
import com.example.adminhungertreats.adapter.PendingOrderAdapter
import com.example.adminhungertreats.databinding.ActivityOrderDetailsBinding
import com.example.adminhungertreats.model.OrderDetails
import java.util.ArrayList

 class OrderDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderDetailsBinding
    private var userName : String ?=null
     private var address : String?=null
     private var phoneNumber:String?=null
     private var totalPrice : String?=null
     private var foodNames : ArrayList<String> = arrayListOf()
     private var foodImages : ArrayList<String> = arrayListOf()
     private var foodQuantity : ArrayList<Int> = arrayListOf()
     private var foodPrices : ArrayList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }
        getDataFromIntent()
    }

     private fun getDataFromIntent() {
        val receiveOrderDetails = intent.getSerializableExtra("UserOrderDetails") as OrderDetails
         receiveOrderDetails?.let{orderDetails ->
                 userName = receiveOrderDetails.userName
                 foodNames = receiveOrderDetails.foodNames as ArrayList<String>
                 foodImages = receiveOrderDetails.foodImages as ArrayList<String>
                 foodQuantity = receiveOrderDetails.foodQuantities as ArrayList<Int>
                 address = receiveOrderDetails.address
                 phoneNumber = receiveOrderDetails.phoneNumber
                 foodPrices = receiveOrderDetails.foodPrices as ArrayList<String>
                 totalPrice = receiveOrderDetails.totalPrice

                 setUserDetails()
                 setAdapter()

         }

     }

     private fun setAdapter() {
        binding.orderDetailsRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = OrderDetailsAdapter(this,foodNames,foodImages,foodQuantity,foodPrices)
        binding.orderDetailsRecyclerView.adapter = adapter
     }

     private fun setUserDetails() {
         binding.name.text = userName
         binding.address.text = address
         binding.phone.text = phoneNumber
         binding.totalPay.text = totalPrice
     }
 }