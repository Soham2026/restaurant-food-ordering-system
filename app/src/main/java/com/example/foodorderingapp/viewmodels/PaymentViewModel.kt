package com.example.foodorderingapp.viewmodels

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorderingapp.model.repository.OrderRepository
import com.razorpay.Checkout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
   val orderRepository: OrderRepository
) : ViewModel() {

    val _paymentStatus = MutableStateFlow("Pending")
    val paymentStatus: StateFlow<String> = _paymentStatus


    fun processPayment(activity: Activity, amount: Double, onPaymentSuccess: (() -> Unit)? =null) {
        //val activity: Activity = context as Activity
        val co = Checkout()

        val roundedAmt = BigDecimal(amount).setScale(2, RoundingMode.HALF_UP)
        val amountInPaise = (roundedAmt.toDouble() * 100).toInt()

        try {
            val options = JSONObject()
            options.put("name", "Razorpay Corp")
            options.put("description", "Demoing Charges")
            //You can omit the image option to fetch the image from the Dashboard
            options.remove("image")
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.remove("order_id");
            options.put("amount", amountInPaise)//pass amount in currency subunits
            options.put("method", JSONObject().apply {
                put("upi", true)
                put("qr", true)
            })
            options.put("upi", JSONObject().apply {
                put("flow", "intent")
            })

            val retryObj = JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email", "gaurav.kumar@example.com")
            prefill.put("contact", "9876543210")

            options.put("prefill", prefill)
            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }


    }

    fun onSuccessHandler() {
        _paymentStatus.value = "Success"
    }

    fun resetPaymentStatus() {
        _paymentStatus.value = "Pending"
    }

    fun clearTable(tableNumber:String, totalAmount:Double){
        viewModelScope.launch {
            orderRepository.deleteOrder(tableNumber,totalAmount)
        }
    }


}