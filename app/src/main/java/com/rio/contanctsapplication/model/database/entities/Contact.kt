package com.rio.contanctsapplication.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * [Contact] base entity data class that stores all the contact details
 */
@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = false)
    var contactName:String,
    var phoneNumber:String,
    var mobileNumber: String,
    var mailId:String="",
    var description:String="",
    var dob:String="",
    var images:String="",
    var favourite:Byte=0
)
