package com.example.cypresspracticaltask.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cypresspracticaltask.adapters.AlbumsAdapter
import com.example.cypresspracticaltask.models.Album
import com.example.cypresspracticaltask.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivityViewModel(val repository: AlbumRepository) : ViewModel() {

    val albums: LiveData<List<Album>>
        get() = if (repository.savedAlbumsLiveData.value?.isNotEmpty() == true) {
            repository.savedAlbumsLiveData
        } else {
//            repository.latestAlbums
            repository.savedAlbumsLiveData
        }

    val savedAlbums: LiveData<List<Album>>?
        get() = repository.savedAlbumsLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAlbums()
//            repository.getAlbumsFromDb()
        }
    }

    fun fetchImagesForAlbum(albumList: List<Album>, adapter: AlbumsAdapter) {
        viewModelScope.launch(Dispatchers.IO) {
            for (i in albumList.indices) {
                albumList[i].id?.let { repository.getImagesForAlbum(it) }
            }
            MainScope().launch { adapter.notifyDataSetChanged() }
        }
    }

}