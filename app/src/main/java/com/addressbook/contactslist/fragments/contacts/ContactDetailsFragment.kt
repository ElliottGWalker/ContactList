package com.addressbook.contactslist.fragments.contacts

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.addressbook.contactslist.R
import com.addressbook.contactslist.databinding.FragmentContactDetailsBinding
import com.addressbook.contactslist.fragments.BaseFragment
import com.addressbook.contactslist.models.ContactModel
import com.addressbook.contactslist.viewmodels.ContactViewModel

class ContactDetailsFragment : BaseFragment() {
    private lateinit var mBinding: FragmentContactDetailsBinding
    private lateinit var contactsViewModel: ContactViewModel

    init {
        layoutID = R.layout.fragment_contact_details
    }

    override fun initViewModels() {
        contactsViewModel = ViewModelProvider(requireActivity()).get(ContactViewModel::class.java)
    }

    override fun initBinding() {
        mBinding = mBindingRoot as FragmentContactDetailsBinding
        mBinding.contactViewModel = contactsViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolBar()
    }

    private fun setupToolBar() {
        mBinding.toolbar.backLayout.visibility = View.VISIBLE
        mBinding.toolbar.backLayout.setOnClickListener { popBackstack() }

        contactsViewModel.contactClicked.observe(viewLifecycleOwner, { model ->
            Log.i("Debug", "model = $model")
            mBinding.toolbar.titleTextView.text = model.getFullName()
        })
    }

    override fun initObservers() {

    }

    override fun fragmentTag(): String {
        return "ContactDetailsFragment"
    }

}