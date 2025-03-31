package com.example.foodorderingapp.model.repository

import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor() {
    var database =
        Firebase.database("https://restaurant-database-ee434-default-rtdb.asia-southeast1.firebasedatabase.app")

    fun placeOrder(
        cart: MutableMap<String, Int>,
        tableNumber: String,
        onOrderPlaced: (Boolean) -> Unit
    ) {
        val databaseReference = database.getReference("ORDERS").child("Table").child(tableNumber)

        val orderId = databaseReference.child(System.currentTimeMillis().toString())

        orderId.child("Status").setValue("Pending")

        orderId.child("Order").setValue(cart).addOnSuccessListener {
            onOrderPlaced(true)
        }.addOnFailureListener {
            onOrderPlaced(false)
        }
    }

    fun deleteOrder(tableNumber: String, totalAmount: Double) {
        val deleteRef = database.getReference("ORDERS").child("Table").child(tableNumber)
        saveOrderToHistory(totalAmount, deleteRef)
        deleteRef.removeValue()
    }

    fun saveOrderToHistory(totalAmount: Double, orderRef: DatabaseReference) {
        val orderHistoryRef = database.getReference("ORDER_HISTORY")
        val pushId = orderHistoryRef.push().key

        val orderMap = mutableMapOf<String, Int>()

        orderRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children) {

                    val orders = snap.child("Order")
                    for (item in orders.children) {
                        val itemName = item.key ?: ""
                        val qty = item.getValue(Int::class.java) ?: 0
                        orderMap[itemName] = orderMap.getOrDefault(itemName, 0) + qty
                    }
                }
                orderHistoryRef.child(pushId.toString()).child("Order").setValue(orderMap)
                orderHistoryRef.child(pushId.toString()).child("Total").setValue(totalAmount)
                orderHistoryRef.child(pushId.toString()).child("Date").setValue(LocalDate.now().toString())
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

}