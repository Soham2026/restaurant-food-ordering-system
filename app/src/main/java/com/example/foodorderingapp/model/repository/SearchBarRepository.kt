package com.example.foodorderingapp.model.repository

import com.example.foodorderingapp.model.data.ItemScreenData
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchBarRepository @Inject constructor() {
    var database = Firebase.database("https://restaurant-database-ee434-default-rtdb.asia-southeast1.firebasedatabase.app")
    fun fetchAllItems(onItemsFetched: (List<ItemScreenData>) -> Unit) {

        val ref = database.getReference("CATEGORIES")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val allItemList = mutableListOf<ItemScreenData>()
                for (categorySnapshot in snapshot.children) {
                    val categoryName = categorySnapshot.key ?: ""

                    val itemsSnapShot = categorySnapshot.child("items")
                    val categoryItemList = mutableListOf<ItemScreenData>()
                    for (itemSnapshot in itemsSnapShot.children) {
                        val itemName = itemSnapshot.key ?: ""
                        val isAvailable =
                            itemSnapshot.child("isAvailable").getValue(Boolean::class.java) ?: false
                        val imageUrl =
                            itemSnapshot.child("image").getValue(String::class.java) ?: ""
                        val price = itemSnapshot.child("price").getValue(Double::class.java) ?: 0.0
                        val isVeg =
                            itemSnapshot.child("isVeg").getValue(Boolean::class.java) ?: true

                        categoryItemList.add(
                            ItemScreenData(
                                itemName,
                                imageUrl,
                                isAvailable,
                                price,
                                isVeg,
                                categoryName
                            )
                        )
                    }
                    allItemList.removeAll {
                        it.itemCategory == categoryName
                    }
                    allItemList.addAll(categoryItemList)
                }
                onItemsFetched(allItemList)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}