package no.kristiania.productsApp.screens.shopping_cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.kristiania.productsApp.data.Product

class shoppingCartViewModel : ViewModel() {
    private val _shoppingCartItems = MutableStateFlow<List<Product>>(emptyList())
    val shoppingCartItems = _shoppingCartItems.asStateFlow()

    fun loadShoppingCart(){
        viewModelScope.launch {
            //set the shoppingcart items
        }
    }
}