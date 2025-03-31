package com.example.foodorderingapp.view

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.splashscreen.SplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.foodorderingapp.R
import com.example.foodorderingapp.viewmodels.CartItemsViewModel
import com.example.foodorderingapp.viewmodels.DataStoreViewModel
import com.example.foodorderingapp.viewmodels.NavigationViewModel
import com.example.foodorderingapp.viewmodels.OnBoardScreenViewModel
import kotlinx.coroutines.delay


@Composable
fun splashScreenUI(
    context: Context,
    navController: NavHostController,
    onBoardScreenViewModel: OnBoardScreenViewModel,
    dataStoreViewModel: DataStoreViewModel,
    navViewModel: NavigationViewModel
) {
    // write a code to make a delay here
    LaunchedEffect(Unit) {
        delay(5000)
        dataStoreViewModel.fetchDeviceUsagePreference(context) { deviceUsagePreference ->

            if (deviceUsagePreference.restaurantName == "") {
                navViewModel.startDestination = "onBoardingScreen"
            } else {
                onBoardScreenViewModel.restaurantName=deviceUsagePreference.restaurantName
                if (deviceUsagePreference.isDeviceForCustomerEnd) {
                    onBoardScreenViewModel.tableNumber=deviceUsagePreference.tableNumber.toString()
                    navViewModel.startDestination = "StartScreen"
                } else {
                    navViewModel.startDestination = "kitchenCategoryScreen"
                }
            }
        }
        when (navViewModel.startDestination) {
            "onBoardingScreen" -> navController.navigate("onBoardingScreen")
            "StartScreen" -> navController.navigate("StartScreen")
            "kitchenScreen" -> navController.navigate("kitchenCategoryScreen")
            "SplashScreen" -> null
        }
    }

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
            painter = rememberAsyncImagePainter(
                model = "file:///android_asset/splashscreen.gif", // Correct assets path
                imageLoader = imageLoader
            ),
            contentDescription = "Animated GIF",
            contentScale = ContentScale.Crop

        )
    }

}