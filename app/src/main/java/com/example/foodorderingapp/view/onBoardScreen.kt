package com.example.foodorderingapp.view

import android.content.Context
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.foodorderingapp.viewmodels.DataStoreViewModel
import com.example.foodorderingapp.viewmodels.OnBoardScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun onBoardingScreenUI(
    navController:NavHostController,
    onBoardScreenViewModel: OnBoardScreenViewModel,
    dataStoreViewModel: DataStoreViewModel
) {

    var pagesCount = onBoardScreenViewModel.pagesCount
    var pagerState = rememberPagerState(initialPage = 0, pageCount = { pagesCount })
    val context = LocalContext.current
    var coroutinescope = rememberCoroutineScope()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(2.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
        ) { page ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when (page) {
                    0 -> onBoardScreenForRestaurantName(
                        context,
                        onBoardScreenViewModel,
//                        { name -> restaurantName = name },
                        {
                            coroutinescope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    )

                    1 -> onBoardScreenForDeviceUsageEnd(
                        onBoardScreenViewModel,
                        {
                            coroutinescope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        {
                            coroutinescope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        },
                        {
                            dataStoreViewModel.storeDeviceUsagePreference(context,onBoardScreenViewModel.restaurantName,false,0)
                            navController.navigate("kitchenCategoryScreen"){popUpTo(0){inclusive=true} }// write code to save name and device usage and navigate to kitchen UI
                        }
                    )

                    2 -> onBoardScreenForTableNumber(
                        onBoardScreenViewModel,
                        {
                            coroutinescope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        },
                        {
                            // SAVES THE DATA AND NAVIGATES TO CUSTOMER END UI
                            dataStoreViewModel.storeDeviceUsagePreference(context,onBoardScreenViewModel.restaurantName,true,onBoardScreenViewModel.tableNumber.toInt())
                            navController.navigate("StartScreen"){popUpTo(0){inclusive=true} }

                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier.padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pagesCount) {
                val color = if (pagerState.currentPage == it) Color.White else Color.Black
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(13.dp)
                        .clip(shape = CircleShape)
                        .border(0.9.dp, Color.White, CircleShape)
                        .background(color, CircleShape)
                )
            }
        }

    }

}


@Composable
fun onBoardScreenForRestaurantName(
    context: Context,
    onBoardScreenViewModel: OnBoardScreenViewModel,
    onNextPressed: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "What's your restaurant called ?",
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = FontFamily.Serif
        )

        Spacer(modifier = Modifier.size(1.dp, 13.dp))

        OutlinedTextField(
            value = onBoardScreenViewModel.restaurantName,
            onValueChange = {
                onBoardScreenViewModel.restaurantName = it
            },
            textStyle = TextStyle(fontFamily = FontFamily.Serif, fontSize = 18.sp),
            shape = RoundedCornerShape(20.dp),
            placeholder = {
                Text(
                    text = "Set Restaurant Name",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.3f),
                    fontFamily = FontFamily.Serif
                )
            },
            modifier = Modifier.size(350.dp, 68.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color.White.copy(alpha = 0.9f)
            )
        )

        Spacer(modifier = Modifier.size(1.dp, 22.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Button(modifier = Modifier
                .border(
                    1.dp, Color.Transparent,
                    RoundedCornerShape(26.dp)
                )
                .clip(
                    RoundedCornerShape(26.dp)
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                onClick = {
                    if (onBoardScreenViewModel.restaurantName.isNotEmpty()) {
                        onNextPressed()
                    } else {
                        Toast.makeText(context, "Please Enter Restaurant Name", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            ) {
                Text(
                    "Next",
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun onBoardScreenForDeviceUsageEnd(
    onBoardScreenViewModel: OnBoardScreenViewModel,
    onNextPressed: () -> Unit,
    onBackPressed: () -> Unit,
    onFinished: () -> Unit
) {
    val list = listOf("Customer", "Kitchen Staff")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "The device is used for :",
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.size(1.dp, 13.dp))
        list.forEachIndexed { index, option ->

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                RadioButton(
                    selected = onBoardScreenViewModel.selectedOption == index,
                    onClick = {
                        onBoardScreenViewModel.selectedOption = index
                        if (option == list[0]) {
                            onBoardScreenViewModel.isNextVisible = true
                            onBoardScreenViewModel.pagesCount += if (onBoardScreenViewModel.pagesCount < 3) 1 else 0
                        } else {
                            onBoardScreenViewModel.isNextVisible = false
                            onBoardScreenViewModel.pagesCount += if (onBoardScreenViewModel.pagesCount > 2) -1 else 0
                        }
                    }
                )

                Text(
                    text = option,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = FontFamily.Serif
                )
            }

        }

        Spacer(modifier = Modifier.size(1.dp, 18.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, end = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(modifier = Modifier
                .border(
                    1.dp, Color.Transparent,
                    RoundedCornerShape(26.dp)
                )
                .clip(
                    RoundedCornerShape(26.dp)
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                onClick = {
                    onBackPressed()
                }
            ) {
                Text(
                    "Back",
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (onBoardScreenViewModel.isNextVisible) {
                Button(modifier = Modifier
                    .border(
                        1.dp, Color.Transparent,
                        RoundedCornerShape(26.dp)
                    )
                    .clip(
                        RoundedCornerShape(26.dp)
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    onClick = {
                        onNextPressed()
                    }
                ) {
                    Text(
                        "Next",
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(1.dp, 40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onBoardScreenViewModel.isNextVisible == false) {
                Button(modifier = Modifier
                    .border(
                        1.dp, Color.Transparent,
                        RoundedCornerShape(26.dp)
                    )
                    .clip(
                        RoundedCornerShape(26.dp)
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    onClick = {
                        onFinished()
                    }
                ) {
                    Text(
                        "Get Started",
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

    }
}

@Composable
fun onBoardScreenForTableNumber(
    onBoardScreenViewModel: OnBoardScreenViewModel,
    onBackPressed: () -> Unit,
    onFinished: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Almost done! Assign a table number to this ordering station ",
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        )
        Spacer(modifier = Modifier.size(1.dp, 15.dp))
        OutlinedTextField(
            value = onBoardScreenViewModel.tableNumber,
            onValueChange = {
                onBoardScreenViewModel.tableNumber = it
            },
            placeholder = {
                Text(
                    text = "Set Table Number",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.3f),
                    fontFamily = FontFamily.Serif
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(fontFamily = FontFamily.Serif, fontSize = 18.sp),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.size(350.dp, 68.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color.White.copy(alpha = 0.9f)
            )

        )

        Spacer(modifier = Modifier.size(1.dp, 22.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Button(modifier = Modifier
                .border(
                    1.dp, Color.Transparent,
                    RoundedCornerShape(26.dp)
                )
                .clip(
                    RoundedCornerShape(26.dp)
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                onClick = {
                    onBackPressed()
                }
            ) {
                Text(
                    "Back",
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        if (onBoardScreenViewModel.tableNumber.isNotEmpty()) {
            Spacer(modifier = Modifier.size(1.dp, 22.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(modifier = Modifier
                    .border(
                        1.dp, Color.Transparent,
                        RoundedCornerShape(26.dp)
                    )
                    .clip(
                        RoundedCornerShape(26.dp)
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    onClick = {
                        onFinished()
                    }
                ) {
                    Text(
                        "Get Started",
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}