package com.rio.contanctsapplication.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rio.contanctsapplication.model.database.ContactsDatabase

class FGroupFragmentViewModelFactory(private val database: ContactsDatabase):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(VGroupFragmentViewModel::class.java)){
            return VGroupFragmentViewModel(database) as T
        }
        throw IllegalArgumentException("(FGroupFragmentViewModelFactory)-Illegal Argument Exception")
    }
}