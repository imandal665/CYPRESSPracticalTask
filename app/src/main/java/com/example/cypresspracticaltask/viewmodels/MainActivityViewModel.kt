package com.example.cypresspracticaltask.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cypresspracticaltask.models.Album

import com.example.cypresspracticaltask.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repository: AlbumRepository) : ViewModel() {

    val albums: LiveData<List<Album>>
        get() = repository.latestAlbums

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAlbums()
        }
    }
}