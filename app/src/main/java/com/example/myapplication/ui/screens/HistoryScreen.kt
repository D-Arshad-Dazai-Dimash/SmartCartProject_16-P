package com.example.myapplication.ui.screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.components.BottomNavigationBar
import com.example.myapplication.viewModel.CartViewModel
import com.example.myapplication.viewModel.Order

@Composable
fun HistoryScreen(navController: NavController, cartViewModel: CartViewModel) {
    cartViewModel.loadOrderHistory()

    val orderHistory = cartViewModel.orderHistory

    Scaffold(
        containerColor = Color.White,
        bottomBar = { BottomNavigationBar(navController = navController) },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFFFFF))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "Back arrow",
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                        .size(45.dp, 44.dp)
                        .clickable {
                            navController.popBackStack("home", inclusive = false)
                        }
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .padding(paddingValues)
                ) {
                    Spacer(modifier = Modifier.height(28.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "History", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Order completed", fontSize = 14.sp, color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (orderHistory.isEmpty()) {
                        Text("No orders found", fontSize = 16.sp)
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(orderHistory) { order ->
                                OrderCard(order, navController)
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun OrderCard(order: Order, navController: NavController) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("orderDetails/${order.orderId}")
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFF00C853), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(text = "Paid", color = Color.White, fontSize = 14.sp)
                }

                Text(
                    text = "${order.totalAmount} ₸",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Order Date: ${order.date}",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                order.products.take(4).forEach { product ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        val imageRes = when (product.barcode) {
                            "1234567890" -> R.drawable.maccoffee
                            "4870202521186" -> R.drawable.import_files_aksi
                            "5449000189325" -> R.drawable.ftpeach
                            else -> R.drawable.bon_aqua
                        }
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = product.name,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(text = product.name, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun OrderDetailsScreen(
    orderId: String,
    cartViewModel: CartViewModel,
    navController: NavController
) {
    val order = cartViewModel.orderHistory.firstOrNull { it.orderId == orderId }

    if (order != null) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Order ID: ${order.orderId}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Order Date: ${order.date}",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            order.products.forEach { product ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val imageRes = when (product.barcode) {
                            "1234567890" -> R.drawable.maccoffee
                            "4870202521186" -> R.drawable.import_files_aksi
                            "5449000189325" -> R.drawable.ftpeach
                            else -> R.drawable.bon_aqua
                        }
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = product.name,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "${product.name}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }

                    Text(
                        text = "${product.price} ₸ x ${product.quantity}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Total Amount: ${order.totalAmount} ₸",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7A00)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Back to Order History",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}




