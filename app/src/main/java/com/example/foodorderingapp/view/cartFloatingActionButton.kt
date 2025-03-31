package com.example.foodorderingapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.decode.ImageSource
import com.example.foodorderingapp.R
import com.example.foodorderingapp.viewmodels.CartItemsViewModel

@Composable
fun cartFloatingActionButton(navController: NavController, cartItemsViewModel: CartItemsViewModel) {
    FloatingActionButton(
        onClick = {
            navController.navigate("cartScreen")
        },
        containerColor = Color.Black.copy(alpha = 0.9f),
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .border(
                3.dp,
                Color.White.copy(alpha = 0.7f),
                shape = RoundedCornerShape(50.dp)
            )
            .size(80.dp),
        elevation = FloatingActionButtonDefaults.elevation(50.dp)
    ) {
        Column {
            Text(
                text = "${cartItemsViewModel.cartItemsCount.value}",
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 16.sp,
                fontFamily = FontFamily.Serif
            )
            Icon(
                Icons.Outlined.ShoppingCart,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterHorizontally)
            )

        }
    }
}