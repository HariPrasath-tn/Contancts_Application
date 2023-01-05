package com.rio.contanctsapplication.view_model

import androidx.lifecycle.ViewModel
import com.rio.contanctsapplication.model.database.ContactsDatabase
import com.rio.contanctsapplication.model.database.entities.Contact
import com.rio.contanctsapplication.model.database.entities.Favorite
import com.rio.contanctsapplication.model.repository.ContactRepository
import com.rio.contanctsapplication.view.home_Activity_fragments.item_selector_fragment.ItemSelectorFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class VItemSelectorViewModel(database: ContactsDatabase, type:Int):ViewModel() {
    private val repository = ContactRepository(database)
    val itemList = when(type){
        ItemSelectorFragment.REQUEST_TO_ADD_CONTACTS_TO_GROUP-> repository.fetchNonFavorites()
        ItemSelectorFragment.REQUEST_TO_ADD_GROUP -> repository.fetchGroups()
        else ->  repository.fetchNonFavorites()
    }
    private val job = Job()
    private val uiCoroutineScope = CoroutineScope(job+ Dispatchers.IO)

    /**
     * [insertContactIntoFavorites] method insert the given contact into favorites list in the database
     * @param contactNames ot type list of String representing the contacts name
     */
    fun insertContactIntoFavorites(contacts:List<Contact>){
        uiCoroutineScope.launch {
            for(contact in contacts) {
                repository.insertFavourite(Favorite(contact.contactName))
                val newRecord = contact.copy(favourite = 1)
                repository.updateContact(contact, newRecord)
            }
        }
    }
}