package com.example.foodorderingapp.view

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foodorderingapp.R
import com.example.foodorderingapp.model.data.KitchenOrderData
import com.example.foodorderingapp.model.repository.KitchenOrderManagementRepository
import com.example.foodorderingapp.viewmodels.KitchenOrderManagementViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun orderManagementScreenUI(
    navController: NavHostController,
    kitchenOrderManagementViewModel: KitchenOrderManagementViewModel
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var pagesCount = 3
    var pagerState = rememberPagerState(initialPage = 0, pageCount = { pagesCount })


    val pendingOrderList = kitchenOrderManagementViewModel.pendingOrderList.collectAsState()
    val orderInProcessList = kitchenOrderManagementViewModel.orderInProcessList.collectAsState()
    val preparedOrderList = kitchenOrderManagementViewModel.preparedOrderList.collectAsState()


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
                        Spacer(Modifier.height(22.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.9f),
                            horizontalArrangement = Arrangement.spacedBy(
                                35.dp,
                                Alignment.CenterHorizontally
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(27.dp)
                                    .width(90.dp)
                                    .clickable {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(0)
                                        }
                                    }
                                    .background(
                                        color = if (pagerState.currentPage == 0) Color.Magenta.copy(
                                            0.3f
                                        ) else Color.Transparent,
                                        RoundedCornerShape(10.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Pending",
                                    color = Color.White,
                                    fontSize = 17.sp,
                                    fontFamily = FontFamily.Serif,
                                    textAlign = TextAlign.Center,
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .height(27.dp)
                                    .width(100.dp)
                                    .clickable {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(1)
                                        }
                                    }
                                    .background(
                                        color = if (pagerState.currentPage == 1) Color.Magenta.copy(
                                            0.3f
                                        ) else Color.Transparent,
                                        RoundedCornerShape(10.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Preparing",
                                    color = Color.White,
                                    fontSize = 17.sp,
                                    fontFamily = FontFamily.Serif,
                                    textAlign = TextAlign.Center,
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .height(27.dp)
                                    .width(100.dp)
                                    .clickable {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(2)
                                        }
                                    }
                                    .background(
                                        color = if (pagerState.currentPage == 2) Color.Magenta.copy(
                                            0.3f
                                        ) else Color.Transparent,
                                        RoundedCornerShape(10.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Prepared",
                                    color = Color.White,
                                    fontSize = 17.sp,
                                    fontFamily = FontFamily.Serif,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                        Spacer(Modifier.height(10.dp))
                        Spacer(
                            Modifier
                                .height(1.dp)
                                .fillMaxWidth(0.97f)
                                .background(
                                    Color.White.copy(0.3f),
                                    RoundedCornerShape(20.dp)
                                )
                        )
                        Spacer(Modifier.height(10.dp))
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .padding(start = 3.dp, end = 3.dp)
                                .fillMaxWidth()
                                .fillMaxHeight(0.95f)
                        ) { page ->

                            when (page) {
                                0 -> horizontalPagerContent(
                                    navController,
                                    kitchenOrderManagementViewModel,
                                    pendingOrderList,
                                    "Pending"
                                )

                                1 -> horizontalPagerContent(
                                    navController,
                                    kitchenOrderManagementViewModel,
                                    orderInProcessList,
                                    "Preparing"
                                )

                                2 -> horizontalPagerContent(
                                    navController,
                                    kitchenOrderManagementViewModel,
                                    preparedOrderList,
                                    "Prepared"
                                )
                            }
                        }

                    }
                }

            },
        )
    }
}

@Composable
fun PendingListView(
    navController: NavHostController,
    kitchenOrderManagementViewModel: KitchenOrderManagementViewModel,
    orderId: String,
    tableNumber: String,
    orders: Map<String, Int>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 5.dp, end = 5.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Order Id: ",
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = orderId,
                        fontSize = 17.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Serif
                    )

                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Table No: ",
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = tableNumber,
                        fontSize = 17.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Serif
                    )

                }

            }
            Spacer(Modifier.height(5.dp))
            Spacer(Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.Black.copy(0.3f)))
            Spacer(Modifier.height(11.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 10.dp, end = 10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                for (order in orders) {
                    Text(
                        text = " ${order.key} - ${order.value}",
                        color = Color.Black,
                        fontSize = 15.5.sp,
                        fontFamily = FontFamily.Serif
                    )
                }
            }
            val jsonOrder = Uri.encode(Gson().toJson(orders))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .padding(start = 12.dp)
                    .clickable { navController.navigate("detailedOrderScreen/$orderId/$tableNumber/Pending/$jsonOrder") },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Show more",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = FontFamily.Serif
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(0.6f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green
                ),
                onClick = {
                    kitchenOrderManagementViewModel.updateStatus(tableNumber, orderId, "Preparing")
                }
            ) {
                Text(
                    text = "Start",
                    fontSize = 17.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Serif
                )
            }
            Spacer(Modifier.height(10.dp))
        }
    }
}


