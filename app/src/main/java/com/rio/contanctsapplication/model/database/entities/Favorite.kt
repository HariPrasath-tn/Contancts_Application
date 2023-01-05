package com.rio.contanctsapplication.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = false)
    val contactName: String
)
