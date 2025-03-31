package com.example.foodorderingapp.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorderingapp.model.data.CategoryScreenData
import com.example.foodorderingapp.model.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    val categoryRepository: CategoryRepository
): ViewModel() {
    var categoryList= mutableStateListOf<CategoryScreenData>()
    //val categoryRepository=CategoryRepository()

    init {
        fetchCategoryData()
    }

    fun fetchCategoryData(){
        viewModelScope.launch{
            categoryRepository.fetchCategoryData({ fetchedCategoryList ->
                categoryList.clear()
                categoryList.addAll(fetchedCategoryList)
            })
        }
    }
}