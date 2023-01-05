package com.rio.contanctsapplication.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rio.contanctsapplication.model.database.ContactsDatabase

class FFavoritesFragmentViewModelFactory(private val database: ContactsDatabase):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(VFavoritesFragmentViewModel::class.java)){
            return VFavoritesFragmentViewModel(database) as T
        }
        throw IllegalArgumentException("(FFavoritesFragmentViewModelFactory) Illegal Argument Exception")
    }
}