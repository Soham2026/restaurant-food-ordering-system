package com.example.foodorderingapp.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.util.Logger
import com.example.foodorderingapp.model.data.ItemScreenData
import com.example.foodorderingapp.model.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val itemRepository: ItemRepository
) : ViewModel() {

    val itemData = mutableStateListOf<ItemScreenData>()
    val categoryName=savedStateHandle["categoryName"]?:""



    init {
        fetchItemsdata(categoryName)
    }

    fun fetchItemsdata(categoryName: String) {
        viewModelScope.launch {
            itemRepository.fetchItemData(categoryName, { fetchedItemData ->
                itemData.clear()
                itemData.addAll(fetchedItemData)
            })
        }
    }



}