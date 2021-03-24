package com.addressbook.contactslist.fragments.contacts

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.addressbook.contactslist.R
import com.addressbook.contactslist.database.AppDatabase
import com.addressbook.contactslist.databinding.FragmentAddContactBinding
import com.addressbook.contactslist.fragments.BaseFragment
import com.addressbook.contactslist.models.ContactModel
import com.addressbook.contactslist.viewmodels.AddNewContactViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class AddContactFragment : BaseFragment(), AddContactCallback {
    private lateinit var mBinding: FragmentAddContactBinding
    private lateinit var addNewContactViewModel: AddNewContactViewModel

    init {
        layoutID = R.layout.fragment_add_contact
    }

    override fun initViewModels() {
        addNewContactViewModel = ViewModelProvider(this).get(AddNewContactViewModel::class.java)
        addNewContactViewModel.addContactCallback = this
    }

    override fun initBinding() {
        mBinding = mBindingRoot as FragmentAddContactBinding
        mBinding.addNewContactViewModel = addNewContactViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        mBinding.saveButton.setOnClickListener {
            addNewContactViewModel.addNewContactFormValid()
        }
    }

    private fun setupToolbar() {
        mBinding.toolbar.titleTextView.text = getString(R.string.add_new_contact)
        mBinding.toolbar.backLayout.visibility = View.VISIBLE

        mBinding.toolbar.backLayout.setOnClickListener { popBackstack() }
    }

    override fun initObservers() {

    }

    override fun fragmentTag(): String {
        return "AddContactFragment"
    }

    override fun formValid() {
        val randomNumber = Random.nextInt(10000000)
        val model = ContactModel(
            randomNumber,
            addNewContactViewModel.firstNameObserver.value,
            addNewContactViewModel.lastNameObserver.value,
            addNewContactViewModel.emailObserver.value,
            addNewContactViewModel.phoneObserver.value,
            addNewContactViewModel.addressObserver.value
        )
        CoroutineScope(Dispatchers.IO).launch { AppDatabase.database.contactsModelDao().insert(model) }
        makeSnack("New Contact Saved...", Snackbar.LENGTH_LONG)
        popBackstack()
    }

    override fun firstNameNotEntered() {
        makeSnack("Please enter first name", Snackbar.LENGTH_LONG)
    }

    override fun lastNameNotEntered() {
        makeSnack("Please enter last name", Snackbar.LENGTH_LONG)
    }

    override fun emailNotEntered() {
        makeSnack("Please enter email", Snackbar.LENGTH_LONG)
    }

    override fun emailNotValid() {
        makeSnack("Email address invalid, please check and try again", Snackbar.LENGTH_LONG)
    }

    override fun phoneNotEntered() {
        makeSnack("Please enter phone number", Snackbar.LENGTH_LONG)
    }

    override fun phoneNotValid() {
        makeSnack("Phone number invalid, please check and try again", Snackbar.LENGTH_LONG)
    }

    override fun addressNotEntered() {
        makeSnack("Please enter address", Snackbar.LENGTH_LONG)
    }


}