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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import com.example.foodorderingapp.viewmodels.OnBoardScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun startScreenUI(
    painter: Painter,
    navController: NavHostController,
    onBoardScreenViewModel: OnBoardScreenViewModel
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxWidth(0.65f),
                drawerShape = RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp),
                drawerContainerColor = Color.Black.copy(alpha = 0.8f)
            ) {
                navigationDrawerItems(drawerState, coroutineScope, navController)
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
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
                                contentDescription = "",
                                modifier = Modifier.fillMaxSize(),
                                tint = Color.White
                            )
                        }
                    },
                    title = {},
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
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
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize()
            )
            {
                Image(
                    painter = painter,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.54f))
                )
// started here


                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.2f),
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.68f),
                                    Color.Black
                                )
                            )
                        )

                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 130.dp, start = 40.dp, end = 40.dp)
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentHeight()
                                .background(Color.Transparent),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Spacer(modifier = Modifier.size(1.dp, 19.dp))
                            Text(
                                text = "Welcome",
                                fontSize = 40.sp,
                                color = Color.White,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.size(1.dp, 3.dp))
                            Text(
                                text = "to",
                                fontSize = 39.sp,
                                color = Color.White,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.size(1.dp, 3.dp))
                            Text(
                                text = "${onBoardScreenViewModel.restaurantName}",
                                fontSize = 65.sp,
                                style = TextStyle(brush = Brush.verticalGradient(listOf(Color.White,Color.Red.copy(alpha = 1f)))),
                                //color = Color.White,
                                fontFamily = FontFamily.Cursive,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center,
                                softWrap = true,
                                modifier = Modifier.shadow(elevation = 20.dp, ambientColor = Color.Black.copy(alpha = 0.5f))
                            )
                            Spacer(modifier = Modifier.size(1.dp, 14.dp))
                        }

                    }
                    OutlinedButton(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 140.dp),
                        onClick = {
                            navController.navigate("CategoryScreen")
                        },
                        border = BorderStroke(2.dp, Color.White)
                    ) {
                        Text(
                            text = "Let's Order", fontSize = 26.sp
                        )
                    }
                }
            }
        }
    }


}

@Composable
fun navigationDrawerItems(
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 50.dp, end = 10.dp),

        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Change Table\nNumber",
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
                    navController.navigate("tableUpdateScreen")
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


    }
}