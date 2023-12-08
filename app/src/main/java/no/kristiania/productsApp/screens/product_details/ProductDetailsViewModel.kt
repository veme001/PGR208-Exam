package no.kristiania.productsApp.screens.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.kristiania.productsApp.data.Product
import no.kristiania.productsApp.data.ProductRepository
import no.kristiania.productsApp.data.ShoppingCartItem

class ProductDetailsViewModel : ViewModel() {
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    fun setSelectedProduct(productId: Int) {
        viewModelScope.launch {
            _selectedProduct.value = ProductRepository.getProductById(productId)
        }
    }

    fun addProductToCart() {
        val product = _selectedProduct.value
        if (product != null){
            viewModelScope.launch {
                val newItem = ShoppingCartItem(product.id, 1)
                ProductRepository.addShoppingCartItem(newItem)
            }
        }
    }

}