package com.example.foodorderingapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorderingapp.model.data.HistoryOrders
import com.example.foodorderingapp.model.data.KitchenOrderData
import com.example.foodorderingapp.model.repository.KitchenOrderManagementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class KitchenOrderManagementViewModel @Inject constructor(
    val kitchenOrderManagementRepository: KitchenOrderManagementRepository
): ViewModel() {

    private val _ordersList=MutableStateFlow(listOf<KitchenOrderData>())
    private val _pendingOrderList=MutableStateFlow(listOf<KitchenOrderData>())
    private val _orderInProcessList=MutableStateFlow(listOf<KitchenOrderData>())
    private val _preparedOrderList=MutableStateFlow(listOf<KitchenOrderData>())

    val pendingOrderList: StateFlow<List<KitchenOrderData>> = _pendingOrderList
    val orderInProcessList: StateFlow<List<KitchenOrderData>> = _orderInProcessList
    val preparedOrderList: StateFlow<List<KitchenOrderData>> = _preparedOrderList

    private val _historyOrder = MutableStateFlow(listOf<HistoryOrders>())
    val historyOrder : StateFlow<List<HistoryOrders>> = _historyOrder

    init {
        fetchOrders()
        getHistoryOrders()
    }

    fun fetchOrders(){
        viewModelScope.launch {
            kitchenOrderManagementRepository.fetchData { onDataFetched ->
                _ordersList.value= onDataFetched

                _pendingOrderList.value=_ordersList.value.filter { it.status=="Pending"}.sortedByDescending { it.orderId.toLongOrNull() ?: Long.MAX_VALUE }
                _orderInProcessList.value=onDataFetched.filter { it.status=="Preparing"}.sortedByDescending { it.orderId.toLongOrNull() ?: Long.MAX_VALUE }
                _preparedOrderList.value=onDataFetched.filter { it.status=="Prepared"}.sortedByDescending { it.orderId.toLongOrNull() ?: Long.MAX_VALUE }
            }
        }
    }

    fun updateStatus(tableNumber:String,orderId:String,status:String){
        viewModelScope.launch {
            kitchenOrderManagementRepository.updateStatus(tableNumber,orderId, status)
        }
    }

    fun getHistoryOrders(){
        viewModelScope.launch {
            kitchenOrderManagementRepository.fetchHistoryOrders { onOrdersFetched ->
                _historyOrder.value=onOrdersFetched.reversed()
            }
        }
    }


}