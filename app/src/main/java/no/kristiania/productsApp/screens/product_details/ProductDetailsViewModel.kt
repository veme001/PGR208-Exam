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

enum class CartAdditionResult {
    SUCCESS,
    ERROR
}

class ProductDetailsViewModel : ViewModel() {
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _cartAdditionResult = MutableStateFlow<CartAdditionResult?>(null)
    val cartAdditionResult: StateFlow<CartAdditionResult?> = _cartAdditionResult

    fun setSelectedProduct(productId: Int) {
        viewModelScope.launch {
            _loading.value = true
            _selectedProduct.value = ProductRepository.getProductById(productId)
            _loading.value = false
        }
    }

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