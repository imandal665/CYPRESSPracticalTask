package com.example.cypresspracticaltask.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cypresspracticaltask.adapters.AlbumsAdapter
import com.example.cypresspracticaltask.managers.InternetCheckManager
import com.example.cypresspracticaltask.models.Album
import com.example.cypresspracticaltask.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivityViewModel(val repository: AlbumRepository) : ViewModel() {

    val albums: LiveData<List<Album>>
        get() = repository.latestAlbums

    val localAlbums: LiveData<List<Album>>
        get() = repository.savedAlbumsLiveData


    val isInternetAvailable = MutableLiveData<Boolean>()

    init {
        InternetCheckManager(object : InternetCheckManager.Consumer {
            override fun accept(internet: Boolean?) {
                if (internet == true) {
                    isInternetAvailable.postValue(true)
                    viewModelScope.launch(Dispatchers.IO) { repository.getAlbums() }
                } else
                    isInternetAvailable.postValue(false)
            }
        })
    }

    fun fetchImagesForAlbum(albumList: List<Album>, adapter: AlbumsAdapter) {
        if (isInternetAvailable.value == true)
            viewModelScope.launch(Dispatchers.IO) {
                for (i in albumList.indices) {
                    albumList[i].id?.let { repository.getImagesForAlbum(it) }
                }
                MainScope().launch { adapter.notifyDataSetChanged() }
            }
    }

}