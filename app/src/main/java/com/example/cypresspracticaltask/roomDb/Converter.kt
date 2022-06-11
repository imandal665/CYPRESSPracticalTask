package com.example.cypresspracticaltask.roomDb

import androidx.room.TypeConverter
import com.example.cypresspracticaltask.models.ImageModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class AlbumImageConverter {
    @TypeConverter
    fun convertVettingArr(list: List<ImageModel?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toVettingItems(value: String?): List<ImageModel>? {
        val listType: Type = object : TypeToken<List<ImageModel>?>() {}.type
        return Gson().fromJson(value, listType)
    }
}