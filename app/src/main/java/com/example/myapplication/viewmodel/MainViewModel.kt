package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.ListItem
import com.example.myapplication.repository.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = DataRepository()

    private val _searchQuery = MutableStateFlow("")
    private val _currentImagePosition = MutableStateFlow(0)
    private val _filteredList = MutableStateFlow<List<ListItem>>(emptyList())
    private val _images = MutableStateFlow(repository.getSampleImages())

    val filteredList: StateFlow<List<ListItem>> get() = _filteredList
    val images: StateFlow<List<Int>> get() = _images

    init {
        updateFilteredList()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        updateFilteredList()
    }

    fun updateImagePosition(position: Int) {
        _currentImagePosition.value = position
        updateFilteredList()
    }

    private fun updateFilteredList() {
        viewModelScope.launch {
            val position = _currentImagePosition.value
            val query = _searchQuery.value
            val baseList = repository.getListForImage(position)
            _filteredList.value = baseList.filter { it.label.contains(query, ignoreCase = true) }
        }
    }
}