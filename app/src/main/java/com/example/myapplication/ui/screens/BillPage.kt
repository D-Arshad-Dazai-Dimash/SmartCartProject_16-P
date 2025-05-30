package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.components.BottomNavigationBar
import com.example.myapplication.viewModel.CartViewModel

@Composable
fun BillPage(navController: NavController, cartViewModel: CartViewModel) {
    val cartProducts = cartViewModel.cartProducts

    val total: Double = cartProducts.sumOf { it.price * it.quantity }
    val formattedTotal = String.format("%.2f", total)

    LaunchedEffect(navController) {
        navController.currentBackStackEntry?.let { backStackEntry ->
            backStackEntry.lifecycle.addObserver(
                androidx.lifecycle.LifecycleEventObserver { _, event ->
                    if (event == androidx.lifecycle.Lifecycle.Event.ON_DESTROY) {
                        cartViewModel.clearCart()
                    }
                }
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {


        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .background(Color(0xFFF5F5F5), CircleShape)
                .align(Alignment.TopStart)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Scaffold(
            containerColor = Color.White,
            bottomBar = { BottomNavigationBar(navController = navController) },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = 46.dp,
                            start = 19.dp,
                            end = 19.dp
                        )
                        .padding(paddingValues)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "MAGNUM SHOPPING CENTER",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "TAX#01234567891234 (VAT Included)", fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Tel 0123456789", fontSize = 14.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "RECEIPT / TAX INVOICE",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        cartProducts.forEach { product ->
                            Text(
                                text = "${product.quantity} ${product.name} - ${product.price * product.quantity} TG",
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Divider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Total", fontSize = 18.sp, fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            "$formattedTotal TG",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "MAGNUM SHOPPING CENTER",
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color.Black
                    )
                    Text(
                        text = "16/9/2566  9:41 p.m.",
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color.Black
                    )
                }
            })
    }
}
