package com.example.foodorderingapp.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.foodorderingapp.R
import com.example.foodorderingapp.model.data.ItemScreenData
import com.example.foodorderingapp.viewmodels.BottomSheetViewModel
import com.example.foodorderingapp.viewmodels.CartItemsViewModel
import com.example.foodorderingapp.viewmodels.FoodPreferenceViewModel
import com.example.foodorderingapp.viewmodels.ItemsViewModel
import com.example.foodorderingapp.viewmodels.SearchBarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchBarScreenUI(
    navController:NavHostController,
    searchBarViewModel: SearchBarViewModel,
    cartItemsViewModel: CartItemsViewModel,
    viewModel: FoodPreferenceViewModel,
){
    val context = LocalContext.current
    var isBottomSheetOpen = rememberSaveable { mutableStateOf(false) }
    var searchKey = remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.bg_photo),
            contentScale = ContentScale.Crop,
            contentDescription = ""
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.7f), Color.Black.copy(alpha = 0.7f)
                        )
                    )
                )
        ) {

            Scaffold(modifier = Modifier.padding(top = 15.dp),
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(modifier = Modifier.wrapContentSize(),
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        ),
//                        navigationIcon = {
//                            IconButton(modifier = Modifier
//                                .size(40.dp)
//                                .padding(start = 2.dp, end = 3.dp),
//                                onClick = {
//                                    navController.popBackStack()
//                                }) {
//                                Icon(
//                                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
//                                    contentDescription = "",
//                                    modifier = Modifier.fillMaxSize()
//                                )
//                            }
//                        },
                        title = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                val focusManager = LocalFocusManager.current
                                OutlinedTextField(
                                    value = searchKey.value,
                                    onValueChange = {
                                        searchKey.value = it
                                    },
                                    placeholder = {

                                        var placeHolderText = remember { mutableStateOf("") }

                                        LaunchedEffect(Unit) {
                                            while (true) {
                                                placeHolderText.value = searchBarViewModel.allItems.toList().random().itemName
                                                kotlinx.coroutines.delay(4000)
                                            }
                                        }
                                        Text(
                                            text = "Search for \" ${placeHolderText.value} \"",
                                            fontSize = 16.sp,
                                            fontStyle = FontStyle.Italic,

                                        )
                                    },
                                    leadingIcon = {
                                        IconButton(modifier = Modifier
                                            .size(40.dp)
                                            .padding(start = 2.dp, end = 3.dp),
                                            onClick = {
                                                navController.popBackStack()
                                            }) {
                                            Icon(
                                                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                                contentDescription = "",
                                                modifier = Modifier.fillMaxSize()
                                            )
                                        }
                                    },
                                    textStyle = TextStyle(
                                        fontSize = 20.sp, textAlign = TextAlign.Start
                                    ),
                                    singleLine = true,
                                    modifier = Modifier
                                        .height(60.dp)
                                        .fillMaxWidth()
                                        .padding(start = 15.dp, top = 5.dp, end = 15.dp),
                                    shape = RoundedCornerShape(50.dp),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        unfocusedTextColor = Color.LightGray,
                                        focusedTextColor = Color.Black,
                                        unfocusedLabelColor = Color.LightGray,
                                        focusedLabelColor = Color.LightGray,
                                        unfocusedLeadingIconColor = Color.DarkGray,
                                        focusedLeadingIconColor = Color.DarkGray,
                                    ),
                                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                                )

                            }
                        },

                    )
                },
                content = {

                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        LazyColumn(modifier = Modifier.padding(top = 15.dp)) {

                            val itemList=searchBarViewModel.allItems.filter { it.itemName.contains(searchKey.value,ignoreCase = true) }

                            if (itemList.size == 0){
                                item {
                                    Text(
                                        text = "No Items Found",
                                        color = Color.White,
                                        fontSize = 22.sp,
                                        fontFamily = FontFamily.Cursive
                                        )
                                }
                            }else{
                                items(count = itemList.size, itemContent = { index ->
                                    if (viewModel.foodPreference.value == true) {
                                        if (itemList[index].isVeg) {
                                            searchItemView(
                                                index,
                                                itemList,
                                                cartItemsViewModel
                                            )
                                        }
                                    } else {
                                        searchItemView(
                                            index,
                                            itemList,
                                            cartItemsViewModel
                                        )
                                    }

                                })
                            }

                        }
                    }
                },
                floatingActionButton = {
                    if (cartItemsViewModel.cartItemsCount.value > 0) {
                        cartFloatingActionButton(navController, cartItemsViewModel)
                    }
                })

        }
    }
}

