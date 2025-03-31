package com.example.foodorderingapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.foodorderingapp.viewmodels.KitchenOrderManagementViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun detailedOrderScreenUI(
    orderId: String,
    tableNumber: String,
    status: String,
    orders: Map<String, Int>,
    navController: NavHostController,
    kitchenOrderManagementViewModel: KitchenOrderManagementViewModel
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                modifier = Modifier.clip(
                    RoundedCornerShape(
                        bottomStart = 35.dp,
                        bottomEnd = 35.dp
                    )
                ),
                navigationIcon = {
                    IconButton(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(30.dp),
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            tint = Color.Black
                        )
                    }
                },
                title = {
                    Text(
                        text = "Orders",
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        modifier = Modifier.width(280.dp),
                        fontFamily = FontFamily.Serif,
                        fontSize = 30.sp
                    )
                }
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .background(Color(0x121212)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Order Id:  ",
                            fontSize = 16.sp,
                            color = Color.White,
                            fontFamily = FontFamily.Serif
                        )
                        Text(
                            text = orderId,
                            fontSize = 20.sp,
                            color = Color.White,
                            fontFamily = FontFamily.Serif
                        )

                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Table No: ",
                            fontSize = 16.sp,
                            color = Color.White,
                            fontFamily = FontFamily.Serif
                        )
                        Text(
                            text = tableNumber,
                            fontSize = 20.sp,
                            color = Color.White,
                            fontFamily = FontFamily.Serif
                        )

                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    )
                    Text(
                        text = "ORDER",
                        fontSize = 17.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .background(Color.White.copy(0.4f))
                            .height(0.8.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(15.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(start = 2.dp, end = 2.dp)
                    ) {
                        item {
                            for (order in orders) {
                                Text(
                                    text = " ${order.key} - ${order.value}",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily.Serif
                                )
                            }
                            Spacer(Modifier.height(12.dp))
                        }


                    }
                    if (status != "Prepared") {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(bottom = 15.dp),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth(0.6f)
                                    .padding(bottom = 20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Green
                                ),
                                onClick = {
                                    if(status=="Pending"){
                                        kitchenOrderManagementViewModel.updateStatus(tableNumber,orderId,"Preparing")
                                    }else{
                                        kitchenOrderManagementViewModel.updateStatus(tableNumber,orderId,"Prepared")
                                    }

                                    navController.popBackStack() // edited on 29th march (to move back to order screen after pressing the button in detailed order screen)
                                }
                            ) {
                                if(status=="Pending"){
                                    Text(
                                        text = "Start",
                                        fontSize = 19.sp,
                                        color = Color.Black,
                                        fontFamily = FontFamily.Serif
                                    )
                                }else{
                                    Text(
                                        text = "Mark Prepared",
                                        fontSize = 19.sp,
                                        color = Color.Black,
                                        fontFamily = FontFamily.Serif
                                    )
                                }

                            }
                        }
                    }

                }
            }
        }
    )
}