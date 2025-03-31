package com.example.foodorderingapp.view

import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodorderingapp.R
import com.example.foodorderingapp.viewmodels.CartItemsViewModel
import com.example.foodorderingapp.viewmodels.ItemsViewModel
import com.example.foodorderingapp.viewmodels.OnBoardScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun cartScreenUI(
    navController: NavController,
    cartItemsViewModel: CartItemsViewModel,
    itemsViewModel: ItemsViewModel,
    onBoardScreenViewModel: OnBoardScreenViewModel
) {

    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
                            Color.Black.copy(alpha = 0.7f),
                            Color.Black.copy(alpha = 0.8f)
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
                                text = "Cart",
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

                    Column(modifier = Modifier.padding(it)) {
                        LazyRow(
                            modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 10.dp)
                        ) {
                            item {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(42.dp)
                                        .padding(start = 15.dp, end = 9.dp),
                                    colors = CardDefaults.cardColors(Color.Black.copy(alpha = 0.6f)),
                                    shape = RoundedCornerShape(24.dp),
                                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.9f))
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Spacer(modifier = Modifier.width(15.dp))
                                        Text(
                                            text = "Menu",
                                            fontSize = 20.sp,
                                            fontFamily = FontFamily.Cursive,
                                            color = Color.White,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .padding(7.dp)
                                                .clickable(
                                                    onClick = {
                                                        navController.popBackStack(
                                                            "CategoryScreen",
                                                            false
                                                        )
                                                    }
                                                )
                                        )
                                        Spacer(modifier = Modifier.width(15.dp))
                                    }
                                }
                            }

                            item {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(42.dp)
                                        .padding(start = 9.dp, end = 9.dp),
                                    colors = CardDefaults.cardColors(Color.Black.copy(alpha = 0.6f)),
                                    shape = RoundedCornerShape(24.dp),
                                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.9f))
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Spacer(modifier = Modifier.width(15.dp))
                                        Text(
                                            text = "Clear Cart",
                                            fontSize = 20.sp,
                                            fontFamily = FontFamily.Cursive,
                                            color = Color.White,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .padding(7.dp)
                                                .clickable(
                                                    onClick = {
                                                        cartItemsViewModel.clearCart()
                                                    }
                                                )
                                        )
                                        Spacer(modifier = Modifier.width(15.dp))
                                    }
                                }
                            }

                            item {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(42.dp)
                                        .padding(start = 9.dp, end = 11.dp),
                                    colors = CardDefaults.cardColors(Color.Black.copy(alpha = 0.6f)),
                                    shape = RoundedCornerShape(24.dp),
                                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.9f))
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Spacer(modifier = Modifier.width(15.dp))
                                        Text(
                                            text = "Call Staff",
                                            fontSize = 20.sp,
                                            fontFamily = FontFamily.Cursive,
                                            color = Color.White,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .padding(7.dp)
                                                .clickable(
                                                    onClick = {
                                                        Toast.makeText(
                                                            context,
                                                            "Attending you soon !!",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                )
                                        )
                                        Spacer(modifier = Modifier.width(15.dp))
                                    }
                                }
                            }

                            item{
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(42.dp)
                                        .padding(start = 9.dp, end = 11.dp),
                                    colors = CardDefaults.cardColors(Color.Black.copy(alpha = 0.6f)),
                                    shape = RoundedCornerShape(24.dp),
                                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.9f))
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Spacer(modifier = Modifier.width(15.dp))
                                        Text(
                                            text = "Bill",
                                            fontSize = 20.sp,
                                            fontFamily = FontFamily.Cursive,
                                            color = Color.White,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .padding(7.dp)
                                                .clickable(
                                                    onClick = {
                                                        if(cartItemsViewModel.parallelCartItems.size>0){
                                                            navController.navigate("billScreen")
                                                        }else{
                                                            Toast.makeText(
                                                                context,
                                                                "No Order Placed Yet !!",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                                    }
                                                )
                                        )
                                        Spacer(modifier = Modifier.width(15.dp))
                                    }
                                }
                            }

                        }
                        Column(
                            modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 10.dp)
                        ) {

                            if (cartItemsViewModel.itemListFromCartMap.size == 0) {
                                navController.popBackStack("CartScreen", true)
                            }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(start = 6.dp, end = 6.dp, top = 4.dp),
                                colors = CardDefaults.cardColors(Color.Black.copy(alpha = 0.6f)),
                                shape = RoundedCornerShape(30.dp),
                                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.9f))
                            ) {
                                LazyColumn(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    contentPadding = PaddingValues(
                                        start = 5.dp,
                                        end = 5.dp,
                                        top = 10.dp,
                                        bottom = 7.dp
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.75f)
                                        .padding(start = 13.dp, end = 13.dp, top = 16.dp)
                                        .shadow(3.dp, shape = RoundedCornerShape(30.dp))
                                ) {

                                    items(
                                        count = cartItemsViewModel.itemListFromCartMap.size,
                                        itemContent = { index ->

                                            val itemName =
                                                cartItemsViewModel.itemListFromCartMap[index]
                                            val item = cartItemsViewModel.cartItems[itemName]

                                            item.let {
                                                Row() {
                                                    val foodChoiceImage =
                                                        if (item!!.ItemData.isVeg) R.drawable.vegfoodsymbol else R.drawable.nonvegfoodsymbol

                                                    Image(
                                                        painter = painterResource(foodChoiceImage),
                                                        contentDescription = null,
                                                        modifier = Modifier
                                                            .size(30.dp)
                                                            .weight(0.1f)
                                                    )
                                                    Column(
                                                        modifier = Modifier
                                                            .padding(start = 10.dp)
                                                            .weight(0.6f),
                                                        horizontalAlignment = Alignment.Start
                                                    ) {
                                                        Text(
                                                            text = itemName,
                                                            fontSize = 20.sp,
                                                            color = Color.White,
                                                            fontFamily = FontFamily.Serif,
                                                            softWrap = true
                                                        )
                                                        Spacer(modifier = Modifier.height(6.dp))

                                                        Text(
                                                            text = "₹ ${item.ItemData.price}",
                                                            fontSize = 13.sp,
                                                            color = Color.White,
                                                            fontFamily = FontFamily.Serif,
                                                            softWrap = true
                                                        )
                                                    }

                                                    Column(
                                                        modifier = Modifier
                                                            .padding(start = 10.dp)
                                                            .weight(0.3f),
                                                        horizontalAlignment = Alignment.End
                                                    ) {

                                                        Row(
                                                            modifier = Modifier
                                                                .border(
                                                                    1.2.dp, Brush.verticalGradient(
                                                                        listOf(
                                                                            Color.White,
                                                                            Color.DarkGray
                                                                        )
                                                                    ),
                                                                    RoundedCornerShape(16.dp)
                                                                )
                                                                .size(100.dp, 40.dp),
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Text(
                                                                text = "${(item.ItemData.price) * (item.count)}",
                                                                fontSize = 20.sp,
                                                                color = Color.White,
                                                                fontFamily = FontFamily.Serif,
                                                                softWrap = true,
                                                                modifier = Modifier
                                                                    .fillMaxSize(),
                                                                textAlign = TextAlign.Center
                                                            )
                                                        }


                                                        Spacer(modifier = Modifier.height(5.dp))
                                                        Row(
                                                            modifier = Modifier
                                                                .background(
                                                                    Color.Red.copy(alpha = 0.1f),
                                                                    shape = RoundedCornerShape(18.dp)
                                                                )
                                                                .border(
                                                                    0.4.dp, Brush.verticalGradient(
                                                                        listOf(
                                                                            Color.White,
                                                                            Color.DarkGray
                                                                        )
                                                                    ),
                                                                    RoundedCornerShape(18.dp)
                                                                )
                                                                .size(119.dp, 31.dp),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.spacedBy(
                                                                2.dp
                                                            )
                                                        ) {
                                                            IconButton(
                                                                modifier = Modifier
                                                                    .weight(0.3f)
                                                                    .padding(start = 1.5.dp),
                                                                onClick = {
                                                                    cartItemsViewModel.removeFromCart(
                                                                        item.ItemData
                                                                    )
                                                                }) {
                                                                Icon(
                                                                    painter = painterResource(R.drawable.minus_icon),
                                                                    contentDescription = null,
                                                                    tint = Color.White
                                                                )
                                                            }

                                                            Text(
                                                                text = "${cartItemsViewModel.cartItems[itemName]?.count}",
                                                                color = Color.White,
                                                                fontSize = 16.sp,
                                                                textAlign = TextAlign.Center,
                                                                fontFamily = FontFamily.Serif,
                                                                modifier = Modifier
                                                                    .weight(0.6f),
                                                                softWrap = true
                                                            )

                                                            IconButton(
                                                                modifier = Modifier
                                                                    .weight(0.3f)
                                                                    .padding(end = 1.5.dp),
                                                                onClick = {
                                                                    cartItemsViewModel.addToCart(
                                                                        item.ItemData
                                                                    )
                                                                }) {
                                                                Icon(
                                                                    Icons.Rounded.Add,
                                                                    contentDescription = null,
                                                                    tint = Color.White
                                                                )
                                                            }
                                                        }


                                                    }
                                                }
                                                Spacer(modifier = Modifier.height(9.dp))
                                                Spacer(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(start = 5.dp, end = 5.dp)
                                                        .height(0.3.dp)
                                                        .background(Color.White.copy(alpha = 0.2f))
                                                )
                                                Spacer(modifier = Modifier.height(19.dp))

                                            }

                                        }
                                    )

                                }

                                //Exp

                                Spacer(modifier = Modifier.height(22.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 15.dp, end = 15.dp, top = 15.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Total : ",
                                        fontSize = 20.sp,
                                        color = Color.White,
                                        fontFamily = FontFamily.Serif,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(start = 7.dp)
                                    )

                                    Text(
                                        text = "${cartItemsViewModel.totalBill.value} ",
                                        fontSize = 23.sp,
                                        color = Color.White,
                                        fontFamily = FontFamily.Serif,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(end = 5.dp)
                                    )

                                }
                                Spacer(modifier = Modifier.height(17.dp))
                                OutlinedButton(
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    colors = ButtonDefaults.buttonColors(
                                        Color.Red.copy(
                                            alpha = 0.1f
                                        )
                                    ),
                                    onClick = {
                                        navController.navigate("cartValidateScreen")
                                    }
                                ) {
                                    Text(
                                        text = "Place Order",
                                        fontSize = 26.sp,
                                        color = Color.White,
                                        fontFamily = FontFamily.Cursive,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(start = 7.dp)
                                    )

                                }
                                Spacer(modifier = Modifier.height(17.dp))

                                //Exp
                            }
                        }


                    }
                }
            )
        }
    }
}
