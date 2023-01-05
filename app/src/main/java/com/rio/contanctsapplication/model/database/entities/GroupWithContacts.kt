package com.rio.contanctsapplication.model.database.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * [GroupWithContacts] joined data class that contains contacts list of a particular group
 */
data class GroupWithContacts (
    @Embedded val group: Group,
    @Relation(
        parentColumn = "groupName",
        entityColumn = "contactName",
        // providing the reference to associate the data
        associateBy = Junction(GroupContactCrossRef::class)
    )
    val contacts: List<Contact>
)