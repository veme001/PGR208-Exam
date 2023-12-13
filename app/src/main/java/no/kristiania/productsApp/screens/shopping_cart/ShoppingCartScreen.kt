package no.kristiania.productsApp.screens.shopping_cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingCartScreen (
    viewModel: ShoppingCartViewModel,
    onBackButtonClick: () -> Unit = {},
    onProductClick: (productId: Int) -> Unit = {},
    navigateToOrderHistory: () -> Unit = {},
    navigateToProductListScreen: () -> Unit = {},
) {
    val shoppingCartItems = viewModel.shoppingCartItems.collectAsState()
    val products = viewModel.productsInCart.collectAsState()
    val orderPrice by viewModel.totalOrderPrice.collectAsState()

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
                text = "Shopping cart",
                style = MaterialTheme.typography.titleLarge
            )
            Row {
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

        if(shoppingCartItems.value.isEmpty()){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Shopping cart",
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    text = "No items in the shopping cart",
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Center
                )
            }
            return
        }

        LazyColumn {
            items(shoppingCartItems.value) { item->
                val product = products.value.find { it.id == item.productId }

                if(product != null){
                    ShoppingCartItem(
                        product = product,
                        quantity = item.quantity,
                        onIncrease = { viewModel.increaseCartQuantity(item) },
                        onDecrease = { viewModel.decreaseCartQuantity(item) },
                        onDelete = { viewModel.deleteCartItem(item) },
                        onClick = {onProductClick(product.id)}
                    )
                }
            }

        }
        if(shoppingCartItems.value.isNotEmpty()){
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
}
