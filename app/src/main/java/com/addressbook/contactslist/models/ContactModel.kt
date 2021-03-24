package com.addressbook.contactslist.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "contacts")
data class ContactModel (
    @PrimaryKey @ColumnInfo(name = "id") var id: Int? = null,
    @ColumnInfo(name = "first_name") var firstName: String? = null,
    @ColumnInfo(name = "last_name") var lastName: String? = null,
    @ColumnInfo(name = "email") var email: String? = null,
    @ColumnInfo(name = "phone") var phone: String? = null,
    @ColumnInfo(name = "address") var address: String? = null
) {
    fun getFullName(): String {
        var fullName = ""
        firstName?.let {
            fullName = it
        }

        lastName?.let {
            fullName = if(fullName.isNullOrEmpty()) it else "$fullName $it"
        }

        return fullName
    }
}
