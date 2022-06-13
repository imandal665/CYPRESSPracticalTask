package com.example.cypresspracticaltask.roomDb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cypresspracticaltask.models.Album
import com.example.cypresspracticaltask.models.ImageModel

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAlbum(album: Album)

    @Query("UPDATE album_table set imageList=:imageList WHERE id=:albumId")
    fun updateImageList(imageList: List<ImageModel>, albumId: Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAlbumList(albums: List<Album>)

    @Query("SELECT * FROM album_table ORDER BY id ASC")
    fun getAllAlbum(): LiveData<List<Album>>
}