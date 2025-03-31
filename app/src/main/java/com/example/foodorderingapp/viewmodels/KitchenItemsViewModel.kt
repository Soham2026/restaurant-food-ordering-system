package com.example.foodorderingapp.viewmodels

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorderingapp.model.data.ItemScreenData
import com.example.foodorderingapp.model.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KitchenItemsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val itemRepository: ItemRepository
) : ViewModel() {

    val categoryName = savedStateHandle["categoryName"] ?: ""

    private val _itemList = MutableStateFlow(listOf<ItemScreenData>())
    val itemList: StateFlow<List<ItemScreenData>> = _itemList

    init {
        fetchItemData()
    }

    fun fetchItemData() {
        viewModelScope.launch {
            itemRepository.fetchItemData(categoryName) { fetchedItems ->
                _itemList.value = fetchedItems
            }
        }
    }

    fun uploadItemData(
        imageUri: Uri,
        itemName: String,
        isAvailable: Boolean,
        isVeg: Boolean,
        price: Double,
        onDataUploaded: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            itemRepository.uploadItemImage(imageUri, categoryName, itemName) { downloadUrl ->
                val itemImageUrl = downloadUrl.toString()
                itemRepository.uploadItemData(
                    ItemScreenData(itemName, itemImageUrl, isAvailable, price, isVeg, categoryName)
                ) { isDataUploaded ->
                    onDataUploaded(isDataUploaded)
                }

            }
        }
    }

    fun updateItemData(
        itemName: String,
        imageUri: Uri? = null,
        isAvailable: Boolean? = null,
        isVeg: Boolean? = null,
        price: Double? = null
    ) {
        viewModelScope.launch {
            itemRepository.updateItemData(
                categoryName,
                itemName,
                imageUri,
                isAvailable,
                isVeg,
                price
            )
        }
    }

    fun deleteItem(itemName: String) {
        viewModelScope.launch {
            itemRepository.deleteItem(categoryName, itemName)
        }
    }
}