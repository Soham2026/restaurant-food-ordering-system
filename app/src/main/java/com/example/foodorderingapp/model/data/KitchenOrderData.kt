package com.example.foodorderingapp.model.data

data class KitchenOrderData(
    val tableNumber:String,
    val orderId:String,
    var status:String,
    val orders:Map<String,Int>?
)
