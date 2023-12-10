package no.kristiania.productsApp.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.kristiania.productsApp.data.OrderItem
import no.kristiania.productsApp.data.Product
import no.kristiania.productsApp.data.ProductRepository

class OrderViewModel : ViewModel() {
    private val _orderItems = MutableStateFlow<List<OrderItem>>(emptyList())
    val orderItems = _orderItems.asStateFlow()

    private val _productsInOrder = MutableStateFlow<List<Product>>(emptyList())
    val productsInOrder = _productsInOrder.asStateFlow()

    fun loadOrders(){
        viewModelScope.launch(Dispatchers.IO) {

            val orderItems = ProductRepository.getOrders()
            _orderItems.value = orderItems

            val productIds = orderItems.flatMap { it.products }

            val products = ProductRepository.getProductsById(productIds)

            _productsInOrder.value = products

        }
    }
}