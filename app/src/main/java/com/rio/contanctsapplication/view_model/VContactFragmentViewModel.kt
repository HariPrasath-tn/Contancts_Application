package com.rio.contanctsapplication.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rio.contanctsapplication.model.database.ContactsDatabase
import com.rio.contanctsapplication.model.database.entities.Contact
import com.rio.contanctsapplication.model.repository.ContactRepository
import kotlinx.coroutines.launch

class VContactFragmentViewModel(database: ContactsDatabase):ViewModel() {
    val repository = ContactRepository(database)
    val contacts = repository.fetchContacts()
    val filteredContacts = MutableLiveData<List<Contact>>(listOf())

    fun fetchContactWith(nameSubString: String){
        viewModelScope.launch {
            filteredContacts.postValue(repository.fetchContactsWithNameContaining(nameSubString))
        }
    }
}