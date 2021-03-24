package com.addressbook.contactslist.fragments.contacts

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.addressbook.contactslist.R
import com.addressbook.contactslist.adapters.ContactsAdapter
import com.addressbook.contactslist.database.AppDatabase
import com.addressbook.contactslist.databinding.FragmentContactsBinding
import com.addressbook.contactslist.fragments.BaseFragment
import com.addressbook.contactslist.models.ContactModel
import com.addressbook.contactslist.viewmodels.ContactViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Predicate
import io.reactivex.rxjava3.schedulers.Schedulers

class ContactsFragment : BaseFragment() {
    private lateinit var mBinding: FragmentContactsBinding
    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var contactsViewModel: ContactViewModel

    init {
        layoutID = R.layout.fragment_contacts
    }

    override fun initViewModels() {
        contactsViewModel = ViewModelProvider(requireActivity()).get(ContactViewModel::class.java)
    }

    override fun initBinding() {
        mBinding = mBindingRoot as FragmentContactsBinding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()
        setupSearchView()
    }

    private fun setupToolbar() {
        mBinding.toolbar.titleTextView.text = getString(R.string.address_book)
        mBinding.toolbar.addLayout.visibility = View.VISIBLE

        mBinding.toolbar.addLayout.setOnClickListener {
            navigateTo(R.id.action_contactsFragment_to_addContactFragment)
        }
    }

    private fun setupRecyclerView() {
        contactsAdapter = ContactsAdapter {
            contactsViewModel.contactClicked.value = it
            navigateTo(R.id.action_contactsFragment_to_contactDetailsFragment)
        }
        mBinding.contactRecyclerView.adapter = contactsAdapter

        if(contactsAdapter.currentList.size > 0){
            mBinding.noContactConstraintLayout.visibility = View.GONE
            mBinding.contactsConstraintLayout.visibility = View.VISIBLE
        }

        contactsViewModel.contacts.observe(viewLifecycleOwner, {
            contactsAdapter.submitList(it)
            if(contactsAdapter.currentList.size > 0){
                mBinding.noContactConstraintLayout.visibility = View.GONE
                mBinding.contactsConstraintLayout.visibility = View.VISIBLE
            }
        })
    }

    private fun setupSearchView() {
        mBinding.searchView.doOnTextChanged { text, start, before, count ->
            filterList(text.toString())
        }
    }

    private fun filterList(text: String?) {
        text?.let {
            val list = contactsViewModel.contacts.value
            val filteredList = list?.filter { model ->
                val fullName = model.getFullName().toLowerCase()
                fullName.contains(it)
            }
            contactsAdapter.submitList(filteredList)
        } ?: run {
            contactsAdapter.submitList(contactsViewModel.contacts.value)
        }
    }

    override fun initObservers() {

    }

    override fun fragmentTag(): String {
        return "ContactsFragment"
    }

}