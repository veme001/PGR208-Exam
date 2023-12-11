package no.kristiania.productsApp.screens.order_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.kristiania.productsApp.data.OrderItem
import no.kristiania.productsApp.data.Product
import no.kristiania.productsApp.data.ProductRepository

class OrderDetailsViewModel : ViewModel() {
    private val _selectedOrder = MutableStateFlow<OrderItem?>(null)
    val selectedOrder = _selectedOrder.asStateFlow()

    private val _orderProducts = MutableStateFlow<List<Product?>>(emptyList())
    val orderProducts = _orderProducts.asStateFlow()

    fun setSelectedOrder(orderId: Int) {
        viewModelScope.launch {
            _selectedOrder.value = ProductRepository.getOrderById(orderId)
            _selectedOrder.value?.let {
                _orderProducts.value = ProductRepository.getProductsById(it.products)
            }
        }
    }
}