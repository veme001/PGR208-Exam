package no.kristiania.productsApp.screens.shopping_cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.kristiania.productsApp.data.Product
import no.kristiania.productsApp.data.ProductRepository
import no.kristiania.productsApp.data.ShoppingCartItem

class ShoppingCartViewModel : ViewModel() {
    private val _shoppingCartItems = MutableStateFlow<List<ShoppingCartItem>>(emptyList())
    val shoppingCartItems = _shoppingCartItems.asStateFlow()

    private val _productsInCart = MutableStateFlow<List<Product>>(emptyList())
    val productsInCart = _productsInCart.asStateFlow()

    private val _totalOrderPrice = MutableStateFlow<Double>(0.0)
    val totalOrderPrice = _totalOrderPrice.asStateFlow()


    fun loadShoppingCart(){
        viewModelScope.launch(Dispatchers.IO) {

            val cartItems = ProductRepository.getShoppingCart()
            _shoppingCartItems.value = cartItems

            val productIds = cartItems.map { it.productId }

            val products = ProductRepository.getProductsById(productIds)

            _productsInCart.value = products

            getOrderPrice()
        }
    }

    fun getOrderPrice(): Double {
        return _productsInCart.value.sumOf { it.price }
    }
}