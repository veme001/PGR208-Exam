package no.kristiania.productsApp.screens.product_list

import android.util.Log
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import no.kristiania.productsApp.data.Product
import no.kristiania.productsApp.data.ProductRepository
import java.util.Locale.Category

class ProductListViewModel : ViewModel() {

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
