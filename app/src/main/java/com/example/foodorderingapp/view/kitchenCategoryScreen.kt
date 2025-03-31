package com.example.foodorderingapp.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
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
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController  //
import com.example.foodorderingapp.R
import com.example.foodorderingapp.model.data.ItemScreenData
import com.example.foodorderingapp.model.repository.CategoryRepository
import com.example.foodorderingapp.viewmodels.KitchenCategoryViewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun kitchenCategoryScreenUI(
    navController: NavHostController,
    kitchenCategoryViewmodel: KitchenCategoryViewmodel
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var isDeletionDialogShown by rememberSaveable { mutableStateOf(false) }
    var categoryNameToBeDeleted by rememberSaveable { mutableStateOf("") }
    val categoryList = kitchenCategoryViewmodel.categoryList.collectAsState()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

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
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier.clip(
                        RoundedCornerShape(
                            bottomStart = 20.dp,
                            bottomEnd = 20.dp
                        )
                    ),
                    navigationIcon = {
                        IconButton(
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .size(30.dp),
                            onClick = {
                                coroutineScope.launch {
                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }

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
                            text = "Categories",
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

                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(R.drawable.bg_photo),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.7f))
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                                .padding(3.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(
                                count = categoryList.value.size,
                                itemContent = { index ->
                                    OutlinedCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 7.dp, end = 7.dp, top = 27.dp)
                                            .height(100.dp),
                                        elevation = CardDefaults.outlinedCardElevation(5.dp),
                                        shape = RoundedCornerShape(20.dp),
                                        colors = CardDefaults.outlinedCardColors(
                                            containerColor = Color.Transparent.copy(0.6f)
                                        )
                                    ) {
                                        Box(Modifier.fillMaxSize()) {
                                            Box(
                                                modifier = Modifier
                                                    .fillParentMaxSize()
                                                    .blur(14.dp)
                                            )
                                            Row(
                                                Modifier.fillMaxSize(),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {

                                                Column(
                                                    modifier = Modifier
                                                        .fillParentMaxHeight()
                                                        .weight(0.6f)
                                                        .padding(start = 13.dp, top = 9.dp)
                                                        .combinedClickable(
                                                            onLongClick = {
                                                                categoryNameToBeDeleted =
                                                                    categoryList.value[index].categoryName
                                                                isDeletionDialogShown = true
                                                            },
                                                            onClick = {
                                                                // navigate to that particular category items
                                                                navController.navigate("kitchenItemScreen/${categoryList.value[index].categoryName}")
                                                            }
                                                        ),
                                                    horizontalAlignment = Alignment.Start,
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = categoryList.value[index].categoryName,
                                                        fontSize = 19.sp,
                                                        color = Color.White,
                                                        fontFamily = FontFamily.Serif,
                                                        textAlign = TextAlign.Left,
                                                        softWrap = true,
                                                        modifier = Modifier
                                                    )
                                                    Spacer(modifier = Modifier.height(35.dp))
                                                }


                                                Switch(
                                                    checked = categoryList.value[index].isAvailable,
                                                    onCheckedChange = {
                                                        val name =
                                                            categoryList.value[index].categoryName
                                                        val availabilityStatus =
                                                            !(categoryList.value[index].isAvailable)
                                                        kitchenCategoryViewmodel.updateCategory(
                                                            categoryName = name,
                                                            isAvailable = availabilityStatus
                                                        )
                                                    },
                                                    colors = SwitchDefaults.colors(
                                                        checkedTrackColor = Color.White,
                                                        checkedThumbColor = Color(68, 255, 0),
                                                        uncheckedThumbColor = Color.Gray,
                                                        uncheckedTrackColor = Color.White
                                                    ),
                                                    modifier = Modifier
                                                        .weight(0.2f)
                                                        .padding(end = 10.dp, top = 9.dp)
                                                )
                                            }

                                        }

                                    }
                                }
                            )

                            item {
                                Spacer(modifier = Modifier.height(110.dp))
                            }
                        }
                    }
                }

            },
            floatingActionButton = {
                val isCategory = true
                FloatingActionButton(
                    onClick = {
                        navController.navigate("addToMenu/$isCategory/NO_VALUE_NEEDED")
                    },
                    shape = RoundedCornerShape(40.dp),
                    containerColor = Color.White,
                    elevation = FloatingActionButtonDefaults.elevation(10.dp),
                    modifier = Modifier.size(72.dp)
                ) {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(46.dp)
                    )
                }
            },
        )

        if (isDeletionDialogShown == true) {
            deleteItemDialog(
                isCategory = true,
                categoryName = categoryNameToBeDeleted,
                kitchenCategoryViewmodel = kitchenCategoryViewmodel
            ) { isDeletionDialogShown = false }
        }

    }
}

@Composable
fun navigationDrawerItemsForKitchenUI(
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController
) {
    var currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 50.dp, end = 10.dp),

        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Reset",
            fontSize = 22.sp,
            color = Color.White,
            softWrap = true,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp)
                .clickable(onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    navController.navigate("onBoardingScreen")
                })
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(13.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.9.dp)
                .background(Color.White.copy(alpha = 0.4f), shape = RoundedCornerShape(10.dp))
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(13.dp)
        )

        Text(
            text = "Manage Items",
            fontSize = 22.sp,
            color = Color.White,
            softWrap = true,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp)
                .clickable(onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    if (currentScreen != "kitchenCategoryScreen")
                        navController.navigate("kitchenCategoryScreen")  // categories screen
                })
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(13.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.9.dp)
                .background(Color.White.copy(alpha = 0.4f), shape = RoundedCornerShape(10.dp))
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(13.dp)
        )

        Text(
            text = "Orders",
            fontSize = 22.sp,
            color = Color.White,
            softWrap = true,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp)
                .clickable(onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    if (currentScreen != "orderManagementScreen")
                        navController.navigate("orderManagementScreen")
                })
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(13.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.9.dp)
                .background(Color.White.copy(alpha = 0.4f), shape = RoundedCornerShape(10.dp))
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(13.dp)
        )

        Text(
            text = "Order Records",
            fontSize = 22.sp,
            color = Color.White,
            softWrap = true,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp)
                .clickable(onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    if (currentScreen != "orderRecordsScreen")  // change chk to records screen
                        navController.navigate("orderRecordsScreen")  // move to records screen
                })
        )


    }
}

