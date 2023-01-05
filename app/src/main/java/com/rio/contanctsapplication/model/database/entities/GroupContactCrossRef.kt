package com.rio.contanctsapplication.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GroupContactCrossRef(
    @PrimaryKey
    val sNo:Long=0,
    val groupName:String,
    val contactName:String
)
