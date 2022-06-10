package com.example.cypresspracticaltask.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cypresspracticaltask.interfaces.ApiInterface
import com.example.cypresspracticaltask.models.Album

class AlbumRepository(private val apiInterface: ApiInterface) {

    private val latestAlbumsLiveData = MutableLiveData<List<Album>>()

    val latestAlbums: LiveData<List<Album>>
        get() = latestAlbumsLiveData

    suspend fun getAlbums() {
        val result = apiInterface.getAlbums(1, 4)
        if (result.isSuccessful && result.body() != null) {
//            latestAlbumsLiveData.postValue(result.body())
            val items = result.body()
            if (items?.isNotEmpty() == true && items.size > 4) {
                latestAlbumsLiveData.postValue(items.subList(0, 4))
            } else if (items?.isNotEmpty() == true) {
                latestAlbumsLiveData.postValue(items)
            }
        }
    }

}