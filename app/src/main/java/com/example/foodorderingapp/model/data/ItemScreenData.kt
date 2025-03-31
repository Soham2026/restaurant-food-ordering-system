package com.example.foodorderingapp.model.data

data class ItemScreenData(
    val itemName:String,
    val itemImageUrl:String,
    val isAvailable:Boolean,
    val price:Double,
    val isVeg:Boolean,
    val itemCategory:String
)
