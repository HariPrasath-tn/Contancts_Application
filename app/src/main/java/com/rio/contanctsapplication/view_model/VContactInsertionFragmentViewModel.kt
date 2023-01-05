package com.rio.contanctsapplication.view_model

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rio.contanctsapplication.R
import com.rio.contanctsapplication.model.database.ContactsDatabase
import com.rio.contanctsapplication.model.database.entities.Contact
import com.rio.contanctsapplication.model.repository.ContactRepository
import com.rio.contanctsapplication.view.HomeActivity
import com.rio.contanctsapplication.view.makeToastWith
import com.rio.contanctsapplication.view.showInfoBox
import kotlinx.coroutines.*

class ContactInsertionFragmentViewModel(database: ContactsDatabase) : ViewModel(){
    private val repository = ContactRepository(database)
    private val job = Job()
    private var uiCoroutineScope = CoroutineScope(job+Dispatchers.IO)
    var contactData = MutableLiveData<HashMap<String, String>>(HashMap())

    fun insertData(contact:Contact, context: Context){
        uiCoroutineScope.launch {
            try {
                repository.insertContact(
                    contact
                )
                withContext(Dispatchers.Main) {
                    makeToastWith(context, "Contact Saved SuccessFully")
                    val tempActivity = (context as HomeActivity)
                    tempActivity.onBackPressed()
                }
            }catch (e: SQLiteConstraintException){
                withContext(Dispatchers.Main) {
                    showInfoBox(
                        context,
                        "Contact Already Exists",
                        "Saving contact with same name is not allowed since it may leads " +
                                "to confusion among contacts.\nTry another name",
                        iconId = R.drawable.app_icon
                    )
                }
            }
        }
    }
}
fun MutableLiveData<HashMap<String, String>>.update(){
    value = value
}