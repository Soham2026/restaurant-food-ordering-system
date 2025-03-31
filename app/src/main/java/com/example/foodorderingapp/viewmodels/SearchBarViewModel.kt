package com.example.foodorderingapp.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorderingapp.model.data.ItemScreenData
import com.example.foodorderingapp.model.repository.SearchBarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchBarViewModel @Inject constructor(
    val searchBarRepository: SearchBarRepository
): ViewModel() {

    val allItems = mutableStateListOf<ItemScreenData>()

    init {
        fetchAllItems()
    }

    fun fetchAllItems(){
        viewModelScope.launch {
            searchBarRepository.fetchAllItems { onAllItemsFetched ->
                allItems.clear()
                allItems.addAll(onAllItemsFetched)
            }
        }
    }
}