package com.example.foodorderingapp.view

import android.content.Context
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodorderingapp.R
import com.example.foodorderingapp.viewmodels.CartItemsViewModel
import com.example.foodorderingapp.viewmodels.DataStoreViewModel
import com.example.foodorderingapp.viewmodels.FoodPreferenceViewModel
import com.example.foodorderingapp.viewmodels.ItemsViewModel
import com.example.foodorderingapp.viewmodels.KitchenCategoryViewmodel
import com.example.foodorderingapp.viewmodels.KitchenItemsViewModel
import com.example.foodorderingapp.viewmodels.KitchenOrderManagementViewModel
import com.example.foodorderingapp.viewmodels.NavigationViewModel
import com.example.foodorderingapp.viewmodels.OnBoardScreenViewModel
import com.example.foodorderingapp.viewmodels.OrderViewModel
import com.example.foodorderingapp.viewmodels.PaymentViewModel
import com.example.foodorderingapp.viewmodels.SearchBarViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Composable
fun navigation(
    context: Context,
    navController: NavHostController,
    dataStoreViewModel: DataStoreViewModel,
    cartItemsViewModel: CartItemsViewModel,
    onBoardScreenViewModel: OnBoardScreenViewModel,
    navViewModel: NavigationViewModel,
    startDestination: String,
    kitchenCategoryViewmodel: KitchenCategoryViewmodel,
    paymentViewModel: PaymentViewModel
) {

    var viewModel: FoodPreferenceViewModel = viewModel()
    val searchBarViewModel:SearchBarViewModel= hiltViewModel()
    val kitchenOrderManagementViewModel: KitchenOrderManagementViewModel = hiltViewModel()


    NavHost(
        navController = navController,
        startDestination = startDestination

    ) {
        composable(
            "StartScreen",
            exitTransition = {
                slideOutHorizontally(animationSpec = tween(950), targetOffsetX = { -it })
            }

        ) {
            startScreenUI(
                painterResource(R.drawable.bg_photo),
                navController,
                onBoardScreenViewModel
            )
        }

        composable("CategoryScreen") {
            categoryScreenUI(
                viewModel,
                painterResource(R.drawable.bg_photo),
                navController,
                cartItemsViewModel
            )
        }

        composable(
            "ItemScreen/{categoryName}/{isPureVeg}",
            arguments = listOf(navArgument("isPureVeg") { type = NavType.BoolType }),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                )
            }
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            val isPureVeg = backStackEntry.arguments?.getBoolean("isPureVeg") ?: false
            val itemsViewModel: ItemsViewModel = hiltViewModel()  // moved this from the local composable
            itemScreenUI(
                viewModel,
                painterResource(R.drawable.bg_photo),
                navController,
                categoryName,
                isPureVeg,
                itemsViewModel,
                cartItemsViewModel,
                searchBarViewModel
            )
        }

        composable("CartScreen") {
            val itemsViewModel: ItemsViewModel = hiltViewModel()
            cartScreenUI(navController, cartItemsViewModel, itemsViewModel, onBoardScreenViewModel)
        }

        composable("onBoardingScreen") {
            onBoardingScreenUI(navController, onBoardScreenViewModel, dataStoreViewModel)
        }

        composable("SplashScreen") {
            splashScreenUI(
                context,
                navController,
                onBoardScreenViewModel,
                dataStoreViewModel,
                navViewModel
            )
        }

        composable("tableUpdateScreen") {
            tableChangeUI(navController, onBoardScreenViewModel, dataStoreViewModel)
        }

        composable("cartValidateScreen") {
            validateCartItemsUI(context, navController, cartItemsViewModel)
        }

        composable("orderPlacingScreen") {
            val orderViewModel: OrderViewModel = hiltViewModel()
            orderPlacingScreenUI(
                context,
                navController,
                orderViewModel,
                cartItemsViewModel,
                onBoardScreenViewModel
            )
        }

        composable("orderConfirmedScreen") {
            orderConfirmedScreenUI(navController)
        }

        composable("billScreen") {
            billScreenUI(navController, cartItemsViewModel,onBoardScreenViewModel,paymentViewModel)
        }

        composable("kitchenCategoryScreen") {

            kitchenCategoryScreenUI(navController, kitchenCategoryViewmodel)
        }

        composable("kitchenItemScreen/{categoryName}",
            enterTransition = {
                //fadeIn(animationSpec = tween(500))+ scaleIn(initialScale = 0.9f,animationSpec = tween(500, easing = FastOutSlowInEasing))
                slideInHorizontally(
                    initialOffsetX = { it }, // Moves slightly, not fully
                    animationSpec = tween(550, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(500))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it }, // Moves slightly, not fully
                    animationSpec = tween(550, easing = FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            val kitchenItemsViewModel: KitchenItemsViewModel = hiltViewModel()
            val categoryName = it.arguments?.getString("categoryName") ?: ""
            kitchenItemScreenUI(navController, kitchenItemsViewModel, categoryName)
        }

        composable("addToMenu/{isCategory}/{categoryName}",
            arguments = listOf(navArgument("isCategory") { type = NavType.BoolType }),
            enterTransition = {
                //fadeIn(animationSpec = tween(500))+ scaleIn(initialScale = 0.9f,animationSpec = tween(500, easing = FastOutSlowInEasing))
                slideInHorizontally(
                    initialOffsetX = { it }, // Moves slightly, not fully
                    animationSpec = tween(550, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(500))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it }, // Moves slightly, not fully
                    animationSpec = tween(550, easing = FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            val kitchenItemsViewModel: KitchenItemsViewModel = hiltViewModel()
            val isCategory = it.arguments?.getBoolean("isCategory")
            if (isCategory == true) {
                addItemOrCategory(
                    isCategory = true,
                    navController = navController,
                    kitchenCategoryViewmodel = kitchenCategoryViewmodel
                )
            } else {
                addItemOrCategory(
                    isCategory = false,
                    navController = navController,
                    kitchenItemsViewModel = kitchenItemsViewModel
                )
            }

        }

        composable("orderManagementScreen") {
            orderManagementScreenUI(navController, kitchenOrderManagementViewModel)
        }

        composable("detailedOrderScreen/{orderId}/{tableNumber}/{status}/{orders}",
            arguments = listOf(navArgument("orders") { type = NavType.StringType }),
            enterTransition = {
                //fadeIn(animationSpec = tween(500))+ scaleIn(initialScale = 0.9f,animationSpec = tween(500, easing = FastOutSlowInEasing))
                slideInHorizontally(
                    initialOffsetX = { it }, // Moves slightly, not fully
                    animationSpec = tween(550, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(500))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it }, // Moves slightly, not fully
                    animationSpec = tween(550, easing = FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(300))
            })
        {
            val orderId = it.arguments?.getString("orderId") ?: ""
            val tableNumber = it.arguments?.getString("tableNumber") ?: ""
            val status = it.arguments?.getString("status") ?: ""
            val json_orders = it.arguments?.getString("orders") ?: "{}"

            val orders: Map<String, Int> =
                Gson().fromJson(json_orders, object : TypeToken<Map<String, Int>>() {}.type)
            detailedOrderScreenUI(
                orderId,
                tableNumber,
                status,
                orders,
                navController,
                kitchenOrderManagementViewModel
            )
        }

        composable("searchBarScreen",
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { it/2 }, // Starts from the bottom
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing))
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { it }, // Moves down while fading out
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing))
            }
            ) {
            searchBarScreenUI(
                navController,
                searchBarViewModel,
                cartItemsViewModel,
                viewModel,
            )
        }

        composable("orderRecordsScreen") {
            orderRecordsScreenUI(navController, kitchenOrderManagementViewModel)
        }


    }
}
