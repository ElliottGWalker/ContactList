package com.addressbook.contactslist.fragments.contacts

interface AddContactCallback {
    fun formValid()
    fun firstNameNotEntered()
    fun lastNameNotEntered()
    fun emailNotEntered()
    fun emailNotValid()
    fun phoneNotEntered()
    fun phoneNotValid()
    fun addressNotEntered()
}