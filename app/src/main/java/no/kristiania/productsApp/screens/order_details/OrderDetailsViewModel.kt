package no.kristiania.productsApp.screens.order_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.kristiania.productsApp.data.OrderItem
import no.kristiania.productsApp.data.Product
import no.kristiania.productsApp.data.ProductRepository

// View model for the OrderDetails screen, this file makes the data available for the UI
// https://developer.android.com/reference/android/arch/lifecycle/ViewModel
class OrderDetailsViewModel : ViewModel() {

    // Order to show details for
    private val _selectedOrder = MutableStateFlow<OrderItem?>(null)
    val selectedOrder = _selectedOrder.asStateFlow()

    //Products in this order
    private val _orderProducts = MutableStateFlow<List<Product?>>(emptyList()) //Products in the order
    val orderProducts = _orderProducts.asStateFlow()

    // Launching a coroutine for setting the selected order based on param.
    // Then gets a list of products based on the list of ints stored in the OrderItem
    fun setSelectedOrder(orderId: Int) {
        viewModelScope.launch {
            _selectedOrder.value = ProductRepository.getOrderById(orderId)
            _selectedOrder.value?.let {
                _orderProducts.value = ProductRepository.getProductsById(it.products)
            }
        }
    }
}