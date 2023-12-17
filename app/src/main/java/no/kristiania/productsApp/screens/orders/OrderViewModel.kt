package no.kristiania.productsApp.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.kristiania.productsApp.data.OrderDetails
import no.kristiania.productsApp.data.OrderItem
import no.kristiania.productsApp.data.ProductQuantity
import no.kristiania.productsApp.data.ProductRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// View model for the Order screen, this file makes the data available for the UI
// https://developer.android.com/reference/android/arch/lifecycle/ViewModel
class OrderViewModel : ViewModel() {

    //List of all the orders
    private val _orderItems = MutableStateFlow<List<OrderItem>>(emptyList())
    val orderItems = _orderItems.asStateFlow()

    //A list of orderDetails used to store more details about the orders
    private val _orderDetailsList = MutableStateFlow<List<OrderDetails>>(emptyList())
    val orderDetailsList = _orderDetailsList.asStateFlow()

    //Function launched in the main activity on navigating to the Order screen
    //Populates the orderItems with data from the database. Uses this information to make a new object with more details
    fun loadOrders(){
        viewModelScope.launch(Dispatchers.IO) {
            val orderItems = ProductRepository.getOrders()
            _orderItems.value = orderItems

            //Making a list of details. This list use data from the original OrderItem (id, price), but formats the date,
            // count the number of items and uses the list of ids to find the products and its quantity
            val detailsList = orderItems.map {item ->
                val productIds = item.products
                val products = ProductRepository.getProductsById(productIds)

                val productQuantities = productIds
                    .groupingBy { it }
                    .eachCount()
                    .map { (productId, quantity) ->
                        ProductQuantity(productId, products.find { it.id == productId }?.title ?: "", quantity)
                    }
                // Making a new object with more detailed information to use in the UI
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
    // Formatting function to format the date of the order
    fun formatDate(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("HH:mm EEEE d MMMM yyyy", Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }
}
