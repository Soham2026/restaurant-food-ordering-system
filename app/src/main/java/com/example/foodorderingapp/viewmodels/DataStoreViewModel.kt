package com.example.foodorderingapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorderingapp.model.data.RestaurantDeviceInfo
import com.example.foodorderingapp.model.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    val dataStoreRepository:DataStoreRepository
):ViewModel() {

     var deviceInfo:RestaurantDeviceInfo=RestaurantDeviceInfo()

    fun fetchDeviceUsagePreference(context: Context,onDataFetched:(RestaurantDeviceInfo) -> Unit){
        dataStoreRepository.getInfo(viewModelScope,context){ fetchedDeviceInfo ->
            onDataFetched(fetchedDeviceInfo)
        }
    }

    fun storeDeviceUsagePreference(context: Context,restaurantName:String,isCustomerEnd:Boolean,tableNumber:Int){
        dataStoreRepository.updateInfo(viewModelScope,context,restaurantName,isCustomerEnd,tableNumber)
    }

    fun changeTableNumber(context:Context,tableNumber: Int){
        dataStoreRepository.updateTableNumber(viewModelScope,context,tableNumber)
    }

}