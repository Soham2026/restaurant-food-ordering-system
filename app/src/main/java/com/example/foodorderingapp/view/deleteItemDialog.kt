package com.example.foodorderingapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodorderingapp.R
import com.example.foodorderingapp.viewmodels.KitchenCategoryViewmodel
import com.example.foodorderingapp.viewmodels.KitchenItemsViewModel


@Composable
fun deleteItemDialog(
    isCategory: Boolean? = null,
    categoryName: String? = null,
    itemName: String? = null,
    kitchenCategoryViewmodel: KitchenCategoryViewmodel? = null,
    kitchenItemsViewModel: KitchenItemsViewModel? = null,
    onDismissRequest: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(0.2f))
            .background(Color.Black.copy(0.33f))
            .background(Color.Transparent.copy(0.5f))
            .clickable {
                onDismissRequest()
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.83f)
                .wrapContentHeight()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Black.copy(0.95f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.83f)
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 9.dp, end = 9.dp),
                ) {
                    if (isCategory != null) {
                        if (isCategory) {
                            Text(
                                text = "Do you want to delete the category \"$categoryName\" ",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Serif,
                                textAlign = TextAlign.Center,
                                softWrap = true,
                            )
                        } else {
                            Text(
                                text = "Do you want to delete the item \"$itemName\" ",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Serif,
                                textAlign = TextAlign.Center,
                                softWrap = true,
                            )
                        }
                    }

                }
                Spacer(modifier = Modifier.height(19.9.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 15.dp, end = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Button(
                        modifier = Modifier.width(95.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(0.4f)),
                        shape = RoundedCornerShape(15.dp),
                        onClick = {
                            onDismissRequest()
                        }
                    ) {
                        Text(
                            text = "Cancel",
                            color = Color.White,
                            fontFamily = FontFamily.Serif,
                            textAlign = TextAlign.Center,
                        )
                    }

                    Button(
                        modifier = Modifier.width(95.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(0.4f)),
                        shape = RoundedCornerShape(15.dp),
                        onClick = {
                            // delete the item or category
                            if (isCategory != null) {
                                if (isCategory) {
                                    kitchenCategoryViewmodel!!.deleteCategory(categoryName!!)
                                } else {
                                    kitchenItemsViewModel!!.deleteItem(itemName!!)
                                }
                            }
                            onDismissRequest()
                        }
                    ) {
                        Text(
                            text = "Yes",
                            color = Color.White,
                            fontFamily = FontFamily.Serif,
                            textAlign = TextAlign.Center,
                        )
                    }

                }
                Spacer(modifier = Modifier.height(16.dp))
            }

        }
    }

}