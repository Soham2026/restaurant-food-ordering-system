package com.example.foodorderingapp.model.repository

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.foodorderingapp.R
import com.example.foodorderingapp.model.data.CategoryScreenData
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.storage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor() {
    val database =
        Firebase.database("https://restaurant-database-ee434-default-rtdb.asia-southeast1.firebasedatabase.app")
    val categoryReference = database.getReference("CATEGORIES")
    val firebaseStorageReference = Firebase.storage.reference

    fun fetchCategoryData(onFetchedData: (List<CategoryScreenData>) -> Unit) {

        categoryReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val categoryDataList = mutableListOf<CategoryScreenData>()
                for (dataSnapShot in snapshot.children) {
                    val categoryName = dataSnapShot.key ?: ""
                    val isAvailable =
                        dataSnapShot.child("isAvailable").getValue(Boolean::class.java) ?: false
                    val imageUrl = dataSnapShot.child("image").getValue(String::class.java) ?: ""
                    val isPureVeg =
                        dataSnapShot.child("isPureVeg").getValue(Boolean::class.java) ?: false
                    categoryDataList.add(
                        CategoryScreenData(
                            categoryName,
                            isAvailable,
                            imageUrl,
                            isPureVeg
                        )
                    )
                }
                onFetchedData(categoryDataList)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Firebase Error: ${error.message}")
            }
        }
        )
    }

    fun uploadCategoryImage(imageUri: Uri, categoryName: String, onImageUploaded: (Uri) -> Unit) {
        val categoryFolderRef = firebaseStorageReference.child("Categories/$categoryName.jpeg")

        categoryFolderRef.putFile(imageUri).addOnSuccessListener {
            categoryFolderRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                onImageUploaded(downloadUrl)
            }
        }.addOnFailureListener {

        }

    }

    fun uploadCategoryData(data: CategoryScreenData, onDataUploaded:(Boolean) -> Unit) {

        val uploadReference = categoryReference.child(data.categoryName)

        val updateData=mapOf(
            "isAvailable" to data.isAvailable,
            "isPureVeg" to data.isPureVeg,
            "image" to data.categoryImageUrl
        )
        uploadReference.updateChildren(updateData).addOnSuccessListener {
            onDataUploaded(true)
        }.addOnFailureListener{
            onDataUploaded(false)
        }
    }

    fun updateCategoryData(categoryName:String,imageUri:Uri?=null,isAvailable:Boolean?=null,isPureVeg:Boolean?=null){
        val ref=categoryReference.child(categoryName)
        if(imageUri!=null){
            uploadCategoryImage(imageUri,categoryName){downloadUrl ->
                ref.child("image").setValue(downloadUrl.toString())
            }
        }

        if(isAvailable!=null){
            ref.child("isAvailable").setValue(isAvailable)
        }

        if(isPureVeg!=null){
            ref.child("isPureVeg").setValue(isPureVeg)
        }
    }

    fun deleteCategoryData(categoryName:String){
        val ref=categoryReference.child(categoryName)
        ref.removeValue()
    }



}