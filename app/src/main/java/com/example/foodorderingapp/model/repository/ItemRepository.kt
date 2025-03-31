package com.example.foodorderingapp.model.repository

import android.net.Uri
import android.util.Log
import com.example.foodorderingapp.model.data.ItemScreenData
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.storage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor() {
    var database = Firebase.database("https://restaurant-database-ee434-default-rtdb.asia-southeast1.firebasedatabase.app")
    val firebaseStorageReference = Firebase.storage.reference

    fun fetchItemData(categoryName: String, onItemDataFetched: (List<ItemScreenData>) -> Unit) {
        var itemReference = database.getReference("CATEGORIES").child(categoryName).child("items")

        itemReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemDataList = mutableListOf<ItemScreenData>()
                for (dataSnapShot in snapshot.children) {
                    val itemName = dataSnapShot.key ?: ""
                    val isAvailable =
                        dataSnapShot.child("isAvailable").getValue(Boolean::class.java) ?: false
                    val imageUrl = dataSnapShot.child("image").getValue(String::class.java) ?: ""
                    val price = dataSnapShot.child("price").getValue(Double::class.java) ?: 0.0
                    val isVeg = dataSnapShot.child("isVeg").getValue(Boolean::class.java) ?: true

                    itemDataList.add(
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

                onItemDataFetched(itemDataList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun validateItemsInCart(
        categoryName: String,
        itemName: String,
        onAvailabilityChecked: (Boolean) -> Unit
    ) {
        var isAvailable = false
        var itemReference =
            database.getReference("CATEGORIES").child(categoryName).child("items").child(itemName)
                .child("isAvailable")

        itemReference.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                isAvailable = snapshot.getValue(Boolean::class.java) ?: true
                onAvailabilityChecked(isAvailable)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun uploadItemImage(
        imageUri: Uri,
        categoryName: String,
        itemName: String,
        onImageUploaded: (Uri) -> Unit
    ) {
        val categoryFolderRef = firebaseStorageReference.child("$categoryName/$itemName.jpeg")

        categoryFolderRef.putFile(imageUri).addOnSuccessListener {
            categoryFolderRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                onImageUploaded(downloadUrl)
            }
        }.addOnFailureListener {

        }

    }

    fun uploadItemData(data: ItemScreenData, onDataUploaded: (Boolean) -> Unit) {
        Log.d("tag", "Item category is ${data.itemCategory}")
        val uploadReference =
            database.getReference("CATEGORIES").child(data.itemCategory).child("items")
                .child(data.itemName)

        val uploadData = mapOf(
            "isAvailable" to data.isAvailable,
            "isVeg" to data.isVeg,
            "image" to data.itemImageUrl,
            "price" to data.price
        )

        uploadReference.updateChildren(uploadData).addOnSuccessListener {
            onDataUploaded(true)
        }.addOnFailureListener {
            onDataUploaded(false)
        }

    }

    fun updateItemData(
        categoryName: String,
        itemName: String,
        imageUri: Uri? = null,
        isAvailable: Boolean? = null,
        isVeg: Boolean? = null,
        price: Double? = null
    ) {
        var updateRef =
            database.getReference("CATEGORIES").child(categoryName).child("items").child(itemName)

        if (imageUri != null) {
            uploadItemImage(imageUri, categoryName, itemName) { downloadUrl ->
                updateRef.child("image").setValue(downloadUrl.toString())
            }
        }

        if (isAvailable != null) {
            updateRef.child("isAvailable").setValue(isAvailable)
        }

        if (isVeg != null) {
            updateRef.child("isPureVeg").setValue(isVeg)
        }

        if (price != null) {
            updateRef.child("price").setValue(price)
        }
    }

    fun deleteItem(categoryName: String, itemName: String) {
        val deleteRef =
            database.getReference("CATEGORIES").child(categoryName).child("items").child(itemName)
        deleteRef.removeValue()
    }

}