package com.simats.smartpark

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.billingclient.api.*

@Composable
fun SubscriptionScreen(
    onContinue: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as Activity
    val TAG = "SubscriptionScreen"
    
    val REAL_SUBSCRIPTION_SKU = "smartpark_premium_subscription"
    val TEST_SUBSCRIPTION_SKU = "android.test.purchased"

    var productDetails by remember { mutableStateOf<ProductDetails?>(null) }
    var billingClient by remember { mutableStateOf<BillingClient?>(null) }
    var isSubscribed by remember { mutableStateOf(false) }

    // Initialize state from SharedPreferences
    LaunchedEffect(Unit) {
        val sharedPref = context.getSharedPreferences("subscription_prefs", Context.MODE_PRIVATE)
        isSubscribed = sharedPref.getBoolean("is_premium_user", false)
    }

    val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                handlePurchase(purchase, billingClient, context, onContinue)
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            Toast.makeText(context, "Purchase canceled", Toast.LENGTH_SHORT).show()
        } else {
            Log.e(TAG, "Purchase failed: ${billingResult.debugMessage}")
        }
    }

    LaunchedEffect(Unit) {
        val client = BillingClient.newBuilder(context)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()
        
        billingClient = client

        client.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(result: BillingResult) {
                if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                    // Check existing purchases
                    client.queryPurchasesAsync(
                        QueryPurchasesParams.newBuilder()
                            .setProductType(BillingClient.ProductType.SUBS)
                            .build()
                    ) { _, purchases ->
                        val hasActiveSub = purchases.any { it.purchaseState == Purchase.PurchaseState.PURCHASED }
                        if (hasActiveSub) {
                            saveSubscriptionStatus(context, true)
                            isSubscribed = true
                        }
                    }

                    querySubscriptionDetails(client, REAL_SUBSCRIPTION_SKU, TEST_SUBSCRIPTION_SKU) { details ->
                        productDetails = details
                    }
                }
            }
            override fun onBillingServiceDisconnected() {
                Log.w(TAG, "Billing service disconnected")
            }
        })
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Change to white background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header Section - Keep SmartPark same color/style
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1D4ED8)) // Keep the blue header
                    .padding(vertical = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "SmartPark",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Surface(
                        color = Color.White.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = if (isSubscribed) "ACTIVE PREMIUM" else "PREMIUM",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSubscribed) Color(0xFF4ADE80) else Color(0xFFFFD700)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = if (isSubscribed) "Thank you for being a Premium member!" else "Upgrade for a smoother and faster experience",
                    fontSize = 16.sp,
                    color = Color.Black.copy(alpha = 0.7f), // Darker text for white bg
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                // Benefits with darker text
                BenefitItem(title = "Ad-Free Experience", subtitle = "No ads while using the app")
                BenefitItem(title = "Real-time availability", subtitle = "Check parking spots live")
                BenefitItem(title = "Premium Tools", subtitle = "Access advanced features")

                Spacer(modifier = Modifier.height(40.dp))

                // Price Card
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    color = Color(0xFF7C3AED),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Only", fontSize = 16.sp, color = Color.White.copy(alpha = 0.8f))
                        Text(text = "₹100", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(text = "per month", fontSize = 16.sp, color = Color.White.copy(alpha = 0.8f))
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Action Button - Bordered or colored to stand out on white
                Button(
                    onClick = {
                        if (isSubscribed) {
                            onContinue()
                        } else {
                            launchSubscriptionFlow(activity, billingClient, productDetails)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1D4ED8), // Blue button for visibility
                        contentColor = Color.White
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isSubscribed) "Continue to App" else "Get Premium",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(20.dp))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                TextButton(
                    onClick = { onContinue() },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = if (isSubscribed) "Go to Home" else "Skip for now",
                        color = Color(0xFF1D4ED8), // Updated to matching Premium Blue
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun BenefitItem(title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = Color(0xFF4ADE80),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black) // Black text
            Text(text = subtitle, fontSize = 14.sp, color = Color.Black.copy(alpha = 0.6f)) // Black text
        }
    }
}

private fun saveSubscriptionStatus(context: Context, status: Boolean) {
    val sharedPref = context.getSharedPreferences("subscription_prefs", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putBoolean("is_premium_user", status)
        apply()
    }
}

private fun querySubscriptionDetails(
    client: BillingClient,
    realSku: String,
    testSku: String,
    onResult: (ProductDetails?) -> Unit
) {
    val productList = listOf(
        QueryProductDetailsParams.Product.newBuilder()
            .setProductId(realSku)
            .setProductType(BillingClient.ProductType.SUBS)
            .build()
    )
    val params = QueryProductDetailsParams.newBuilder().setProductList(productList).build()

    client.queryProductDetailsAsync(params) { result, list ->
        if (result.responseCode == BillingClient.BillingResponseCode.OK && list.isNotEmpty()) {
            onResult(list[0])
        } else {
            val testProductList = listOf(
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(testSku)
                    .setProductType(BillingClient.ProductType.INAPP)
                    .build()
            )
            val testParams = QueryProductDetailsParams.newBuilder().setProductList(testProductList).build()
            client.queryProductDetailsAsync(testParams) { _, testList ->
                onResult(if (testList.isNotEmpty()) testList[0] else null)
            }
        }
    }
}

private fun launchSubscriptionFlow(
    activity: Activity,
    client: BillingClient?,
    details: ProductDetails?
) {
    if (client == null || details == null || !client.isReady) {
        Toast.makeText(activity, "Billing service not ready", Toast.LENGTH_SHORT).show()
        return
    }

    val paramsBuilder = BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(details)

    if (details.productType == BillingClient.ProductType.SUBS) {
        val offerToken = details.subscriptionOfferDetails?.firstOrNull()?.offerToken
        if (offerToken == null) {
            Toast.makeText(activity, "No offer available", Toast.LENGTH_SHORT).show()
            return
        }
        paramsBuilder.setOfferToken(offerToken)
    }

    val billingFlowParams = BillingFlowParams.newBuilder()
        .setProductDetailsParamsList(listOf(paramsBuilder.build()))
        .build()

    client.launchBillingFlow(activity, billingFlowParams)
}

private fun handlePurchase(
    purchase: Purchase,
    client: BillingClient?,
    context: Context,
    onSuccess: () -> Unit
) {
    if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
        saveSubscriptionStatus(context, true)
        Toast.makeText(context, "Subscription Activated!", Toast.LENGTH_LONG).show()
        
        if (!purchase.isAcknowledged && client != null) {
            val params = AcknowledgePurchaseParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken).build()
            client.acknowledgePurchase(params) { result ->
                if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                    Log.d("Subscription", "Purchase acknowledged")
                }
            }
        }
        onSuccess()
    }
}
