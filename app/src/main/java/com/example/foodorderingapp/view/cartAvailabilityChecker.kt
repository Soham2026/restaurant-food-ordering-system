package com.example.foodorderingapp.view

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import com.example.foodorderingapp.viewmodels.CartItemsViewModel
import kotlinx.coroutines.delay

@Composable
fun validateCartItemsUI(
    context: Context,
    navController: NavHostController,
    cartItemsViewModel: CartItemsViewModel
) {
    var updatedCart= mutableMapOf<String,Int>()
    var isCartValid = true
    val appContext= LocalContext.current.applicationContext
    LaunchedEffect(Unit) {
        delay(1600)
        for (item in cartItemsViewModel.cartItems.keys.toList()) {

            var itemName = item
            var categoryName = cartItemsViewModel.cartItems[item]?.ItemData!!.itemCategory

            cartItemsViewModel.validateItemsInCart(categoryName, itemName) { isItemAvailable ->
                if (isItemAvailable == false) {
                    cartItemsViewModel.deleteItemFromCart(itemName)
                    cartItemsViewModel.isCartValid.value = false
                    isCartValid = false
                }else{
                    updatedCart[itemName]=cartItemsViewModel.cartItems[itemName]!!.count
                }
            }
            delay(900)
        }

        if (isCartValid == true) {
            cartItemsViewModel.isCartValid.value = true
            navController.navigate("orderPlacingScreen")
        } else {
            Toast.makeText(appContext,"Some Items were removed from cart due to unavailability !!",Toast.LENGTH_LONG).show()
            navController.popBackStack()
        }
    }
    //show the loading screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {

        val imageLoader = ImageLoader.Builder(context)
            .components {
                add(ImageDecoderDecoder.Factory())  // No need to pass context explicitly
            }
            .build()

        Image(
            modifier = Modifier.size(330.dp, 400.dp),
            painter = rememberAsyncImagePainter(
                model = "file:///android_asset/cartValidateScreenLoader.gif", // Correct assets path
                imageLoader = imageLoader
            ),
            contentDescription = "Animated GIF",
            contentScale = ContentScale.Crop

        )
    }
}