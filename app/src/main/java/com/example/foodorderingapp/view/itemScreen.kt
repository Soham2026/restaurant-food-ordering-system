package com.example.foodorderingapp.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.foodorderingapp.R
import com.example.foodorderingapp.viewmodels.BottomSheetViewModel
import com.example.foodorderingapp.viewmodels.CartItemsViewModel
import com.example.foodorderingapp.viewmodels.FoodPreferenceViewModel
import com.example.foodorderingapp.viewmodels.ItemsViewModel
import com.example.foodorderingapp.viewmodels.SearchBarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun itemScreenUI(
    viewModel: FoodPreferenceViewModel,
    painter: Painter,
    navController: NavController,
    categoryName: String,
    isPureVeg: Boolean,
    itemsViewModel: ItemsViewModel,
    cartItemsViewModel: CartItemsViewModel,
    searchBarViewModel:SearchBarViewModel
) {

    val context = LocalContext.current
    var isBottomSheetOpen = rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
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
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = categoryName,
                                    fontSize = 24.sp,
                                    color = Color.White,
                                    fontFamily = FontFamily.Cursive,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(start = 7.dp),
                                )

                                if (isPureVeg == true) {
                                    Image(
                                        painter = painterResource(R.drawable.vegfoodsymbol),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(45.dp)
                                            .padding(top = 7.dp, bottom = 7.dp)
                                    )
                                }
                            }
                        },

                        actions = {

                            Box(
                                modifier = Modifier.width(95.dp)
                            ) {
                                Switch(
                                    modifier = Modifier.align(Alignment.BottomEnd),
                                    checked = viewModel.foodPreference.value,
                                    onCheckedChange = {
                                        viewModel.foodPreference.value = it
                                    },

                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.Green,
                                        checkedTrackColor = Color(255, 244, 244),
                                        uncheckedTrackColor = Color(255, 244, 244),
                                        uncheckedThumbColor = Color(255, 11, 11)
                                    )
                                )
                                Text(
                                    text = if (viewModel.foodPreference.value == true) "Veg" else "",
                                    fontSize = 23.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Cursive,
                                    color = Color.Green,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            }
                        })
                },
                content = {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start=7.dp,end=7.dp)
                                .background(Color.Transparent)
                        ) {
                            Row(
                                modifier = Modifier
                                    .height(60.dp)
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, top = 5.dp, end = 10.dp)
                                    .clip(RoundedCornerShape(50.dp))
                                    .background(Color.White)
                                    .clickable {
                                        navController.navigate("searchBarScreen")
                                    },

                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {

                                Spacer(Modifier.width(10.dp))

                                Icon(
                                    Icons.Outlined.Search, contentDescription = "", tint = Color.DarkGray
                                )
                                Spacer(Modifier.width(10.dp))
                                var placeHolderText = remember { mutableStateOf("") }

                                LaunchedEffect(Unit) {
                                    while (true) {
                                        placeHolderText.value = searchBarViewModel.allItems.toList().random().itemName
                                        kotlinx.coroutines.delay(4200)
                                    }
                                }
                                Text(
                                    text = "Search for \" ${placeHolderText.value} \"",
                                    fontSize = 16.sp,
                                    fontStyle = FontStyle.Italic,
                                    color=Color.LightGray
                                )

                            }

                        }
                        LazyColumn(modifier = Modifier.padding(top = 15.dp)) {


                            items(count = itemsViewModel.itemData.size, itemContent = { index ->
                                if (viewModel.foodPreference.value == true) {
                                    if (itemsViewModel.itemData[index].isVeg) {
                                        eachItemView(
                                            index,
                                            itemsViewModel,
                                            cartItemsViewModel
                                        )
                                    }
                                } else {
                                    eachItemView(
                                        index,
                                        itemsViewModel,
                                        cartItemsViewModel
                                    )
                                }

                            })
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
 fun eachItemView(
    index: Int,
    itemsViewModel: ItemsViewModel,
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
                            if (itemsViewModel.itemData[index].isVeg) R.drawable.vegfoodsymbol else R.drawable.nonvegfoodsymbol
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
                            text = itemsViewModel.itemData[index].itemName,
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
                            text = "₹ ${itemsViewModel.itemData[index].price}",
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
                                if (itemsViewModel.itemData[index].isAvailable) {
                                    Image(
                                        painter = rememberAsyncImagePainter(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(itemsViewModel.itemData[index].itemImageUrl)
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
                                                .data(itemsViewModel.itemData[index].itemImageUrl)
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
                                addButtonOnItem(
                                    cartItemsViewModel, index, itemsViewModel, textBoxWidth
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
                itemsViewModel,
                bottomSheetViewModel,
                cartItemsViewModel,
                textBoxWidth
            )
        }

    }
}

@Composable
private fun addButtonOnItem(
    cartItemsViewModel: CartItemsViewModel,
    index: Int,
    itemsViewModel: ItemsViewModel,
    textBoxWidth: MutableState<Dp>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (itemsViewModel.itemData[index].isAvailable) Color(147, 8, 8, 255) else Color.DarkGray
            )
            .border(1.2.dp, Brush.verticalGradient(listOf(Color.White.copy(alpha = 0.8f),Color.DarkGray)), shape = RoundedCornerShape(15.dp))
    ) {
        if (cartItemsViewModel.cartItems.containsKey(itemsViewModel.itemData[index].itemName) && itemsViewModel.itemData[index].isAvailable) {
            textBoxWidth.value = 120.dp
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {

                IconButton(modifier = Modifier
                    .weight(0.3f)
                    .padding(start = 1.5.dp), onClick = {
                    cartItemsViewModel.removeFromCart(itemsViewModel.itemData[index])
                }) {
                    Icon(
                        painter = painterResource(R.drawable.minus_icon),
                        contentDescription = "",
                        tint = Color.White
                    )
                }

                Text(
                    text = " ${cartItemsViewModel.cartItems[itemsViewModel.itemData[index].itemName]?.count}",
                    fontSize = 21.sp,
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.4f)
                )

                IconButton(modifier = Modifier
                    .weight(0.3f)
                    .padding(end = 1.5.dp), onClick = {
                    cartItemsViewModel.addToCart(itemsViewModel.itemData[index])
                }) {
                    Icon(Icons.Filled.Add, contentDescription = "", tint = Color.White)
                }

            }
        } else {
            textBoxWidth.value = 114.dp
            Button(
                modifier = Modifier.fillMaxSize(),
                onClick = {
                    cartItemsViewModel.addToCart(itemsViewModel.itemData[index])
                },
                colors = ButtonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.DarkGray,
                    contentColor = Color.White,
                    disabledContentColor = Color.White
                ),
                enabled = itemsViewModel.itemData[index].isAvailable,
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
    itemsViewModel: ItemsViewModel,
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
                                    .data(itemsViewModel.itemData[index].itemImageUrl)
                                    .placeholder(R.drawable.placeholderimage)
                                    .build()
                            ),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                    }
                    Spacer(modifier = Modifier.height(19.dp))


                    if (itemsViewModel.itemData[index].isAvailable) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Box(Modifier.fillMaxWidth(0.6f)) {
                                Text(
                                    text = itemsViewModel.itemData[index].itemName,
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
                                if (cartItemsViewModel.cartItems.containsKey(itemsViewModel.itemData[index].itemName)) {
                                    addButtonOnItem(
                                        cartItemsViewModel,
                                        index,
                                        itemsViewModel,
                                        textBoxWidth
                                    )
                                } else {
                                    Button(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        shape = RoundedCornerShape(15.dp),
                                        colors = ButtonDefaults.buttonColors(Color.Red.copy(alpha = 0.8f)),
                                        onClick = {
                                            cartItemsViewModel.addToCart(itemsViewModel.itemData[index])
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
                                text = itemsViewModel.itemData[index].itemName,
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
                        text = "₹ ${itemsViewModel.itemData[index].price}",
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
