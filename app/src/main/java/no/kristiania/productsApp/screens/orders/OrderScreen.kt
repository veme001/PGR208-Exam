package no.kristiania.productsApp.screens.orders

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// Order screen responsible for the UI of order history
@Composable
fun OrdersScreen (
    viewModel: OrderViewModel,
    onOrderClick: (orderId: Int) -> Unit = {},
    onBackButtonClick: () -> Unit = {},
    navigateToProductListScreen: () -> Unit = {},
    navigateToShoppingCart: () -> Unit = {},
) {
    val orders = viewModel.orderDetailsList.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row (
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
                    contentDescription = "Navigate Back"
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
                text = "Order history",
                style = MaterialTheme.typography.titleLarge
            )
            Row{
                IconButton(
                    onClick = { navigateToShoppingCart() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "shopping cart"
                    )
                }
            }
        }
        // if check to check if order history is empty or not. if yes then gives message to user
        if(orders.value.isEmpty()){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Shopping cart",
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    text = "No orders placed yet...",
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Center
                )
            }
            return
        }

        Divider()

        Spacer(modifier = Modifier
            .height(8.dp)
        )

        // Lazycolumn to display orders in a scrollable list
        LazyColumn {
            items(orders.value.reversed()) { order->
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
