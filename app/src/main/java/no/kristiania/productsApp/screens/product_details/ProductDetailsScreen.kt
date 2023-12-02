package no.kristiania.productsApp.screens.product_details

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import no.kristiania.productsApp.data.Product

@Composable
fun ProductDetailsScreen (
    viewModel: ProductDetailsViewModel,
    onBackButtonClick: () -> Unit = {}
) {

    val productState = viewModel.selectedProduct.collectAsState()

    val product = productState.value
    if (product == null) {
        Text(text = "Failed to get product details. Selected product object is NULL")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent),
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
                onClick = {}
            ) {
                Icon (
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "shopping cart"
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(color = Color.Gray)
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = rememberAsyncImagePainter(model = product?.imageUrl),
                contentScale = ContentScale.Crop,
                contentDescription = "Image of ${product?.title}"
            )
        }


        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
        ) {

            Text(text = "Title: ${product?.title}")
            Text(text = "Price: ${product?.price}")
            Text(text = "Rating: ${product?.rating}")
            Text(text = "Description: ${product?.description}")
        }
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(150.dp)
                .padding(8.dp),
            onClick = { /*TODO*/ },

        ) {
            Text(text = "Add to cart")
            }
    }
}