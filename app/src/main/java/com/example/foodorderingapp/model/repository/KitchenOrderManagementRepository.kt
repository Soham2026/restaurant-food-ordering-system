package com.example.foodorderingapp.model.repository

import androidx.compose.animation.core.snap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodorderingapp.model.data.HistoryOrders
import com.example.foodorderingapp.model.data.KitchenOrderData
import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KitchenOrderManagementRepository @Inject constructor() {
    val database = Firebase.database("https://restaurant-database-ee434-default-rtdb.asia-southeast1.firebasedatabase.app")
    val tableReference = database.getReference("ORDERS").child("Table")
    val listnerForEachTable = mutableMapOf<String, ValueEventListener>()

    val allOrderList = mutableListOf<KitchenOrderData>()

    fun fetchData(onDataFetched: (List<KitchenOrderData>) -> Unit) {
        tableReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val tableNumber = snapshot.key

                if (listnerForEachTable.containsKey(tableNumber) == false) {
                    val valueEventListner = object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {

                            val orderList = mutableListOf<KitchenOrderData>()
                            for (orderSnapShot in snapshot.children) {
                                val id = orderSnapShot.key

                                val status =
                                    orderSnapShot.child("Status").getValue(String::class.java)
                                val orders = orderSnapShot.child("Order")
                                    .getValue(object : GenericTypeIndicator<Map<String, Int>>() {})

                                orderList.add(
                                    KitchenOrderData(
                                        tableNumber!!,
                                        id!!,
                                        status!!,
                                        orders
                                    )
                                )
                            }

                            allOrderList.removeAll {
                                it.tableNumber == tableNumber
                            }
                            allOrderList.addAll(orderList)
                            onDataFetched(allOrderList.toList())
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    }

                    tableReference.child(tableNumber!!).addValueEventListener(valueEventListner)
                    listnerForEachTable[tableNumber!!] = valueEventListner
                }

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val tableNumber = snapshot.key
                listnerForEachTable[tableNumber]?.let {
                    tableReference.child(tableNumber!!).removeEventListener(it)
                    listnerForEachTable.remove(tableNumber)
                }


            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    fun updateStatus(tableNumber: String, orderId: String, status: String) {
        val updateReference = tableReference.child(tableNumber).child(orderId).child("Status")

        updateReference.setValue(status)
    }

    fun fetchHistoryOrders(onOrderFetched:(List<HistoryOrders>) -> Unit){
        val orderHistoryRef = database.getReference("ORDER_HISTORY")


        orderHistoryRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val orderList= mutableListOf<HistoryOrders>()
                for (order in snapshot.children){
                    val orderId=order.key?:""
                    val totalamount=order.child("Total").getValue(Double::class.java)?: 0.00
                    val date=order.child("Date").getValue(String::class.java)?:""
                    val orders= mutableMapOf<String,Int>()

                    for( item in order.child("Order").children){
                        val itemName=item.key?:""
                        val qty=item.getValue(Int::class.java)?:0
                        orders[itemName]=qty
                    }

                    val order=HistoryOrders(orderId,totalamount,orders,date)
                    orderList.add(order)
                }

                onOrderFetched(orderList)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}