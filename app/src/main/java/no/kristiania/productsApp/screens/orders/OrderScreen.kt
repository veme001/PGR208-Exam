package no.kristiania.productsApp.screens.orders

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OrdersScreen (
    viewModel: OrderViewModel,
    onOrderClick: (orderId: Int) -> Unit = {},
    onBackButtonClick: () -> Unit = {},
) {
    val orders = viewModel.orderDetailsList.collectAsState()


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            IconButton(
                onClick = { onBackButtonClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Navigate Back"
                )
            }
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Order history",
                style = MaterialTheme.typography.titleLarge
            )
        }
        Divider()

        Spacer(modifier = Modifier
            .height(8.dp)
        )

        LazyColumn {
            items(orders.value) { order->
                OrderItem(
                    date = order.date,
                    products = order.products,
                    totalPrice = order.totalOrderPrice,
                    numberOfItems = order.numberOfItems,
                    onClick = {
                        onOrderClick(order.orderId)
                    }
                )
            }
        }
    }
}
