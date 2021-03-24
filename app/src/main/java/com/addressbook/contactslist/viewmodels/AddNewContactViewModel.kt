package com.addressbook.contactslist.viewmodels

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.addressbook.contactslist.fragments.contacts.AddContactCallback

class AddNewContactViewModel: ViewModel() {

    var firstNameObserver: MutableLiveData<String> = MutableLiveData()
    var lastNameObserver: MutableLiveData<String> = MutableLiveData()
    var emailObserver: MutableLiveData<String> = MutableLiveData()
    var phoneObserver: MutableLiveData<String> = MutableLiveData()
    var addressObserver: MutableLiveData<String> = MutableLiveData()

    var addContactCallback: AddContactCallback? = null

    fun addNewContactFormValid() {
        if(firstNameObserver.value.isNullOrEmpty()){
            addContactCallback?.firstNameNotEntered()
            return
        }
        if(lastNameObserver.value.isNullOrEmpty()){
            addContactCallback?.lastNameNotEntered()
            return
        }
        if(emailObserver.value.isNullOrEmpty()){
            addContactCallback?.emailNotEntered()
            return
        }
        if(!emailIsValid()){
            addContactCallback?.emailNotValid()
            return
        }
        if(phoneObserver.value.isNullOrEmpty()){
            addContactCallback?.phoneNotEntered()
            return
        }
        if(!phoneIsValid()){
            addContactCallback?.phoneNotValid()
            return
        }
        if(addressObserver.value.isNullOrEmpty()){
            addContactCallback?.addressNotEntered()
            return
        }

        addContactCallback?.formValid()
    }

    private fun emailIsValid(): Boolean {
        return when {
            !Patterns.EMAIL_ADDRESS.matcher(emailObserver.value ?: "").matches() -> {
                false
            }
            else -> true
        }
    }

    private fun phoneIsValid(): Boolean {
        return when {
            !Patterns.PHONE.matcher(phoneObserver.value ?: "").matches() -> {
                return false
            }
            else -> true
        }

    }
}