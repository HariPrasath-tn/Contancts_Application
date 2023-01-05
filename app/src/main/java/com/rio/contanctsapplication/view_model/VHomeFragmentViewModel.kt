package com.rio.contanctsapplication.view_model

import androidx.lifecycle.ViewModel
import com.rio.contanctsapplication.model.database.ContactsDatabase
import com.rio.contanctsapplication.model.database.entities.Contact
import com.rio.contanctsapplication.model.database.entities.Favorite
import com.rio.contanctsapplication.model.database.entities.Group
import com.rio.contanctsapplication.model.repository.ContactRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class VHomeFragmentViewModel(database: ContactsDatabase):ViewModel() {
    private val repository = ContactRepository(database)
    val numberOfContacts = repository.fetchContactsCount()
    val numberOfFavoriteContacts = repository.fetchFavoriteContactsCount()
    val numberOfGroups = repository.fetchGroupsCount()
    val favoriteContacts = repository.fetchFavorites()
    val groupsAvail = repository.fetchGroups()

    private val job = Job()
    private val uiCoroutineScope = CoroutineScope(job+ Dispatchers.IO)

    /**
     * [removeFromFavorite] method removes the given contact name from the favorite list
     * @param contactName of type String representing a contact
     */
    fun removeFromFavorite(oldRecord:Contact){
        uiCoroutineScope.launch {
            repository.deleteContact(Favorite(oldRecord.contactName))
            val newRecord = oldRecord.copy(favourite = 0)
            repository.updateContact(oldRecord, newRecord)
        }
    }

    /**
     * [deleteGroup] method deletes the given group from the database
     * @param group of type Group representing a group
     */
    fun deleteGroup(group: Group){
        uiCoroutineScope.launch {
            repository.deleteGroup(group)
        }
    }
}