package com.rio.contanctsapplication.model.database.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * [ContactWithGroups] joined data class that contains groups list of a particular contact
 */
data class ContactWithGroups(
    @Embedded val contact: Contact,
    @Relation(
        parentColumn = "contactName",
        entityColumn = "groupName",
        // providing the reference to associate the data
        associateBy = Junction(GroupContactCrossRef::class)
    )
    val groups: List<Group>
)
