package com.example.foodorderingapp.view

import android.graphics.Typeface
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foodorderingapp.R

@Preview(showBackground = true)
@Composable
fun orderConfirmedScreenUI(navController: NavHostController= rememberNavController()) {


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
                            Color.Black.copy(alpha = 0.85f),
                            Color.Black.copy(alpha = 0.8f)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxSize()
                    .background(Color.Transparent),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val screenHeight = LocalConfiguration.current.screenHeightDp.dp
                Box(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                        .offset(y=-90.dp),
                    contentAlignment = Alignment.TopCenter
                ){
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Order",
                            fontSize = 80.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily.Cursive,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "Placed",
                            fontSize = 80.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily.Cursive,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                }

                OutlinedButton(
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Red.copy(alpha = 0.4f)
                    ),
                    onClick = {
                        navController.navigate("CategoryScreen")
                    },
                    border = BorderStroke(0.9.dp,Color.White.copy(0.8f))
                ) {
                    Text(
                        text = "Order More",
                        fontSize = 30.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.size(1.dp,30.dp))

                OutlinedButton(
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Black.copy(alpha = 0.6f)
                    ),
                    onClick = {
                        navController.navigate("billScreen")

                    },
                    modifier = Modifier.width(178.4.dp).height(53.dp),
                    border = BorderStroke(0.9.dp,Color.White.copy(0.8f))
                ) {
                    Text(
                        text = "Bill",
                        fontSize = 30.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.fillMaxWidth()
                    )
                }


            }

        }
    }
}