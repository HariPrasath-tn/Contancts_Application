package com.rio.contanctsapplication.model.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class FavoriteAndContact(
    @Embedded val favorite: Favorite,
    @Relation(
        parentColumn = "contactName",
        entityColumn = "contactName"
    )
    val contact:Contact
)
