package no.kristiania.productsApp.screens.product_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.kristiania.productsApp.data.Product
import no.kristiania.productsApp.data.ProductRepository

// View model for the ProductList screen, this file makes the data available for the UI
// https://developer.android.com/reference/android/arch/lifecycle/ViewModel
class ProductListViewModel : ViewModel() {

    // Boolean to indicate if the data is loading or not
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            val category = _selectedCategory.value
            Log.d("ProductListViewModel", "Loading products for category: $category")
            _products.value = when (category) {
                "All" -> ProductRepository.getAllProducts()
                else -> ProductRepository.getProductsByCategory(category)
            }
            _loading.value = false
        }
    }

    fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
        Log.d("ProductListViewModel", "Selected category: $category ")
        loadProducts()
    }

}
