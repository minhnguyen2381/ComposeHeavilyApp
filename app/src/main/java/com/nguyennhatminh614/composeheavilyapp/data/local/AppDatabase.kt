package com.nguyennhatminh614.composeheavilyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import  com.nguyennhatminh614.composeheavilyapp.data.local.dao.UserDao
import  com.nguyennhatminh614.composeheavilyapp.data.local.entity.UserEntity

@Database(
    version = 1,
    entities = [
        UserEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