@Composable
fun PreparingListView(
    navController: NavHostController,
    kitchenOrderManagementViewModel: KitchenOrderManagementViewModel,
    orderId: String,
    tableNumber: String,
    orders: Map<String, Int>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 5.dp, end = 5.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Order Id: ",
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = orderId,
                        fontSize = 17.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Serif
                    )

                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Table No: ",
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = tableNumber,
                        fontSize = 17.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Serif
                    )

                }
            }
            Spacer(Modifier.height(5.dp))
            Spacer(Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.Black.copy(0.3f)))
            Spacer(Modifier.height(11.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 10.dp, end = 10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                for (order in orders) {
                    Text(
                        text = " ${order.key} - ${order.value}",
                        color = Color.Black,
                        fontSize = 15.5.sp,
                        fontFamily = FontFamily.Serif
                    )
                }
            }
            val jsonOrder = Uri.encode(Gson().toJson(orders))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .padding(start = 12.dp)
                    .clickable { navController.navigate("detailedOrderScreen/$orderId/$tableNumber/Preparing/$jsonOrder") },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Show more",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = FontFamily.Serif
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(0.6f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green
                ),
                onClick = {
                    kitchenOrderManagementViewModel.updateStatus(tableNumber, orderId, "Prepared")
                }
            ) {
                Text(
                    text = "Mark Prepared",
                    fontSize = 17.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Serif
                )
            }
            Spacer(Modifier.height(10.dp))
        }
    }
}


@Composable
fun PreparedListView(
    navController: NavHostController,
    kitchenOrderManagementViewModel: KitchenOrderManagementViewModel,
    orderId: String,
    tableNumber: String,
    orders: Map<String, Int>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 5.dp, end = 5.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Order Id: ",
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = orderId,
                        fontSize = 17.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Serif
                    )

                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Table No: ",
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = tableNumber,
                        fontSize = 17.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Serif
                    )

                }
            }
            Spacer(Modifier.height(5.dp))
            Spacer(Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.Black.copy(0.3f)))
            Spacer(Modifier.height(11.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 10.dp, end = 10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                for (order in orders) {
                    Text(
                        text = " ${order.key} - ${order.value}",
                        color = Color.Black,
                        fontSize = 15.5.sp,
                        fontFamily = FontFamily.Serif
                    )
                }
            }
            val jsonOrder = Uri.encode(Gson().toJson(orders))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .padding(start = 12.dp)
                    .clickable { navController.navigate("detailedOrderScreen/$orderId/$tableNumber/Prepared/$jsonOrder") },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Show more",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = FontFamily.Serif
                )
            }

        }
    }
}

@Composable
fun horizontalPagerContent(
    navController: NavHostController,
    kitchenOrderManagementViewModel: KitchenOrderManagementViewModel,
    list: State<List<KitchenOrderData>>,
    state: String
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.95f)
    ) {
        items(
            count = list.value.size,
            itemContent = {
                val orders= list.value[it].orders?: emptyMap()
                if (state == "Pending") {
                    PendingListView(
                        navController,
                        kitchenOrderManagementViewModel,
                        list.value[it].orderId,
                        list.value[it].tableNumber,
                        orders
                    )
                } else if (state == "Preparing") {
                    PreparingListView(
                        navController,
                        kitchenOrderManagementViewModel,
                        list.value[it].orderId,
                        list.value[it].tableNumber,
                        orders
                    )
                } else {
                    PreparedListView(
                        navController,
                        kitchenOrderManagementViewModel,
                        list.value[it].orderId,
                        list.value[it].tableNumber,
                        orders
                    )
                }
                Spacer(Modifier.height(18.dp))
            }
        )
    }
}