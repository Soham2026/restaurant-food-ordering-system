package com.example.foodorderingapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foodorderingapp.ui.theme.FoodOrderingAppTheme
import com.example.foodorderingapp.view.categoryScreenUI
import com.example.foodorderingapp.view.navigation
import com.example.foodorderingapp.view.splashScreenUI
import com.example.foodorderingapp.view.startScreenUI
import com.example.foodorderingapp.viewmodels.CartItemsViewModel
import com.example.foodorderingapp.viewmodels.DataStoreViewModel
import com.example.foodorderingapp.viewmodels.KitchenCategoryViewmodel
import com.example.foodorderingapp.viewmodels.NavigationViewModel
import com.example.foodorderingapp.viewmodels.OnBoardScreenViewModel
import com.example.foodorderingapp.viewmodels.PaymentViewModel
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultWithDataListener {

    lateinit var paymentViewModel: PaymentViewModel

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodOrderingAppTheme {
                val navController = rememberNavController()
                val cartItemsViewModel: CartItemsViewModel = hiltViewModel()
                val dataStoreViewModel: DataStoreViewModel = hiltViewModel()
                val navViewModel: NavigationViewModel = hiltViewModel()
                val onBoardScreenViewModel: OnBoardScreenViewModel = hiltViewModel()
                val context = LocalContext.current
                val kitchenCategoryViewmodel: KitchenCategoryViewmodel = hiltViewModel()
                paymentViewModel = hiltViewModel()

                //RAZOR PAY KEY INITIALIZATION
                Checkout.preload(applicationContext)
                val checkout = Checkout()
                checkout.setKeyID("rzp_test_JyemVqBCN2SsiS")

                navigation(
                    context,
                    navController,
                    dataStoreViewModel,
                    cartItemsViewModel,
                    onBoardScreenViewModel,
                    navViewModel,
                    navViewModel.startDestination,
                    kitchenCategoryViewmodel,
                    paymentViewModel
                )
            }
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        paymentViewModel.onSuccessHandler()
        Toast.makeText(this, "Payment Complete : ${p0}", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        paymentViewModel.resetPaymentStatus()
        Toast.makeText(this, "Payment Failed : ${p1}", Toast.LENGTH_SHORT).show()
        Log.d("payment", "${p1}")
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FoodOrderingAppTheme {

    }
}