package com.example.foodorderingapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.foodorderingapp.viewmodels.DataStoreViewModel
import com.example.foodorderingapp.viewmodels.OnBoardScreenViewModel

@Composable
fun tableChangeUI(
    navController:NavHostController,
    onBoardScreenViewModel: OnBoardScreenViewModel,
    dataStoreViewModel: DataStoreViewModel
) {
    val context = LocalContext.current
    val existingTableNumber = onBoardScreenViewModel.tableNumber
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = onBoardScreenViewModel.tableNumber,
            onValueChange = { newNumber ->
                onBoardScreenViewModel.tableNumber = newNumber
            },
            placeholder = {
                Text(
                    text=existingTableNumber,
                    color = Color.LightGray,
                    fontSize = 17.sp,
                    fontFamily = FontFamily.Serif
                )
            },
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 17.sp,
                fontFamily = FontFamily.Serif
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.size(350.dp, 68.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color.White.copy(alpha = 0.9f)
            )
        )

        Spacer(modifier = Modifier.size(1.dp,30.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            onClick = {
                dataStoreViewModel.changeTableNumber(context,onBoardScreenViewModel.tableNumber.toInt())
                navController.navigate("StartScreen"){
                    popUpTo(0){inclusive=true}
                }
            }
        ) {
            Text(
                text="Update",
                color = Color.Black,
                fontSize = 17.sp,
                fontFamily = FontFamily.Serif
            )
        }
    }
}