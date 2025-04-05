package com.example.foodorderingapp.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BottomSheetViewModel @Inject constructor(): ViewModel(){
    var isBottomSheetOpen= mutableStateOf(false)
    var selectedItemIndex=-1
    init {
        Log.d("BottomSheetViewModel", "New BottomSheetViewModel instance created!")
    }
}