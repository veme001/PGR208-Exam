package no.kristiania.productsApp.screens.shopping_cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.kristiania.productsApp.data.OrderItem
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
            _totalOrderPrice.value = getOrderPrice()
        }

    }

    private fun getOrderPrice(): Double {
        return _shoppingCartItems.value.sumOf { item ->
            val product = _productsInCart.value.find { it.id == item.productId }
            product?.price?.times(item.quantity) ?: 0.0
        }
    }

    fun confirmOrder(){
        val cartItems = _shoppingCartItems.value
        val products = _productsInCart.value

        if (cartItems.isNotEmpty() && products.isNotEmpty()){
            viewModelScope.launch {
                val productIds = mutableListOf<Int>()

                cartItems.forEach { item ->
                    repeat(item.quantity){
                        productIds.add(item.productId)
                    }
                }
                val newOrder = OrderItem(
                    products = productIds,
                    priceOfOrder = _totalOrderPrice.value,
                    date = System.currentTimeMillis()
                )

                ProductRepository.addOrder(newOrder)
                clearShoppingCart()
            }
        }
        else {
            Log.d("Placing order", "The shopping cart is empty")
        }
    }

    private fun clearShoppingCart() {
        viewModelScope.launch {

            _shoppingCartItems.value.forEach { item ->
                ProductRepository.removeShoppingCartItem(item)
            }

            _shoppingCartItems.value = emptyList()
            _productsInCart.value = emptyList()
            _totalOrderPrice.value = 0.0
        }
    }
}