package com.example.foodorderingapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class BottomSheetViewModel: ViewModel(){
    var isBottomSheetOpen= mutableStateOf(false)
}