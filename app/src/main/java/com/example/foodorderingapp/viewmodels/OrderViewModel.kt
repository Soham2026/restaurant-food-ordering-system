package com.example.foodorderingapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.foodorderingapp.model.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    val orderRepository: OrderRepository
):ViewModel() {

    fun placeOrder(cart: MutableMap<String, Int>, tableNumber: String,onOrderPlaced:(Boolean)->Unit){
        orderRepository.placeOrder(cart, tableNumber){ isOrderPlaced ->
            onOrderPlaced(isOrderPlaced)
        }
    }
}