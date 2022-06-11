package com.example.cypresspracticaltask.models

import androidx.room.Entity
import androidx.room.PrimaryKey

//{
//    "userId": 1,
//    "id": 1,
//    "title": "quidem molestiae enim"
//},
@Entity(tableName = "album_table")
data class Album(
    val userId: Long? = 0L,
    @PrimaryKey(autoGenerate = false)
    val id: Long? = 0L,
    val title: String? = "",
    var imageList: List<ImageModel>? = null
)
