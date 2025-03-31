package com.example.foodorderingapp.model.repository

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.example.foodorderingapp.model.data.RestaurantDeviceInfo
import com.example.foodorderingapp.model.datastore.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreRepository @Inject constructor() {
    val dataStoreManager = DataStoreManager()

    fun getInfo(
        viewModelScope: CoroutineScope,
        context: Context,
        onDataFetched: (RestaurantDeviceInfo) -> Unit
    ) {
        viewModelScope.launch {
            val deviceInfo = dataStoreManager.getDeviceInfo(context)
            onDataFetched(deviceInfo)
        }
    }

    fun updateInfo(
        viewModelScope: CoroutineScope,
        context: Context,
        restaurantName: String,
        isCustomerEnd: Boolean,
        tableNumber: Int
    ) {
        viewModelScope.launch {
            dataStoreManager.updateDeviceInfo(context,restaurantName,isCustomerEnd,tableNumber)
        }
    }

    fun updateTableNumber(
        viewModelScope: CoroutineScope,
        context:Context,
        tableNumber:Int
    ){
        viewModelScope.launch {
            dataStoreManager.updateTableNumber(context,tableNumber)
        }
    }
}