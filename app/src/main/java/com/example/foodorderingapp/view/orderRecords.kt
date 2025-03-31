package com.example.foodorderingapp.view

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.compose.rememberNavController
import com.example.foodorderingapp.model.repository.KitchenOrderManagementRepository
import com.example.foodorderingapp.viewmodels.KitchenOrderManagementViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun orderRecordsScreenUI(
    navController: NavHostController,
    kitchenOrderManagementViewModel: KitchenOrderManagementViewModel
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val orderRecordList = kitchenOrderManagementViewModel.historyOrder.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxWidth(0.65f),
                drawerShape = RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp),
                drawerContainerColor = Color.Black.copy(alpha = 0.8f)
            ) {
                navigationDrawerItemsForKitchenUI(drawerState, coroutineScope, navController)
            }
        }
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
                                coroutineScope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                Icons.Rounded.Menu,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                tint = Color.Black
                            )
                        }
                    },
                    title = {
                        Text(
                            text = "  Order Records",
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
                        Spacer(Modifier.height(22.dp))

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(2.dp)
                                .background(Color(0x121212)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(
                                count = orderRecordList.value.size,
                                itemContent = { index ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .padding(start = 5.dp, end = 5.dp),
                                        shape = RoundedCornerShape(20.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White
                                        ),
                                        border = BorderStroke(0.9.dp,Color.Gray)
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 5.dp, end = 5.dp)
                                                .background(Color.White),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Spacer(Modifier.height(15.dp))

                                            Row(
                                                modifier = Modifier.padding(start = 7.dp).fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start
                                            ) {
                                                Text(
                                                    text = "Order Id: ",
                                                    fontSize = 12.sp,
                                                    color = Color.Black,
                                                    fontFamily = FontFamily.Serif
                                                )
                                                Text(
                                                    text = orderRecordList.value[index].orderId,
                                                    fontSize = 17.sp,
                                                    color = Color.Black,
                                                    fontFamily = FontFamily.Serif
                                                )
                                            }
                                            Spacer(Modifier.height(12.dp))

                                            Row(
                                                modifier = Modifier.padding(start = 1.dp).fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ){
                                                Row(
                                                    modifier = Modifier.padding(start = 7.dp),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Start
                                                ) {
                                                    Text(
                                                        text = "Amount:  ",
                                                        fontSize = 13.sp,
                                                        color = Color.Black,
                                                        fontFamily = FontFamily.Serif
                                                    )
                                                    Text(
                                                        text = "Rs "+orderRecordList.value[index].totalAmount.toString(),
                                                        fontSize = 17.sp,
                                                        color = Color.Black,
                                                        fontWeight = FontWeight.Bold,
                                                        fontFamily = FontFamily.Serif
                                                    )

                                                }

                                                Row(
                                                    modifier = Modifier.padding(end = 2.dp),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Start
                                                ) {
                                                    Text(
                                                        text = "Date: ",
                                                        fontSize = 12.sp,
                                                        color = Color.Black,
                                                        fontFamily = FontFamily.Serif
                                                    )
                                                    Text(
                                                        text = orderRecordList.value[index].date.toString(),
                                                        fontSize = 17.sp,
                                                        color = Color.Black,
                                                        fontFamily = FontFamily.Serif
                                                    )

                                                }
                                            }


                                            Spacer(Modifier.height(5.dp))
                                            Spacer(
                                                Modifier
                                                    .height(1.dp)
                                                    .fillMaxWidth()
                                                    .background(Color.Black.copy(0.3f))
                                            )
                                            Spacer(Modifier.height(11.dp))
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(120.dp)
                                                    .padding(start = 10.dp, end = 10.dp)
                                                    .verticalScroll(rememberScrollState(), true),
                                                horizontalAlignment = Alignment.Start
                                            ) {
                                                for (order in orderRecordList.value[index].orders) {
                                                    Text(
                                                        text = " ${order.key} - ${order.value}",
                                                        color = Color.Black,
                                                        fontSize = 15.5.sp,
                                                        fontFamily = FontFamily.Serif
                                                    )
                                                }
                                            }

                                        }
                                    }
                                    Spacer(Modifier.height(20.dp))
                                }
                            )

                        }


                    }
                }

            },
        )
    }

}