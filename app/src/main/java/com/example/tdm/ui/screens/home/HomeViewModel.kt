package com.example.tdm.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tdm.data.model.Restaurant
import com.example.tdm.data.repository.RestaurantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: RestaurantRepository) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurants: StateFlow<List<Restaurant>> = _restaurants

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchRestaurants() {
        _isLoading.value = true
        _error.value = null

        Log.d("RestaurantViewModel", "Fetching restaurants")
        viewModelScope.launch {
            try {
                val fetchedRestaurants = repository.getRestaurants()
                _restaurants.value = fetchedRestaurants
                Log.d("RestaurantViewModel", "Fetched ${fetchedRestaurants.size} restaurants")
            } catch (e: Exception) {
                _error.value = "An error occurred: ${e.message}"
                Log.e("RestaurantViewModel", "Error fetching restaurants", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}

class HomeViewModelFactory(private val repository: RestaurantRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
