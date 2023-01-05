package com.rio.contanctsapplication.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Group(
    @PrimaryKey(autoGenerate = false)
    var groupName:String,
    var groupDescription:String,
    var image:String="",
)
