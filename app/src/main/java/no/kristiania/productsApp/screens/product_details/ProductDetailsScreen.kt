package no.kristiania.productsApp.screens.product_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import no.kristiania.products.data.Product

@Preview
@Composable
fun ProductDetailsScreen () {

    val productDetails = Product(
        id = 1,
        title = "Holzweiler Hoodie",
        price = 299.00,
        description = "Hoodie from Holzweiler. 100% cotton",
        category = "Hoodies",
        imageUrl = "",
        rating = 4.5
    )

    AsyncImage(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray),
        model = productDetails.imageUrl,
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
        contentDescription = "Image of ${productDetails.title}"
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red),
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Blue)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = "Product details",
                style = MaterialTheme.typography.titleLarge,
            )
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(color = Color.Green)
        ) {

            Text(text = "Title: ${productDetails.title}")
            Text(text = "Price: ${productDetails.price}")
            Text(text = "Rating: ${productDetails.rating}")
            Text(text = "Description: ${productDetails.description}")
        }
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(200.dp)
                .padding(16.dp),
            onClick = { /*TODO*/ },

        ) {
            Text(text = "Legg i handlekurv")
            }
    }
}