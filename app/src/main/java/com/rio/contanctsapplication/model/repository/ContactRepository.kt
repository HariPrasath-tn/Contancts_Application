package com.rio.contanctsapplication.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.rio.contanctsapplication.model.database.ContactsDatabase
import com.rio.contanctsapplication.model.database.entities.*

class ContactRepository(private val database:ContactsDatabase) {
    /**
     * [insertContact] method insert given single contact into the database if the username is not
     * already exist in the database else throws Exception
     * @param contact of type Contact representing contact's all the data
     */
    suspend fun insertContact(contact: Contact){
        database.contactDao.insertContact(contact).toString()
    }

    /**
     * [insertContact] method inserts list of contact into the database if the username
     * already exists then it ignore that particular contact
     * @param contacts list of contacts
     */
    suspend fun insertContact(contacts: List<Contact>){
        database.contactDao.insertContact(contacts)
    }

    /**
     * [insertFavourite] method inserts favourited contacts into the database
     * @param favorite of type Favourite representing contact's all the data
     */
    suspend fun insertFavourite(favorite: Favorite){
        database.contactDao.insertFavorite(favorite)
    }

    /**
     * [insertGroup] method inserts created groups into the database
     * @param group of type Favourite representing contact's all the data
     */
    suspend fun insertGroup(group: Group){
        database.contactDao.insertGroup(group)
    }

    /**
     * [insertGroupContactRef] method inserts created groups into the database
     * @param ref of type Favourite representing contact's all the data
     */
    suspend fun insertGroupContactRef(ref: GroupContactCrossRef){
        database.contactDao.insertGroupContactReference(ref)
    }

    /**
     * [deleteContact] method delete the given contact from the database
     * @param contact of type Contact representing contact's all the data
     */
    suspend fun deleteContact(contact: Contact){
        database.contactDao.deleteContact(contact)
    }

    /**
     * [deleteFavorite] method removes the given contact from the favorite list
     * @param favorite of type favorite representing the person favorite by the admin
     */
    suspend fun deleteContact(favorite: Favorite){
        database.contactDao.deleteFavorite(favorite)
    }

    /**
     * [deleteGroup] method delete the given group from the database
     * @param group of type Group representing group o contacts
     */
    suspend fun deleteGroup(group: Group){
        database.contactDao.deleteGroup(group)
    }

    /**
     * [deleteContact] method removes the given contact from the Group
     * @param groupName of type string representing name of the group to be delete
     */
    suspend fun delete(groupName: String){
        database.contactDao.deleteContactsInGroup(groupName)
    }

    /**
     * [updateContact] method updates the given contact into the database
     * @param oldRecord of type Contact representing the contact before Update
     * @param newRecord of type Contact representing the contact after Update
     */
    suspend fun updateContact(oldRecord:Contact, newRecord: Contact){
        // removing old Contact to prevent duplicate
        database.contactDao.deleteContact(oldRecord)
        // inserting the new record
        database.contactDao.insertContact(newRecord)
    }

    /**
     * [fetchContact] method fetches the given contact from the database
     * @param contactName of type String representing a contact in the contact list
     * @return contact corresponding to the given name
     */
    suspend fun fetchContact(contactName:String):Contact{
        return database.contactDao.fetchContactWithUserName(contactName)
    }

    /**
     * [fetchContacts] method fetches all the contacts from the database
     * @return LiveData of List of contacts from the database
     */
    fun fetchContacts():LiveData<List<Contact>>{
        return database.contactDao.fetchContacts()
    }

    /**
     * [fetchContactsCount] method fetches number of contacts in the database
     * @return LiveData of integer
     */
    fun fetchContactsCount():LiveData<Int>{
        return database.contactDao.fetchContactsCount()
    }

    /**
     * [fetchGroupsCount] method fetches number of groups in the database
     * @return LiveData of integer
     */
    fun fetchGroupsCount():LiveData<Int>{
        return database.contactDao.fetchGroupsCount()
    }

    /**
     * [fetchFavoriteContactsCount] method fetches number of favorite contacts in the database
     * @return LiveData of integer
     */
    fun fetchFavoriteContactsCount():LiveData<Int>{
        return database.contactDao.fetchFavoriteContactsCount()
    }

    /**
     * [fetchContactsContaining] method fetches all the contacts that contains the given part of number
     * @param phoneNumber of type String representing part of number mobile number
     * @return LiveData of List of Contacts from the database that are containing the given number
     */
    fun fetchContactsContaining(phoneNumber:String):LiveData<List<Contact>>{
        return database.contactDao.fetchContacts(phoneNumber)
    }

    /**
     * [fetchContactsWithNameContaining] method fetches all the contacts that contains the given part of number
     * @param nameSubString of type String representing part of number mobile number
     * @return LiveData of List of Contacts from the database that are containing the given number
     */
    suspend fun fetchContactsWithNameContaining(nameSubString:String):List<Contact>{
        return database.contactDao.fetchContactsWith(nameSubString)
    }

    /**
     * [fetchFavorites] method fetches all the favorite contacts in the database
     * @return LiveData of Favorite from the database
     */
    fun fetchFavorites():LiveData<List<Contact>>{
        return database.contactDao.fetchFavorites()
    }

    /**
     * [fetchNonFavorites] method fetches all other contacts than favorite ont in the database
     * @return LiveData of unFavorite contacts from the database
     */
    fun fetchNonFavorites():LiveData<List<Contact>>{
        return database.contactDao.fetchNonFavorites()
    }

    /**
     * [fetchGroups] method fetches all the groups from the database
     * @return LiveData of List of Groups in the database
     */
    fun fetchGroups():LiveData<List<Group>>{
        return database.contactDao.fetchGroups()
    }

    /**
     * [fetchGroupContacts] method fetches all the contacts in the given group
     * @param groupName of type String representing the name of the group for which contacts needed
     * @return LiveData of List of contact in the given Group
     */
    fun fetchGroupContacts(groupName:String):LiveData<List<GroupWithContacts>>{
        return database.contactDao.fetchGroupContacts(groupName)
    }

    /**
     * [fetchGroupsContainingContact] method fetches all the groups containing the given number
     * @param contactName of type String representing part of number mobile number
     * @return LiveData of List of Groups
     */
    fun fetchGroupsContainingContact(contactName:String):LiveData<List<ContactWithGroups>>{
        return database.contactDao.fetchGroupsOfTheContact(contactName)
    }
}