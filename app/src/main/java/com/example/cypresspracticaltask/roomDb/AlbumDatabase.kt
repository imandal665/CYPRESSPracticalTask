package com.example.cypresspracticaltask.roomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cypresspracticaltask.models.Album

@Database(entities = [Album::class], version = 1, exportSchema = false)
@TypeConverters(AlbumImageConverter::class)
abstract class AlbumDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao

    companion object {
        @Volatile
        private var DB_INSTANCE: AlbumDatabase? = null

        fun getDatabase(context: Context): AlbumDatabase {
            val temInstance = DB_INSTANCE
            if (temInstance != null)
                return temInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlbumDatabase::class.java,
                    "user_database"
                ).build()
                DB_INSTANCE = instance
                return instance
            }
        }
    }
}