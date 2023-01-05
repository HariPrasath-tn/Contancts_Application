package com.rio.contanctsapplication.model.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database
import com.rio.contanctsapplication.model.database.entities.Contact
import com.rio.contanctsapplication.model.database.entities.Favorite
import com.rio.contanctsapplication.model.database.entities.Group
import com.rio.contanctsapplication.model.database.entities.GroupContactCrossRef

/**
 * [ContactsDatabase] an abstract class that defines the functionality of the database Dao
 */

@Database(
    entities=[
        Contact::class,
        Favorite::class,
        Group::class,
        GroupContactCrossRef::class
    ],
    version=1
)
abstract class ContactsDatabase : RoomDatabase(){
    abstract val contactDao:ContactsDao

    companion object{
        // annotating the INSTANCE variable as Volatile to make it thread safe
        @Volatile
        var INSTANCE:ContactsDatabase? = null

        /**
         * [getInstanceOfDataBase] method returns the instance of the database
         * @param context of type Context representing the application context. Providing the
         * activity context will lead to data leak when a configuration change occurs
         * @return Instance of the ContactDatabase
         */
         fun getInstanceOfDataBase(context: Context):ContactsDatabase{
            // synchronizing the statements in the function in order to prevent the multi thread
            // access simultaneously
            synchronized(this){
                // using kotlin smart casting in for the control flow
                var instance = INSTANCE
                if(instance == null){
                    // creating the instance of the database class that extends the Room Database
                    instance = Room.databaseBuilder(
                        // application context
                        context.applicationContext,
                        // database class
                        ContactsDatabase::class.java,
                        // name of the database
                        "contact_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }
}
