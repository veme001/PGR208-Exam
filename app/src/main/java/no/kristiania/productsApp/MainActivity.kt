package no.kristiania.productsApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import no.kristiania.productsApp.screens.product_details.ProductDetailsViewModel
import no.kristiania.productsApp.screens.product_list.ProductListScreen
import no.kristiania.productsApp.screens.product_list.ProductListViewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import no.kristiania.productsApp.screens.product_list.ProductListScreen
import no.kristiania.productsApp.screens.product_details.ProductDetailsScreen
import no.kristiania.productsApp.ui.theme.ProductsAppTheme

class MainActivity : ComponentActivity() {
    private val _productListViewModel : ProductListViewModel by viewModels()
    private val _productDetailsViewModel : ProductDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProductsAppTheme {
                ProductListScreen(viewModel = _productListViewModel)
            }
        }
    }
}
