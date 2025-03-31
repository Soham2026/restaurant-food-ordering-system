package com.example.foodorderingapp.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.inputmethodservice.Keyboard.Row
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.foodorderingapp.R
import com.example.foodorderingapp.viewmodels.KitchenCategoryViewmodel
import com.example.foodorderingapp.viewmodels.KitchenItemsViewModel

//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addItemOrCategory(
    isCategory: Boolean,
    navController: NavHostController,
    kitchenCategoryViewmodel: KitchenCategoryViewmodel? = null,
    kitchenItemsViewModel: KitchenItemsViewModel? = null,
    //categoryName: String? = null
) {
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var name by rememberSaveable { mutableStateOf("") }
    var isVeg by rememberSaveable { mutableStateOf(false) }
    var price by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current.applicationContext
    var namelabel = if (isCategory) "Category Name" else "Item Name"

    val intentLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            imageUri = result.data?.data
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                modifier = Modifier.clip(
                    RoundedCornerShape(
                        bottomStart = 40.dp,
                        bottomEnd = 40.dp
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
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            tint = Color.Black
                        )
                    }
                },
                title = { }
            )
        },
        content = { it ->
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
                        .background(Color.Black.copy(alpha = 0.8f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .verticalScroll(rememberScrollState(), true),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.height(30.dp))
                        Box(
                            modifier = Modifier
                                .size(180.dp)
                        ) {

                            Image(
                                painter = rememberAsyncImagePainter(
                                    imageUri ?: R.drawable.bg_photo
                                ),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(90.dp))
                            )
                            IconButton(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .size(60.dp)
                                    .shadow(shape = RoundedCornerShape(30.dp), elevation = 10.dp)
                                    .background(Color.White),
                                onClick = {
                                    // Intent to add item image
                                    val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
                                    intent.type = "image/*"
                                    intentLauncher.launch(intent)
                                }
                            ) {
                                val icon =
                                    if (imageUri == null) Icons.Rounded.Add else Icons.Rounded.Create
                                Icon(
                                    modifier = Modifier.size(32.dp),
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(25.dp))

                        OutlinedTextField(
                            value = name,
                            onValueChange = {
                                name = it
                            },
                            label = {
                                Text(
                                    text = namelabel,
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            },
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.fillMaxWidth(0.8f),
                            textStyle = TextStyle(color = Color.White)
                        )

                        if (!isCategory) {
                            Spacer(modifier = Modifier.height(20.dp))
                            OutlinedTextField(
                                value = price,
                                onValueChange = {
                                    price = it
                                },
                                label = {
                                    Text(
                                        text = "Price",
                                        fontFamily = FontFamily.Serif,
                                        fontSize = 18.sp,
                                        color = Color.White
                                    )
                                },
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.fillMaxWidth(0.8f),
                                textStyle = TextStyle(color = Color.White)
                            )
                        }

                        Spacer(modifier = Modifier.height(30.dp))


                        Row(
                            modifier = Modifier.fillMaxWidth(0.8f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = if (isCategory) "Set category as Pure Veg" else "Set item as Veg",
                                fontFamily = FontFamily.Serif,
                                fontSize = 19.sp,
                                color = Color.White,
                                modifier = Modifier.padding(start = 7.dp),
                                softWrap = true
                            )

                            Switch(
                                checked = isVeg,
                                onCheckedChange = {
                                    isVeg = !isVeg
                                },
                                colors = SwitchDefaults.colors(
                                    checkedTrackColor = Color.White,
                                    checkedThumbColor = Color.Green,
                                    uncheckedThumbColor = Color.Red,
                                    uncheckedTrackColor = Color.White
                                ),
                                modifier = Modifier.padding(end = 4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(110.dp))

                        Button(
                            modifier = Modifier.height(45.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF7062C7)
                            ),
                            onClick = {
                                if (isCategory) {
                                    //FOR CATEGORY
                                    if (imageUri != null && name != "") {
                                        kitchenCategoryViewmodel!!.uploadCategory(
                                            imageUri!!,
                                            name,
                                            false,
                                            isVeg
                                        ) { isDataUploaded ->
                                            if (isDataUploaded) {
                                                navController.popBackStack()
                                                Toast.makeText(
                                                    context,
                                                    "Category Uploaded",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Couldn't Upload !! Please try again",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Please provide necessary details ",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    //FOR ITEM
                                    if (imageUri != null && name != "" && price != "") {
                                        kitchenItemsViewModel!!.uploadItemData(
                                            imageUri!!,
                                            name,
                                            false,
                                            isVeg,
                                            price.toDouble()
                                        ) { isDataUploaded ->
                                            if (isDataUploaded) {
                                                navController.popBackStack()
                                                Toast.makeText(
                                                    context,
                                                    "Category Uploaded",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Couldn't Upload !! Please try again",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Please provide necessary details ",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                }
                            }
                        ) {
                            Text(
                                text = if (isCategory) "Add Category" else "Add Item",
                                fontFamily = FontFamily.Serif,
                                fontSize = 20.sp,
                                color = Color.White,
                                modifier = Modifier.padding(start = 7.dp),
                                softWrap = true
                            )
                        }


                    }
                }

            }
        }
    )

}

