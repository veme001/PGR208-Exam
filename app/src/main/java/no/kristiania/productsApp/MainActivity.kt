package no.kristiania.productsApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import no.kristiania.productsApp.data.ProductRepository
import no.kristiania.productsApp.screens.order_details.OrderDetailsScreen
import no.kristiania.productsApp.screens.order_details.OrderDetailsViewModel
import no.kristiania.productsApp.screens.orders.OrderViewModel
import no.kristiania.productsApp.screens.orders.OrdersScreen
import no.kristiania.productsApp.screens.product_details.ProductDetailsScreen
import no.kristiania.productsApp.screens.product_details.ProductDetailsViewModel
import no.kristiania.productsApp.screens.product_list.ProductListScreen
import no.kristiania.productsApp.screens.product_list.ProductListViewModel
import no.kristiania.productsApp.screens.shopping_cart.ShoppingCartScreen
import no.kristiania.productsApp.screens.shopping_cart.ShoppingCartViewModel
import no.kristiania.productsApp.ui.theme.ProductsAppTheme

class MainActivity : ComponentActivity() {
    private val _productListViewModel : ProductListViewModel by viewModels()
    private val _productDetailsViewModel : ProductDetailsViewModel by viewModels()
    private val _shoppingCartViewModel : ShoppingCartViewModel by viewModels()
    private val _ordersViewModel : OrderViewModel by viewModels()
    private val _ordersDetailViewModel : OrderDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ProductRepository.initiateDatabase(applicationContext)

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
                            },
                            navigateToShoppingCart = {
                                navController.navigate("shoppingCartScreen")
                            },
                            navigateToOrderHistory = {
                                navController.navigate("ordersScreen")
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
                            onBackButtonClick = {navController.popBackStack()},
                            navigateToShoppingCart = {
                                navController.navigate("shoppingCartScreen")
                            },
                            navigateToOrderHistory = {
                                navController.navigate("ordersScreen")
                            }
                        )
                    }
                    composable(route = "shoppingCartScreen") {
                        // LaunchedEffect will run it's code block first time we navigate to favoriteListScreen
                        LaunchedEffect(Unit) {
                            _shoppingCartViewModel.loadShoppingCart()
                        }
                        ShoppingCartScreen(
                            viewModel = _shoppingCartViewModel,
                            onBackButtonClick = { navController.popBackStack() },

                            onProductClick = {productId ->
                                navController.navigate("productDetailsScreen/${productId}")
                            },
                            navigateToProductListScreen = {
                                navController.navigate("productListScreen")
                            },
                            navigateToOrderHistory = {
                                navController.navigate("ordersScreen")
                            }

                        )
                    }
                    composable(route = "ordersScreen") {
                        // LaunchedEffect will run it's code block first time we navigate to favoriteListScreen
                        LaunchedEffect(Unit) {
                            _ordersViewModel.loadOrders()
                        }
                        OrdersScreen(
                            viewModel = _ordersViewModel,
                            onBackButtonClick = { navController.popBackStack() },
                            navigateToProductListScreen = {
                                navController.navigate("productListScreen")
                            },
                            navigateToShoppingCart = {
                                navController.navigate("shoppingCartScreen")
                            },
                            onOrderClick = {orderId ->
                                navController.navigate("ordersDetailsScreen/${orderId}")
                            },
                        )
                    }
                    composable(
                        route = "ordersDetailsScreen/{orderId}",
                        arguments = listOf(
                            navArgument(name = "orderId") {
                                type = NavType.IntType
                            }
                        )
                    ) {backStackEntry ->
                        val orderId = backStackEntry.arguments?.getInt("orderId") ?: -1

                        LaunchedEffect(orderId) {
                            _ordersDetailViewModel.setSelectedOrder(orderId)
                        }
                        OrderDetailsScreen(
                            viewModel = _ordersDetailViewModel,
                            onBackButtonClick = {navController.popBackStack()},
                            navigateToProductListScreen = {
                                navController.navigate("productListScreen")
                            },
                            navigateToShoppingCart = {
                                navController.navigate("shoppingCartScreen")
                            },
                            navigateToOrderHistory = {
                                navController.navigate("ordersScreen")
                            }
                        )
                    }
                }
            }
        }
    }
}