@Composable
fun searchItemView(
    index: Int,
    itemList: List<ItemScreenData>,
    //itemsViewModel: ItemsViewModel,
    cartItemsViewModel: CartItemsViewModel,
    bottomSheetViewModel: BottomSheetViewModel = BottomSheetViewModel()
) {
    var textBoxWidth = remember { mutableStateOf(114.dp) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(start = 5.dp, end = 5.dp, top = 4.dp, bottom = 7.dp),
        elevation = CardDefaults.cardElevation(15.dp),
        shape = RoundedCornerShape(25.dp),
        border = BorderStroke(1.4.dp, Brush.verticalGradient(listOf(Color.White, Color.Gray)))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(25.dp))
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(25.dp)),
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current).data(R.drawable.bg_photo)
                        .placeholder(R.drawable.bg_photo).build()
                ),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(25.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.Black, Color.Transparent.copy(alpha = 0.65f)
                            ), startX = 110.0f
                        )
                    )
            ) {

                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .fillMaxHeight()
                            .padding(start = 10.dp), horizontalAlignment = Alignment.Start
                    ) {

                        val imageResource =
                            if (itemList[index].isVeg) R.drawable.vegfoodsymbol else R.drawable.nonvegfoodsymbol
                        Image(
                            painter = painterResource(imageResource),
                            contentDescription = "",
                            modifier = Modifier
                                .size(43.dp)
                                .offset(y = 10.dp)
                                .padding(3.dp)
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = itemList[index].itemName,
                            fontSize = 23.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 10.dp, top = 7.dp),
                            fontFamily = FontFamily.Serif
                        )
                        Spacer(modifier = Modifier.height(9.dp))
                        Text(
                            text = "₹ ${itemList[index].price}",
                            fontSize = 18.sp,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 10.dp),
                            fontFamily = FontFamily.Serif
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier.size(300.dp, 190.dp)
                        ) {

                            //BOX TO CONTAIN THE IMAGE
                            Box(
                                modifier = Modifier
                                    .size(300.dp, 165.dp)
                                    .shadow(shape = RoundedCornerShape(40.dp), elevation = 20.dp)
                                    .border(
                                        BorderStroke(
                                            4.dp, Brush.verticalGradient(
                                                listOf(
                                                    Color.White, Color.Black.copy(alpha = 0.9f)
                                                )
                                            )
                                        ), shape = RoundedCornerShape(40.dp)
                                    )
                            ) {
                                if (itemList[index].isAvailable) {
                                    Image(
                                        painter = rememberAsyncImagePainter(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(itemList[index].itemImageUrl)
                                                .placeholder(R.drawable.placeholderimage).build()
                                        ),
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(shape = RoundedCornerShape(40.dp))
                                            .clickable(onClick = {
                                                bottomSheetViewModel.isBottomSheetOpen.value = true
                                            }

                                            ),
                                        contentDescription = "",

                                        )
                                } else {
                                    Image(
                                        painter = rememberAsyncImagePainter(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(itemList[index].itemImageUrl)
                                                .placeholder(R.drawable.placeholderimage).build()
                                        ),
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(shape = RoundedCornerShape(40.dp))
                                            .clickable(onClick = {
                                                bottomSheetViewModel.isBottomSheetOpen.value = true
                                            }),
                                        contentDescription = "",
                                        colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                                            setToSaturation(0f)
                                        })
                                    )
                                }
                                // ADD ITEM BUTTON AND COUNTER
                            }

                            //BOX TO CONTAIN THE TEXT

                            Box(
                                modifier = Modifier
                                    .size(textBoxWidth.value, 40.dp)
                                    .offset(y = -3.dp)
                                    .align(Alignment.BottomCenter)
                                    .shadow(
                                        20.dp, shape = RoundedCornerShape(15.dp)
                                    ), contentAlignment = Alignment.Center

                            ) {
                                addButtonOnSearchedItem(
                                    cartItemsViewModel, index, itemList, textBoxWidth
                                )

                            }


                        }

                    }
                }

            }
        }

        if (bottomSheetViewModel.isBottomSheetOpen.value) {
            bottomSheet(
                index,
                itemList,
                bottomSheetViewModel,
                cartItemsViewModel,
                textBoxWidth
            )
        }

    }
}
@Composable
private fun addButtonOnSearchedItem(
    cartItemsViewModel: CartItemsViewModel,
    index: Int,
    itemList:List<ItemScreenData>,
    textBoxWidth: MutableState<Dp>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (itemList[index].isAvailable) Color(147, 8, 8, 255) else Color.DarkGray
            )
            .border(1.2.dp, Brush.verticalGradient(listOf(Color.White.copy(alpha = 0.8f),Color.DarkGray)), shape = RoundedCornerShape(15.dp))
    ) {
        if (cartItemsViewModel.cartItems.containsKey(itemList[index].itemName) && itemList[index].isAvailable) {
            textBoxWidth.value = 120.dp
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {

                IconButton(modifier = Modifier
                    .weight(0.3f)
                    .padding(start = 1.5.dp), onClick = {
                    cartItemsViewModel.removeFromCart(itemList[index])
                }) {
                    Icon(
                        painter = painterResource(R.drawable.minus_icon),
                        contentDescription = "",
                        tint = Color.White
                    )
                }

                Text(
                    text = " ${cartItemsViewModel.cartItems[itemList[index].itemName]?.count}",
                    fontSize = 21.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.4f)
                )

                IconButton(modifier = Modifier
                    .weight(0.3f)
                    .padding(end = 1.5.dp), onClick = {
                    cartItemsViewModel.addToCart(itemList[index])
                }) {
                    Icon(Icons.Filled.Add, contentDescription = "", tint = Color.White)
                }

            }
        } else {
            textBoxWidth.value = 114.dp
            Button(
                modifier = Modifier.fillMaxSize(),
                onClick = {
                    cartItemsViewModel.addToCart(itemList[index])
                },
                colors = ButtonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.DarkGray,
                    contentColor = Color.White,
                    disabledContentColor = Color.White
                ),
                enabled = itemList[index].isAvailable,
                shape = RoundedCornerShape(15.dp)
            ) {
                Text(
                    text = "ADD",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun bottomSheet(
    index: Int,
    itemList: List<ItemScreenData>,
    bottomSheetViewModel: BottomSheetViewModel,
    cartItemsViewModel: CartItemsViewModel,
    textBoxWidth: MutableState<Dp>
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { bottomSheetViewModel.isBottomSheetOpen.value = false },
        containerColor = Color.Black,
        scrimColor = Color.Black.copy(alpha = 0.5f),
        shape = RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.bg_photo),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.9f), Color.Transparent.copy(alpha = 0.3f)
                            ),
                            startY = 1000f
                        )
                    )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp, end = 30.dp)
                            .height(215.dp),
                        shape = RoundedCornerShape(26.dp),
                        border = BorderStroke(
                            2.5.dp, Brush.verticalGradient(
                                listOf(
                                    Color.White, Color.Gray
                                )
                            )
                        ),
                        elevation = CardDefaults.cardElevation(30.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(itemList[index].itemImageUrl)
                                    .placeholder(R.drawable.placeholderimage)
                                    .build()
                            ),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                    }
                    Spacer(modifier = Modifier.height(19.dp))


                    if (itemList[index].isAvailable) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Box(Modifier.fillMaxWidth(0.6f)) {
                                Text(
                                    text = itemList[index].itemName,
                                    color = Color.White,
                                    fontSize = 26.sp,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.SemiBold,
                                    softWrap = true
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(textBoxWidth.value, 40.dp)
                                    .shadow(
                                        20.dp, shape = RoundedCornerShape(15.dp)
                                    )
                                    .background(
                                        Color.Transparent,
                                        shape = RoundedCornerShape(15.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (cartItemsViewModel.cartItems.containsKey(itemList[index].itemName)) {
                                    addButtonOnSearchedItem(
                                        cartItemsViewModel,
                                        index,
                                        itemList,
                                        textBoxWidth
                                    )
                                } else {
                                    Button(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        shape = RoundedCornerShape(15.dp),
                                        colors = ButtonDefaults.buttonColors(Color.Red.copy(alpha = 0.8f)),
                                        onClick = {
                                            cartItemsViewModel.addToCart(itemList[index])
                                            bottomSheetViewModel.isBottomSheetOpen.value = false
                                        }
                                    ) {
                                        Text(
                                            text = "ADD",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp,
                                            fontFamily = FontFamily.Serif
                                        )
                                    }
                                }
                            }

                        }
                    } else {

                        Text(
                            text = "Sorry!! This item is currently unavailable",
                            color = Color.White,
                            fontSize = 21.sp,
                            fontFamily = FontFamily.Cursive
                        )

                        Spacer(modifier = Modifier.height(11.dp))

                        Box(Modifier.fillMaxWidth()) {
                            Text(
                                text = itemList[index].itemName,
                                color = Color.White,
                                fontSize = 26.sp,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.SemiBold,
                                softWrap = true
                            )
                        }


                    }


                    Spacer(modifier = Modifier.height(8.5.dp))

                    Text(
                        text = "₹ ${itemList[index].price}",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Serif,
                        softWrap = true,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 15.dp)
                    )


                }

            }
        }

    }

}