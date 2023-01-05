package com.rio.contanctsapplication.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rio.contanctsapplication.model.database.ContactsDatabase

class FHomeFragmentViewModelFactory(private val database: ContactsDatabase):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(VHomeFragmentViewModel::class.java)){
            return VHomeFragmentViewModel(database) as T
        }
        throw IllegalArgumentException("FHomeFragmentViewModelFactory-Illegal Argument")
    }
}