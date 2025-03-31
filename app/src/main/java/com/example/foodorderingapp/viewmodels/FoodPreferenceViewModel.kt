package com.example.foodorderingapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class FoodPreferenceViewModel: ViewModel() {

    var foodPreference =  mutableStateOf(false)


}