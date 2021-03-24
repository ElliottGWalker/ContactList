package com.addressbook.contactslist.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.addressbook.contactslist.ContextAwareApplication
import com.addressbook.contactslist.models.ContactModel

@Database(
    entities = [
        ContactModel::class
               ], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactsModelDao(): ContactsModelDao

    suspend fun deleteAll() {
        contactsModelDao().deleteAll()
    }

    companion object {
        val database: AppDatabase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            Room.databaseBuilder(
                ContextAwareApplication.applicationContext(),
                AppDatabase::class.java, "database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
