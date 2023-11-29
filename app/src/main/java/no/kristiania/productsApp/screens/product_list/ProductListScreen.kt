package no.kristiania.products.screens.product_list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ProductListScreen() {

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        text = "Products",
        style = MaterialTheme.typography.titleLarge
    )
}