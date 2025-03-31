package com.example.foodorderingapp

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.razorpay.Checkout
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode

//fun processPayment(activity: Activity,amount:Double=2000.00){
//    //val activity: Activity = context as Activity
//    val co = Checkout()
//
//    val roundedAmt= BigDecimal(amount).setScale(2, RoundingMode.HALF_UP)
//    val amountInPaise=(roundedAmt.toDouble()*100).toInt()
//
//    try {
//        val options = JSONObject()
//        options.put("name","Razorpay Corp")
//        options.put("description","Demoing Charges")
//        //You can omit the image option to fetch the image from the Dashboard
//        options.remove("image")
//        options.put("theme.color", "#3399cc");
//        options.put("currency","INR");
//        options.remove("order_id");
//        options.put("amount",amountInPaise)//pass amount in currency subunits
//        options.put("method",JSONObject().apply {
//            put("upi",true)
//            put("qr",true)
//        })
//        options.put("upi",JSONObject().apply {
//            put("flow","intent")
//        })
//
//        val retryObj =  JSONObject();
//        retryObj.put("enabled", true);
//        retryObj.put("max_count", 4);
//        options.put("retry", retryObj);
//
//        val prefill = JSONObject()
//        prefill.put("email","gaurav.kumar@example.com")
//        prefill.put("contact","9876543210")
//
//        options.put("prefill",prefill)
//        co.open(activity,options)
//    }catch (e: Exception){
//        Toast.makeText(activity,"Error in payment: "+ e.message, Toast.LENGTH_LONG).show()
//        e.printStackTrace()
//    }
//}


//options.remove("order_id", "order_DBJOWzybf0sJbb");