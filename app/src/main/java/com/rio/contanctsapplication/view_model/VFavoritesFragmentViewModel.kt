package com.rio.contanctsapplication.view_model

import androidx.lifecycle.ViewModel
import com.rio.contanctsapplication.model.database.ContactsDatabase
import com.rio.contanctsapplication.model.database.entities.Contact
import com.rio.contanctsapplication.model.database.entities.Favorite
import com.rio.contanctsapplication.model.repository.ContactRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class VFavoritesFragmentViewModel(database:ContactsDatabase): ViewModel() {
    private val repository = ContactRepository(database)
    val favoriteContacts = repository.fetchFavorites()
    private val job = Job()
    private val uiCoroutineScope = CoroutineScope(job+Dispatchers.IO)

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
}