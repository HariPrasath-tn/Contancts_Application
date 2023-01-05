package com.rio.contanctsapplication.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rio.contanctsapplication.model.database.ContactsDatabase

class FItemSelectorViewModelFactory(private val database: ContactsDatabase, private val type: Int):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(VItemSelectorViewModel::class.java)){
            return VItemSelectorViewModel(database, type) as T
        }
        throw IllegalArgumentException("Illegal Argument")
    }
}