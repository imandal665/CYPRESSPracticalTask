package com.example.cypresspracticaltask.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cypresspracticaltask.interfaces.ApiInterface
import com.example.cypresspracticaltask.models.Album
import com.example.cypresspracticaltask.models.ImageModel
import com.example.cypresspracticaltask.roomDb.AlbumDao

class AlbumRepository(
    private val apiInterface: ApiInterface,
    private val albumDao: AlbumDao
) {

    private val latestAlbumsLiveData = MutableLiveData<List<Album>>()
    private val latestImagesLiveData = MutableLiveData<List<ImageModel>>()

    val latestAlbums: LiveData<List<Album>>
        get() = latestAlbumsLiveData

    var savedAlbumsLiveData: LiveData<List<Album>> = albumDao.getAllAlbum()

    suspend fun getAlbums() {
        val result = apiInterface.getAlbums()
        if (result.isSuccessful && result.body() != null) {
            val items = result.body()
            if (items?.isNotEmpty() == true && items.size > 4) {
                latestAlbumsLiveData.postValue(items.subList(0, 4))
            }
        }
    }


    suspend fun getImagesForAlbum(albumId: Long) {
        val result = apiInterface.getImagesOfAnAlbum(albumId)
        if (result.isSuccessful && result.body() != null) {
            val items = result.body()
            if (items?.isNotEmpty() == true && items.size > 4) {
                latestImagesLiveData.postValue(items.subList(0, 4))
                latestAlbumsLiveData.value?.forEach {
                    if (it.id == albumId) {
                        if (it.imageList.isNullOrEmpty()) {
                            it.imageList = items
                            albumDao.addAlbum(it)
                        }
                    }
                }
            }
        }
    }

}