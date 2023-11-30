package no.kristiania.productsApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import no.kristiania.productsApp.screens.product_details.ProductDetailsViewModel
import no.kristiania.productsApp.screens.product_list.ProductListScreen
import no.kristiania.productsApp.screens.product_list.ProductListViewModel
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
