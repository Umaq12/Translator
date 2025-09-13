package com.example.covertervk.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "translations")
data class TranslationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val source: String,
    val translated: String,
    val transcription: String?,
    val createdAt: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
)
