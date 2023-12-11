package no.kristiania.productsApp.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.kristiania.productsApp.data.OrderDetails
import no.kristiania.productsApp.data.OrderItem
import no.kristiania.productsApp.data.Product
import no.kristiania.productsApp.data.ProductQuantity
import no.kristiania.productsApp.data.ProductRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderViewModel : ViewModel() {
    private val _orderItems = MutableStateFlow<List<OrderItem>>(emptyList())
    val orderItems = _orderItems.asStateFlow()

    private val _productsInOrder = MutableStateFlow<List<Product>>(emptyList())
    val productsInOrder = _productsInOrder.asStateFlow()

    private val _orderDetailsList = MutableStateFlow<List<OrderDetails>>(emptyList())
    val orderDetailsList = _orderDetailsList.asStateFlow()

    fun loadOrders(){
        viewModelScope.launch(Dispatchers.IO) {
            val orderItems = ProductRepository.getOrders()
            _orderItems.value = orderItems

            val detailsList = orderItems.map {item ->
                val productIds = item.products
                val products = ProductRepository.getProductsById(productIds)

                val productQuantities = productIds
                    .groupingBy { it }
                    .eachCount()
                    .map { (productId, quantity) ->
                        ProductQuantity(productId, products.find { it.id == productId }?.title ?: "", quantity)
                    }
                OrderDetails(
                    orderId = item.orderId,
                    date = formatDate(item.date),
                    numberOfItems = item.products.size,
                    totalOrderPrice = item.priceOfOrder,
                    products = productQuantities
                )
            }
            _orderDetailsList.value = detailsList
        }

    }
    fun formatDate(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }
}
