package no.kristiania.productsApp.screens.order_details


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.text.DateFormat
import java.util.Locale


@Composable
fun OrderDetailsScreen (
    viewModel: OrderDetailsViewModel,
    onBackButtonClick: () -> Unit = {},
    navigateToProductListScreen: () -> Unit = {},
    navigateToShoppingCart: () -> Unit = {},
    navigateToOrderHistory: () -> Unit = {}
) {

    val orderState = viewModel.selectedOrder.collectAsState()
    val orderProducts = viewModel.orderProducts.collectAsState()

    val order = orderState.value
    if (order == null) {
        Text(text = "Failed to get order details. Selected order object is NULL")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { onBackButtonClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back icon"
                )
            }
            IconButton(
                onClick = { navigateToProductListScreen() }
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home icon"
                )
            }

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                text = "Order details",
                style = MaterialTheme.typography.titleLarge,
            )
            Row {
                IconButton(
                    onClick = { navigateToShoppingCart() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "shopping cart"
                    )
                }
                IconButton(
                    onClick = { navigateToOrderHistory() }
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Order history"
                    )
                }
            }
        }

        Divider()
        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        ) {
            if (order != null) {
                Text(text = "Order id: ${order.orderId}")
                Text(text = "Order date: ${formatOrderDate(dateInMillis = order.date)}")
                Text(text = "Total price: $${order.priceOfOrder}")
            }
        }

        LazyColumn {
            items(orderProducts.value) { product ->
                if (product != null) {
                    OrderDetail(
                        image = product.image,
                        title = product.title,
                        price = product.price,
                        category = product.category
                    )
                }
            }
        }
    }
}

@Composable
fun formatOrderDate(dateInMillis: Long): String {
    val dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, Locale.getDefault())
    return dateFormat.format(dateInMillis)
}
