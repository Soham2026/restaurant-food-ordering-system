package com.example.foodorderingapp.model.datastore

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodorderingapp.model.data.RestaurantDeviceInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DataStoreManager() {

    //creating the datastore instance
    val  Context.dataStore:DataStore<Preferences> by preferencesDataStore(name="DataStore")
    //var restaurantDeviceInfo:RestaurantDeviceInfo=RestaurantDeviceInfo()

    companion object{
        val restaurantName= stringPreferencesKey("restaurantName")
        val isDeviceCustomerEnd= booleanPreferencesKey("deviceEnd")
        val tableNumber= intPreferencesKey("tableNumber")
    }

    suspend fun getDeviceInfo(context: Context):RestaurantDeviceInfo{

        val data=context.dataStore.data.first()

        return RestaurantDeviceInfo(
            restaurantName = data[restaurantName]?:"",
            isDeviceForCustomerEnd = data[isDeviceCustomerEnd]?:true,
            tableNumber = data[tableNumber]?:0
        )
    }

    suspend fun updateDeviceInfo(context:Context,restName:String,isCustEnd:Boolean,tableNum:Int=0){

        context.dataStore.edit { settings ->
            settings[restaurantName]=restName
            settings[isDeviceCustomerEnd]=isCustEnd
            settings[tableNumber]=tableNum
        }
    }

    suspend fun updateTableNumber(context:Context,tableNum:Int){
        context.dataStore.edit { settings ->
            settings[tableNumber]=tableNum
        }
    }

}