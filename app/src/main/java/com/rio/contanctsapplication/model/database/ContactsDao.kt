package com.rio.contanctsapplication.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.rio.contanctsapplication.model.database.entities.*

/**
 * [ContactsDao] interfaces defines the method that provides the functionalities to interact with and
 * communicate with the database
 */
@Dao
interface ContactsDao {
    // method that insert the given contact into the database
    @Insert
    suspend fun insertContact(contact: Contact):Long


    // method that inserts multiple contacts
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertContact(contact: List<Contact>)

    // method that inserts favorite contact into the database
    @Insert
    suspend fun insertFavorite(favorite: Favorite):Long

    // method that inserts groups created into the database
    @Insert
    suspend fun insertGroup(group: Group):Long

    //method that insert the relations of contact and the groups into the database
    @Insert
    suspend fun insertGroupContactReference(groupContactCrossRef: GroupContactCrossRef):Long

    // method that deletes the contacts from the database
    @Delete
    suspend fun deleteContact(contact: Contact):Int

    // method that delete favorite contact from the database record
    @Delete
    suspend fun deleteFavorite(favorite: Favorite):Int

    // method that deletes the given group form the database
    @Delete
    suspend fun deleteGroup(group: Group):Int

    // method that deletes the contact from group in the database
    @Query("DELETE FROM GroupContactCrossRef WHERE contactName=:contactName")
    suspend fun deleteContactFromGroupsReference(contactName: String):Int

    // method that deletes the contact from group in the database
    @Query("DELETE FROM GroupContactCrossRef WHERE groupName=:groupName")
    suspend fun deleteContactsInGroup(groupName: String):Int

    //method that updates the contact in the database
    @Update
    suspend fun updateContact(contact: Contact):Int

    // method that fetches the contacts from the database
    @Query("SELECT * FROM contacts")
    fun fetchContacts():LiveData<List<Contact>>

    // method that fetches the no of contacts in the database
    @Query("SELECT COUNT(contactName) FROM contacts")
    fun fetchContactsCount():LiveData<Int>

    // method that fetches the contacts that matches the given part of the number
    @Query("SELECT * FROM contacts WHERE phoneNumber LIKE '%' || :phone || '%'")
    fun fetchContacts(phone:String):LiveData<List<Contact>>

    // method that fetches the contacts that matches the given part of their name
    @Query("SELECT * FROM contacts WHERE contactName LIKE '%' || :name || '%'")
    suspend fun fetchContactsWith(name:String):List<Contact>

    // method that fetches particular contact from the database
    @Query("SELECT * FROM contacts WHERE contactName=:name")
    suspend fun fetchContactWithUserName(name:String):Contact

    // method that fetches the groups from the database
    @Query("SELECT * FROM `group`")
    fun fetchGroups(): LiveData<List<Group>>

    // method that fetches the no of groups in the database
    @Query("SELECT COUNT(groupName) FROM `group`")
    fun fetchGroupsCount():LiveData<Int>

    // method that fetches the favorite contacts from the database
    @Query("SELECT * FROM contacts WHERE contactName IN (SELECT * FROM Favorite)")
    fun fetchFavorites():LiveData<List<Contact>>

    // method that fetches all the contacts that are not favorite from the database
    @Query("SELECT * FROM contacts WHERE contactName NOT IN (SELECT * FROM Favorite)")
    fun fetchNonFavorites():LiveData<List<Contact>>

    // method that fetches the no of favorite contacts in the database
    @Query("SELECT COUNT(contactName) FROM favorite")
    fun fetchFavoriteContactsCount():LiveData<Int>

    // method that fetches the contacts in a group
    // mentioning Transaction annotation to make the call thread safe
    @Transaction
    @Query("SELECT * FROM `group` WHERE groupName=:groupName")
    fun fetchGroupContacts(groupName:String): LiveData<List<GroupWithContacts>>

    // method that fetches the groups in which the contact is available
    // mentioning Transaction annotation to make the call thread safe
    @Transaction
    @Query("SELECT * FROM contacts WHERE contactName=:name")
    fun fetchGroupsOfTheContact(name:String):LiveData<List<ContactWithGroups>>
}