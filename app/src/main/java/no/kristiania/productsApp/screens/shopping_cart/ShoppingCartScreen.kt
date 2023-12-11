package no.kristiania.productsApp.screens.shopping_cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingCartScreen (
    viewModel: ShoppingCartViewModel,
    onBackButtonClick: () -> Unit = {},
    onProductClick: (productId: Int) -> Unit = {},
    navigateToOrderHistory: () -> Unit = {}
) {
    val shoppingCartItems = viewModel.shoppingCartItems.collectAsState()
    val products = viewModel.productsInCart.collectAsState()
    val orderPrice by viewModel.totalOrderPrice.collectAsState()

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
                text = "Shopping Cart",
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(
                onClick = { navigateToOrderHistory() }
            ) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "Order history"
                )
            }
        }

        LazyColumn {
            items(shoppingCartItems.value) { item->
                val product = products.value.find { it.id == item.productId }

                if(product != null){
                    ShoppingCartItem(
                        product = product,
                        quantity = item.quantity,
                        onIncrease = { /* Handle increase logic */ },
                        onDecrease = { /* Handle decrease logic */ },
                        onDelete = { /* Handle decrease logic */ },
                        onClick = {onProductClick(product.id)}
                    )
                }
            }

        }
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(150.dp)
                .padding(8.dp),
            onClick = { viewModel.confirmOrder() },

            ) {
            Text(text = "Place order ($${orderPrice})")
        }
    }
}
