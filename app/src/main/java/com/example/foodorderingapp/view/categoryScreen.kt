package com.example.foodorderingapp.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.FontScaling
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.foodorderingapp.R
import com.example.foodorderingapp.ui.theme.vegThumbColor
import com.example.foodorderingapp.ui.theme.vegtrailColor
import com.example.foodorderingapp.viewmodels.CartItemsViewModel
import com.example.foodorderingapp.viewmodels.CategoriesViewModel
import com.example.foodorderingapp.viewmodels.FoodPreferenceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun categoryScreenUI(
    viewModel: FoodPreferenceViewModel,
    painter: Painter,
    navController: NavController,
    cartItemsViewModel: CartItemsViewModel
) {

    val context = LocalContext.current
    var categoryViewModel: CategoriesViewModel = hiltViewModel()


    var foodType = ""
    if (viewModel.foodPreference.value) {
        foodType = "Veg"
    }

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
                            Color.Black.copy(alpha = 0.9f),
                            Color.Black.copy(alpha = 0.9f)
                        )
                    )
                )
        ) {
            Scaffold(modifier = Modifier.padding(top = 15.dp),

                containerColor = Color.Transparent,

                topBar = {

                    CenterAlignedTopAppBar(
                        //modifier = Modifier.height(120.dp),

                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            scrolledContainerColor = Color.Black.copy(alpha = 0.9f)
                        ),

                        title = {
                            Text(
                                "Menu",
                                fontSize = 41.sp,
                                color = Color.White,
                                fontFamily = FontFamily.Cursive,
                                fontWeight = FontWeight.ExtraBold
                            )
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
                                    text = "$foodType",
                                    fontSize = 23.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Cursive,
                                    color = Color.Green,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            }
                        },
                        // scrollBehavior = topBarBehaviour
                    )
                },

                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (categoryViewModel.categoryList.size == 0) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(68.dp).clip(RoundedCornerShape(18.dp)),
                                    color = Color.White,
                                )
                            }
                        } else {
                            LazyVerticalGrid(
                                modifier = Modifier.padding(1.dp),
                                columns = GridCells.Adaptive(160.dp)
                            ) {
                                items(count = categoryViewModel.categoryList.size,
                                    itemContent = { index ->
                                        Card(
                                            modifier = Modifier
                                                .height(220.dp)
                                                .padding(10.dp),
                                            elevation = CardDefaults.cardElevation(15.dp),
                                            shape = RoundedCornerShape(25.dp),
                                            border = BorderStroke(
                                                1.dp, brush = Brush.verticalGradient(
                                                    listOf(Color.White, Color.Gray)
                                                )
                                            ),

                                            ) {

                                            Box(modifier = Modifier
                                                .fillMaxSize()
                                                .clickable(
                                                    enabled = categoryViewModel.categoryList[index].isAvailable,
                                                    onClick = {
                                                        navController.navigate("ItemScreen/${categoryViewModel.categoryList[index].categoryName}/${categoryViewModel.categoryList[index].isPureVeg}")
                                                    }
                                                )) {
                                                if (categoryViewModel.categoryList[index].isAvailable) {
                                                    Image(
                                                        painter = rememberAsyncImagePainter(
                                                            model = ImageRequest.Builder(
                                                                LocalContext.current
                                                            )
                                                                .data(categoryViewModel.categoryList[index].categoryImageUrl)
                                                                .placeholder(R.drawable.placeholderimage)
                                                                .build()
                                                        ),
                                                        contentScale = ContentScale.Crop,
                                                        modifier = Modifier.fillMaxSize(),
                                                        contentDescription = ""
                                                    )
                                                } else {
                                                    Image(
                                                        painter = rememberAsyncImagePainter(
                                                            model = ImageRequest.Builder(
                                                                LocalContext.current
                                                            )
                                                                .data(categoryViewModel.categoryList[index].categoryImageUrl)
                                                                .placeholder(R.drawable.placeholderimage)
                                                                .build()
                                                        ),
                                                        contentScale = ContentScale.Crop,
                                                        modifier = Modifier.fillMaxSize(),
                                                        contentDescription = "",
                                                        colorFilter = ColorFilter.colorMatrix(
                                                            ColorMatrix().apply {
                                                                setToSaturation(0f)
                                                            })
                                                    )
                                                }

                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .background(
                                                            Brush.verticalGradient(
                                                                colors = listOf(
                                                                    Color.Transparent, Color.Black
                                                                )
                                                            )
                                                        )
                                                ) {
                                                    Text(
                                                        text = categoryViewModel.categoryList[index].categoryName,
                                                        fontSize = 18.sp,
                                                        color = Color.White,
                                                        //fontWeight = FontWeight.Bold,
                                                        modifier = Modifier
                                                            .align(Alignment.BottomStart)
                                                            .padding(start = 10.dp, bottom = 8.dp),
                                                        fontFamily = FontFamily.Serif
                                                    )
                                                }
                                            }


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

                }

            )
        }
    }


}

