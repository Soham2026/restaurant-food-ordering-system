package com.example.foodorderingapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardScreenViewModel @Inject constructor():ViewModel() {
    var pagesCount by mutableStateOf(3)
    var restaurantName by mutableStateOf("")
    var selectedOption by mutableStateOf(0)
    var isNextVisible by mutableStateOf(true)
    var tableNumber by mutableStateOf("")
}