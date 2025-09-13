package com.example.covertervk.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TranslationEntity::class],
    version = 2,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun translationDao(): TranslationDao
}

