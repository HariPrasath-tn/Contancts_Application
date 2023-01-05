package com.rio.contanctsapplication.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rio.contanctsapplication.model.database.ContactsDatabase
import kotlinx.coroutines.CoroutineExceptionHandler

class FContactInsertionFragmentViewModelFactory(
    private val database: ContactsDatabase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ContactInsertionFragmentViewModel::class.java)) {
            return ContactInsertionFragmentViewModel(database) as T
        }
        throw IllegalArgumentException("Illegal Argument")
    }
}