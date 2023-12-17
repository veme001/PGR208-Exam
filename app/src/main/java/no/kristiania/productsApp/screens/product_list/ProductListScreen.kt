package no.kristiania.productsApp.screens.product_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// Product list composable that shows all available items
@Composable
fun ProductListScreen (
    viewModel: ProductListViewModel = viewModel(),
    onProductClick: (productId: Int) -> Unit = {},
    navigateToShoppingCart: () -> Unit = {},
    navigateToOrderHistory: () -> Unit = {}
) {

    val products = viewModel.products.collectAsState()
    val loading = viewModel.loading.collectAsState()

    // Loading screen when the app is opened
    if (loading.value) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

        ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Products",
                style = MaterialTheme.typography.titleLarge
            )
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navigateToShoppingCart() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Shopping cart"
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

            }

        Spacer(modifier = Modifier.size(8.dp))

        // Row with filter buttons for the different categories
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
            Arrangement.SpaceBetween
        ) {
            CategoryFilterButton("electronics", viewModel, "Electronics")
            CategoryFilterButton("jewelery", viewModel, "Jewelery")
            CategoryFilterButton("men's clothing", viewModel, "Men")
            CategoryFilterButton("women's clothing", viewModel, "Women")
        }

        Spacer(modifier = Modifier.size(8.dp))

        // Lazycolumn to display the items in the shop
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(products.value){product ->
                ProductItem(
                    product = product,
                    onClick = {
                        onProductClick(product.id)
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFilterButton(category: String, viewModel: ProductListViewModel, displayText: String) {
    val isSelected = viewModel.selectedCategory.collectAsState().value == category

    FilterChip(
        selected = isSelected,
        onClick = {
            if (isSelected) {
                viewModel.setSelectedCategory("All")
            } else {
                viewModel.setSelectedCategory(category)
            }
        },
        label = { Text(text = displayText) },
    )

}
