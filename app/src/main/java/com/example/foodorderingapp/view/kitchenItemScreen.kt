package com.example.foodorderingapp.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.foodorderingapp.R
import com.example.foodorderingapp.viewmodels.KitchenItemsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun kitchenItemScreenUI(
    navController:NavHostController,
    kitchenItemsViewModel: KitchenItemsViewModel,
    categoryName:String
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var isDeletionDialogShown by rememberSaveable { mutableStateOf(false) }
    var itemNameToBeDeleted by rememberSaveable { mutableStateOf("") }
    val itemList = kitchenItemsViewModel.itemList.collectAsState()

    val scrollBehavior= TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

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
                            text = categoryName,
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
                                count = itemList.value.size,
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
                                                                itemNameToBeDeleted =
                                                                    itemList.value[index].itemName
                                                                isDeletionDialogShown = true
                                                            },
                                                            onClick = {
                                                                // navigate to that particular category items
                                                            }
                                                        ),
                                                    horizontalAlignment = Alignment.Start,
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = itemList.value[index].itemName,
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
                                                    checked = itemList.value[index].isAvailable,
                                                    onCheckedChange = {
                                                        val name=itemList.value[index].itemName
                                                        val availabilityStatus=!(itemList.value[index].isAvailable)
                                                        kitchenItemsViewModel.updateItemData(itemName = name, isAvailable = availabilityStatus)
                                                    },
                                                    colors = SwitchDefaults.colors(
                                                        checkedTrackColor = Color.White,
                                                        checkedThumbColor = Color(68,255,0),
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
                val isCategory = false
                FloatingActionButton(
                    onClick = {
                        navController.navigate("addToMenu/$isCategory/$categoryName")
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

        if(isDeletionDialogShown==true){
            deleteItemDialog(isCategory = false, itemName = itemNameToBeDeleted, kitchenItemsViewModel = kitchenItemsViewModel){isDeletionDialogShown=false}
        }

    }
}