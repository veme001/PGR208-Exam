package no.kristiania.productsApp.screens.shopping_cart


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import no.kristiania.productsApp.data.Product

// Composable that handles how the cards in the shoppingcart look like

@Composable
fun ShoppingCartItem(
    product: Product,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit = {}
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable { onClick() }
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(80.dp, 80.dp)
                    .background(color = Color.Gray),
                model = product.image,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                contentDescription = "Image of ${product.title}"
            )
            Spacer(modifier = Modifier.width(6.dp))

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = product.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Button(onClick = { onDecrease() },
                        modifier = Modifier.weight(0.1f)
                    ) {
                        Text(text = "-")
                    }
                    Text(text = "Quantity: $quantity", modifier = Modifier.padding(4.dp))

                    Button(onClick = { onIncrease() },
                        modifier = Modifier.weight(0.1f)
                    ) {
                        Text(text = "+")
                    }
                    IconButton(
                        onClick = { onDelete() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Gray
                        )
                    }
                }
            }

        }

    }

}