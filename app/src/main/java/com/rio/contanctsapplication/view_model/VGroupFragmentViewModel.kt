package com.rio.contanctsapplication.view_model

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rio.contanctsapplication.R
import com.rio.contanctsapplication.model.database.ContactsDatabase
import com.rio.contanctsapplication.model.database.entities.Contact
import com.rio.contanctsapplication.model.database.entities.Group
import com.rio.contanctsapplication.model.repository.ContactRepository
import com.rio.contanctsapplication.view.HomeActivity
import com.rio.contanctsapplication.view.makeToastWith
import com.rio.contanctsapplication.view.showInfoBox
import kotlinx.coroutines.*

class VGroupFragmentViewModel(database: ContactsDatabase): ViewModel() {
    private val repository = ContactRepository(database)

    // groupsFragment elements
    val groupsAvail = repository.fetchGroups()
    val groupsContacts = MutableLiveData<List<List<Contact>>>()

    // newGroupFragment elements
    val newGroupName = MutableLiveData<String>()
    val newGroupDescription = MutableLiveData<String>()
    val newGroupDp = MutableLiveData<String>()

    private val job = Job()
    private val uiCoroutineScope = CoroutineScope(job+ Dispatchers.IO)

    /**
     * [triggerNewGroupCreation] method validate and create the new Group
     * throws Exception if the group already exists
     */
    fun triggerNewGroupCreation(context:Context){
        Log.i("Master", newGroupName.value.toString())
        if(newGroupName.value?.length!! < 3){
            makeToastWith(context, "Group name too small")
            return
        }
        uiCoroutineScope.launch {
            try {
                repository.insertGroup(Group(
                    newGroupName.value!!.toString(),
                    newGroupDescription.value!!.toString()
                ))
                withContext(Dispatchers.Main) {
                    makeToastWith(context, "Group SuccessFully Added")
                    (context as HomeActivity).onBackPressed()
                }

            }catch (e:SQLiteConstraintException){
                withContext(Dispatchers.Main) {
                    showInfoBox(
                        context,
                        "Group Already Exist",
                        "${newGroupName.value} already allocated for another Group. Please try " +
                                "anther",
                        R.drawable.app_icon
                    )
                }
            }
        }
    }
}