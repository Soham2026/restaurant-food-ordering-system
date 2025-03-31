package com.example.foodorderingapp.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import com.example.foodorderingapp.viewmodels.CartItemsViewModel
import com.example.foodorderingapp.viewmodels.OnBoardScreenViewModel
import com.example.foodorderingapp.viewmodels.OrderViewModel
import kotlinx.coroutines.delay

@Composable
fun orderPlacingScreenUI(
    context: Context,
    navController: NavHostController,
    orderViewModel: OrderViewModel,
    cartItemsViewModel: CartItemsViewModel,
    onBoardScreenViewModel: OnBoardScreenViewModel
) {

    LaunchedEffect(Unit) {
        cartItemsViewModel.flattenCartToOrder()
        delay(2200)
        orderViewModel.placeOrder(
            cartItemsViewModel.updatedCart,
            onBoardScreenViewModel.tableNumber
        ){ isOrderPlaced ->

            if(isOrderPlaced){
                // move to order placed screen
                navController.popBackStack("cartValidateScreen",true)
                navController.navigate("orderConfirmedScreen")
                cartItemsViewModel.parallelCart()
                cartItemsViewModel.clearCart()

            }else{
                navController.popBackStack("orderPlacingScreen",true)
                navController.popBackStack("cartValidateScreen",true)
                navController.navigate("CartScreen")
            }
        }
    }

    //
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {

        val imageLoader = ImageLoader.Builder(context)
            .components {
                add(ImageDecoderDecoder.Factory())
            }
            .build()

        Image(
            modifier = Modifier.size(330.dp, 400.dp),
            painter = rememberAsyncImagePainter(
                model = "file:///android_asset/orderplacinggif.gif",
                imageLoader = imageLoader
            ),
            contentDescription = "Animated GIF",
            contentScale = ContentScale.Crop

        )
    }
}