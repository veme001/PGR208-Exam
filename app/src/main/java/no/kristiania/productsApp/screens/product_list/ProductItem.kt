package no.kristiania.productsApp.screens.product_list


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import no.kristiania.productsApp.data.Product

@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp
            )
            .clickable {
                onClick()
            }
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10)
            ),
        shape = RoundedCornerShape(10),

    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
                AsyncImage(
                    modifier = Modifier
                        .size(108.dp, 108.dp)
                        .background(color = Color.Gray),
                    model = product.image,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Image of ${product.title}"
                )
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

            ){
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = product.title,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "$${product.price}",
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}
