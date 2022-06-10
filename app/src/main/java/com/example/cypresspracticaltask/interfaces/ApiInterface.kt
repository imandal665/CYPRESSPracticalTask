package com.example.cypresspracticaltask.interfaces

import com.example.cypresspracticaltask.models.Album
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/albums")
    suspend fun getAlbums(): Response<List<Album>>

    @GET("/albums")
    suspend fun getAlbums(
        @Query("page") page: Int,
        @Query("count") count: Int
    ): Response<List<Album>>

//    @GET("/photos")
//    suspend fun getPhotosOfAnAlbum()
}