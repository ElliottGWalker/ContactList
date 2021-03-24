package com.addressbook.contactslist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.addressbook.contactslist.models.ContactModel

@Dao
interface ContactsModelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: ContactModel): Long

    @Query("SELECT * FROM contacts ORDER BY last_name ASC")
    suspend fun getContactsNow(): List<ContactModel>

    @Query("SELECT * FROM contacts ORDER BY last_name ASC")
    fun getContactsAsync(): LiveData<List<ContactModel>>

    @Query("DELETE FROM contacts")
    suspend fun deleteAll()

    @Query("DELETE FROM contacts WHERE id = :id")
    suspend fun delete(id: Int)

}