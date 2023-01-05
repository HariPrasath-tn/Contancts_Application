package com.rio.contanctsapplication.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rio.contanctsapplication.model.database.ContactsDatabase

class FContactFragmentViewModelFactory(private val database:ContactsDatabase): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(VContactFragmentViewModel::class.java)){
            return VContactFragmentViewModel(database) as T
        }
        throw IllegalArgumentException("Illegal Arguments Provided")
    }
}