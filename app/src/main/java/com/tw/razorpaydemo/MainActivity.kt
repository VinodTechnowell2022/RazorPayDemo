package com.tw.razorpaydemo

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PaymentResultListener{

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button = findViewById(R.id.button)

        Checkout.preload(applicationContext)

        button.setOnClickListener(View.OnClickListener { startPayment() })
    }


    private fun startPayment() {
        /**
         * Instantiate Checkout
         */

        val checkout = Checkout()
        checkout.setKeyID("rzp_test_0YofrpeGOMuhUL")
        /**
         * Set your logo here
         */
        checkout.setImage(R.mipmap.ic_launcher)

        /**
         * Reference to current activity
         */
        val activity: Activity = this

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            val options = JSONObject()

            options.put("name", "Binny Android")
            options.put("description", "Reference No. #123456")
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            //            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc")
            options.put("currency", "INR")
            options.put("amount", "30000") //pass amount in currency subunits
            options.put("prefill.email", "binny.android@gmail.com")
            options.put("prefill.contact", "9988776655")
            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)

            checkout.open(activity, options)
        } catch (e: Exception) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e)
        }
    }

    override fun onPaymentSuccess(s: String) {
        Log.d("ONSUCCESS", "onPaymentSuccess: $s")
    }

    override fun onPaymentError(i: Int, s: String) {
        Log.d("ONERROR", "onPaymentError: $s")
    }

}