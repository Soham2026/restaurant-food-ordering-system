package com.example.foodorderingapp.view

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.foodorderingapp.R
//import com.example.foodorderingapp.processPayment
import com.example.foodorderingapp.viewmodels.CartItemsViewModel
import com.example.foodorderingapp.viewmodels.OnBoardScreenViewModel
import com.example.foodorderingapp.viewmodels.PaymentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun billScreenUI(
    navController: NavHostController,
    cartItemsViewModel: CartItemsViewModel,
    onBoardScreenViewModel: OnBoardScreenViewModel,
    paymentViewModel: PaymentViewModel
) {
    val context = LocalContext.current.applicationContext
    val context2 = LocalContext.current
    val activity = context2 as? Activity ?: return
    var paymentStatus = paymentViewModel.paymentStatus.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.bg_photo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.7f),
                            Color.Black.copy(alpha = 0.9f)
                        )
                    )
                )
        ) {
            Scaffold(modifier = Modifier
                .padding(top = 15.dp),
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                        modifier = Modifier
                            .wrapContentSize(),
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        ),
                        navigationIcon = {
                            IconButton(modifier = Modifier
                                .size(40.dp)
                                .padding(start = 2.dp, end = 3.dp),
                                onClick = {
                                    navController.popBackStack()
                                }) {
                                Icon(
                                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                    contentDescription = "",
                                    modifier = Modifier.fillMaxSize(),
                                    tint = Color.White
                                )
                            }
                        },
                        title = {
                            Text(
                                text = "Bill",
                                fontSize = 34.sp,
                                color = Color.White,
                                fontFamily = FontFamily.Cursive,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(start = 7.dp),
                            )
                        },
                        actions = {
                            Row(
                                modifier = Modifier
                                    .padding(top = 5.dp, end = 13.dp)
                                    .fillMaxHeight(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Table: ${onBoardScreenViewModel.tableNumber}",
                                    fontSize = 27.sp,
                                    color = Color.White,
                                    fontFamily = FontFamily.Cursive,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    )
                },
                content = {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState(), true),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.size(1.dp, 7.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                                .padding(start = 27.dp, end = 27.dp, top = 10.dp),
                            colors = CardDefaults.cardColors(Color.Black.copy(alpha = 0.6f)),
                            shape = RoundedCornerShape(30.dp),
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.9f))

                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Spacer(modifier = Modifier.size(1.dp, 7.dp))
                                Text(
                                    text = "Orders",
                                    fontFamily = FontFamily.Cursive,
                                    fontSize = 30.sp,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.size(1.dp, 3.dp))
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(0.7.dp)
                                        .padding(start = 15.dp, end = 15.dp)
                                        .background(Color.White.copy(alpha = 0.5f))
                                )

                                Spacer(modifier = Modifier.size(1.dp, 7.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp, end = 16.dp)
                                ) {
                                    Text(
                                        text = "Items",
                                        fontFamily = FontFamily.Cursive,
                                        fontSize = 19.sp,
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .width(160.dp)
                                    )
                                    Text(
                                        text = "Qty",
                                        fontFamily = FontFamily.Cursive,
                                        fontSize = 19.sp,
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .width(60.dp)
                                    )
                                    Text(
                                        text = "Amt",
                                        fontFamily = FontFamily.Cursive,
                                        fontSize = 19.sp,
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .width(95.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.size(1.dp, 3.dp))
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(0.7.dp)
                                        .padding(start = 15.dp, end = 15.dp)
                                        .background(Color.White.copy(alpha = 0.5f))
                                )

                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    items(
                                        count = cartItemsViewModel.parallelCartItemList.size,
                                        itemContent = { index ->

                                            var itemName =
                                                cartItemsViewModel.parallelCartItemList[index]
                                            var itemCount =
                                                cartItemsViewModel.parallelCartItems[itemName]!!.count
                                            var itemRate =
                                                cartItemsViewModel.parallelCartItems[itemName]!!.ItemData.price

                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(start = 16.dp, end = 16.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = itemName,
                                                    fontFamily = FontFamily.Serif,
                                                    fontSize = 17.sp,
                                                    color = Color.White,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .width(160.dp),
                                                    softWrap = true
                                                )

                                                Text(
                                                    text = itemCount.toString(),
                                                    fontFamily = FontFamily.Serif,
                                                    fontSize = 16.sp,
                                                    color = Color.White,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .width(60.dp)
                                                )
                                                Text(
                                                    text = "${(itemRate * itemCount).toDouble()}",
                                                    fontFamily = FontFamily.Serif,
                                                    fontSize = 17.sp,
                                                    color = Color.White,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .width(95.dp)
                                                )
                                            }

                                            Spacer(modifier = Modifier.size(1.dp, 5.dp))
                                        }
                                    )

                                }


                            }

                        }
                        Spacer(modifier = Modifier.size(1.dp, 3.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(start = 36.dp, end = 36.dp, top = 10.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total Bill : ",
                                fontSize = 31.sp,
                                color = Color.White,
                                fontFamily = FontFamily.Cursive
                            )
                            Text(
                                text = "${cartItemsViewModel.netBillAmount}",
                                fontSize = 29.sp,
                                color = Color.White,
                                fontFamily = FontFamily.Cursive
                            )
                        }

                        Spacer(modifier = Modifier.size(1.dp, 30.dp))

                        OutlinedButton(
                            border = BorderStroke(1.dp, Color.White),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.Black.copy(alpha = 0.75f)
                            ),
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .wrapContentHeight(),
                            onClick = {
                                paymentViewModel.processPayment(
                                    activity,
                                    cartItemsViewModel.netBillAmount
                                )
                            }
                        ) {
                            Text(
                                text = "Pay",
                                fontFamily = FontFamily.Cursive,
                                fontSize = 32.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        LaunchedEffect(paymentStatus.value) {
                            if (paymentStatus.value == "Success") {
                                Toast.makeText(context, "Payment Successful", Toast.LENGTH_SHORT)
                                    .show()
                                paymentViewModel.clearTable(onBoardScreenViewModel.tableNumber,cartItemsViewModel.netBillAmount)
                                cartItemsViewModel.clearOrder()
                                paymentViewModel.resetPaymentStatus()
                                navController.navigate("StartScreen") {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        }

                    }
                }
            )

        }
    }

}