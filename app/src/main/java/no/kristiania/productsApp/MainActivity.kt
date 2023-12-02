package no.kristiania.productsApp

import android.os.Bundle
import android.util.proto.ProtoOutputStream
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import no.kristiania.productsApp.data.ProductRepository
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
                val navController = rememberNavController()


                NavHost(
                    navController = navController,
                    startDestination = "productListScreen"
                ) {
                    composable(
                        route = "productListScreen"
                    ) {
                        ProductListScreen(
                            viewModel = _productListViewModel,
                            onProductClick = {productId ->
                                navController.navigate("productDetailsScreen/${productId}")
                            }
                        )
                    }
                    composable(
                        route = "productDetailsScreen/{productId}",
                        arguments = listOf(
                            navArgument(name = "productId") {
                                type = NavType.IntType
                            }
                        )
                    ) {backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId") ?: -1

                        LaunchedEffect(productId) {
                            _productDetailsViewModel.setSelectedProduct(productId)
                        }

                        ProductDetailsScreen(
                            viewModel = _productDetailsViewModel,
                            onBackButtonClick = {navController.popBackStack()}
                        )
                    }
                }
            }
        }
    }
}
