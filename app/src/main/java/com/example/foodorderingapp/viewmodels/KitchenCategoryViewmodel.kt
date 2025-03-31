package com.example.foodorderingapp.viewmodels

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorderingapp.model.data.CategoryScreenData
import com.example.foodorderingapp.model.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KitchenCategoryViewmodel @Inject constructor(
    val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _categoryList = MutableStateFlow(listOf<CategoryScreenData>())
    val categoryList: StateFlow<List<CategoryScreenData>> = _categoryList

    init {
        fetchCategoryData()
    }

    fun fetchCategoryData() {
        viewModelScope.launch {
            categoryRepository.fetchCategoryData { fetchedData ->
                _categoryList.value = fetchedData
            }
        }
    }

    fun uploadCategory(
        imageUri: Uri,
        categoryName: String,
        isAvailable: Boolean,
        isPureVeg: Boolean,
        onDataUploaded: (Boolean) -> Unit
    ) {

        viewModelScope.launch {
            categoryRepository.uploadCategoryImage(imageUri, categoryName) { downloadUrl ->
                val categoryImageUrl = downloadUrl.toString()
                categoryRepository.uploadCategoryData(
                    CategoryScreenData(
                        categoryName,
                        isAvailable,
                        categoryImageUrl,
                        isPureVeg
                    )
                ) { isDataUploaded ->
                    onDataUploaded(isDataUploaded)
                }
            }

        }
    }


    fun updateCategory(categoryName:String,imageUri:Uri?=null,isAvailable:Boolean?=null,isPureVeg:Boolean?=null){
        viewModelScope.launch {
            categoryRepository.updateCategoryData(categoryName, imageUri, isAvailable,isPureVeg)
        }
    }

    fun deleteCategory(categoryName:String){
        viewModelScope.launch {
            categoryRepository.deleteCategoryData(categoryName)
        }
    }




}