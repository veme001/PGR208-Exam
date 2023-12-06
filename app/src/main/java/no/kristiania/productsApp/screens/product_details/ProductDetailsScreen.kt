package no.kristiania.productsApp.screens.product_details


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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun ProductDetailsScreen (
    viewModel: ProductDetailsViewModel,
    onBackButtonClick: () -> Unit = {},
    navigateToShoppingCart: () -> Unit = {}
) {

    val productState = viewModel.selectedProduct.collectAsState()

    val product = productState.value
    if (product == null) {
        Text(text = "Failed to get product details. Selected product object is NULL")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
            .verticalScroll(rememberScrollState())
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { onBackButtonClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Refresh products"
                )
            }

            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = "Product details",
                style = MaterialTheme.typography.titleLarge,
            )
            IconButton(
                onClick = { navigateToShoppingCart() }
            ) {
                Icon (
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "shopping cart"
                )
            }
        }

        Divider()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(64.dp)
                .background(color = Color.Transparent)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = product?.image,
                //painter = painterResource(id = R.drawable.placeholder_image),
                contentScale = ContentScale.Fit,
                contentDescription = "Image of ${product?.title}"
            )
        }

        Divider()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = product?.title ?: "Product Title",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$${product?.price ?: "N/A"}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star",
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Rating: ${product?.rating?.rate} (${product?.rating?.count} reviews)",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product?.description ?: "No description available",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(150.dp)
                .padding(8.dp),
            onClick = { viewModel.addProductToCart() },

            ) {
            Text(text = "Add to cart")
        }
    }
}