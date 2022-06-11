package com.example.cypresspracticaltask.roomDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cypresspracticaltask.models.Album

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAlbum(album: Album)

    @Query("SELECT * FROM album_table ORDER BY id ASC")
    fun getAllAlbum(): LiveData<List<Album>>
}