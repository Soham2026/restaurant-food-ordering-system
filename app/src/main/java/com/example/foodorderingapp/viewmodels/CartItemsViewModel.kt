package com.example.foodorderingapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodorderingapp.model.data.CartItemData
import com.example.foodorderingapp.model.data.ItemScreenData
import com.example.foodorderingapp.model.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartItemsViewModel @Inject constructor(
    val itemRepository: ItemRepository
) : ViewModel() {
    var cartItems = mutableStateMapOf<String, CartItemData>()
    var cartItemsCount = mutableStateOf(0)
    var itemListFromCartMap = mutableStateListOf<String>()
    var totalBill = mutableStateOf(0.00)
    var isCartValid=mutableStateOf(true)
    var updatedCart= mutableMapOf<String,Int>()
    var parallelCartItems= mutableStateMapOf<String,CartItemData>()
    var parallelCartItemList = mutableStateListOf<String>()
    var netBillAmount by mutableStateOf(0.00)

    fun addToCart(item: ItemScreenData) {
        val name = item.itemName
        if (cartItems.containsKey(name)) {
            var count = cartItems[name]?.count ?: 0
            count++
            cartItems[name] = CartItemData(item, count)
        } else {
            cartItems[name] = CartItemData(item, 1)
            itemListFromCartMap.add(name)
        }
        cartItemsCount.value++
        calculateBill()
    }

    fun removeFromCart(item: ItemScreenData) {
        val name = item.itemName
        if (cartItems.containsKey(name)) {
            var count = cartItems[name]?.count ?: 0
            count--
            cartItemsCount.value--
            if (count == 0) {
                cartItems.remove(name)
               itemListFromCartMap.remove(name)
            } else {
                cartItems[name] = CartItemData(item, count)
            }
        }
        calculateBill()
    }

    fun deleteItemFromCart(itemName:String){
        cartItemsCount.value-=cartItems[itemName]?.count?: 0
        cartItems.remove(itemName)
        itemListFromCartMap.remove(itemName)
        calculateBill()
    }

    fun clearCart() {
        cartItems.clear()
        cartItemsCount.value=0
        itemListFromCartMap.clear()
        calculateBill()
    }
    fun clearOrder(){
        updatedCart.clear()
        parallelCartItems.clear()
        parallelCartItemList.clear()
        netBillAmount=0.0
        clearCart()
    }

    private fun calculateBill(){
        totalBill.value=0.00
        viewModelScope.launch {
            for( item in cartItems.values.toList()){
                totalBill.value += item.ItemData.price * item.count
            }
        }
    }

    fun validateItemsInCart(categoryName:String,itemName:String,onAvailabilityChecked:(Boolean) -> Unit){
         itemRepository.validateItemsInCart(categoryName,itemName){
             onAvailabilityChecked(it)
         }
    }

    fun flattenCartToOrder(){
        updatedCart.clear()
        for(item in cartItems.keys){
            updatedCart[item]=cartItems[item]!!.count
        }
    }

    fun parallelCart(){
        netBillAmount=0.0
        for(item in cartItems.keys){
            parallelCartItems[item]=cartItems[item]!!
        }
        parallelCartItemList.addAll(itemListFromCartMap)
        viewModelScope.launch {
            for(item in parallelCartItems.keys){
                netBillAmount+=(parallelCartItems[item]!!.ItemData.price)*(parallelCartItems[item]!!.count)
            }
        }
    }

}