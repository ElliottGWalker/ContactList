package com.addressbook.contactslist.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.addressbook.contactslist.database.AppDatabase
import com.addressbook.contactslist.models.ContactModel

class ContactViewModel: ViewModel() {

    var contacts = AppDatabase.database.contactsModelDao().getContactsAsync()

    var contactClicked: MutableLiveData<ContactModel> = MutableLiveData()
}