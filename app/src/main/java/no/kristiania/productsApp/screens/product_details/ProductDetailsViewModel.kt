package no.kristiania.productsApp.screens.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.kristiania.productsApp.data.Product
import no.kristiania.productsApp.data.ProductRepository
import no.kristiania.productsApp.data.ShoppingCartItem

// A enum class used to tell the user the result when adding a product to the cart
enum class CartAdditionResult {
    SUCCESS,
    ERROR
}

// View model for the Product details screen, this file makes the data available for the UI
// https://developer.android.com/reference/android/arch/lifecycle/ViewModel
class ProductDetailsViewModel : ViewModel() {

    //The selected product to show details about
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    // Boolean to indicate if the data is loading or not
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    // a enum value to tell the user the result of of adding a product to cart
    private val _cartAdditionResult = MutableStateFlow<CartAdditionResult?>(null)
    val cartAdditionResult: StateFlow<CartAdditionResult?> = _cartAdditionResult

    // Launching a coroutine for setting the selected product based on param. Setting the loading value
    fun setSelectedProduct(productId: Int) {
        viewModelScope.launch {
            _loading.value = true
            _selectedProduct.value = ProductRepository.getProductById(productId)
            _loading.value = false
        }
    }

    //Function that runs when user hits the add to cart button. Uses the selectedProduct and makes
    // a new ShoppingCartItem with the values it needs. Adding the new item to the database and
    // changes the CartAdditionResult based on the result.
    fun addProductToCart() {
        val product = _selectedProduct.value
        if (product != null){
            viewModelScope.launch {
                try {
                    val newItem = ShoppingCartItem(product.id, 1)
                    ProductRepository.addShoppingCartItem(newItem)
                    _cartAdditionResult.value = CartAdditionResult.SUCCESS
                } catch (e: Exception) {
                    _cartAdditionResult.value = CartAdditionResult.ERROR
                }
            }
        }
    }
}