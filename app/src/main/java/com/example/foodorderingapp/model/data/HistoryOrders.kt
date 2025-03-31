package com.example.foodorderingapp.model.data

data class HistoryOrders (
    val orderId:String,
    val totalAmount:Double,
    val orders:Map<String,Int>,
    val date:String
)