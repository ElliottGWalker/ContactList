package com.addressbook.contactslist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.addressbook.contactslist.fragments.BaseFragment
import com.addressbook.contactslist.R

class MainActivity : AppCompatActivity() {

    var currentActiveFragment: BaseFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        if(currentActiveFragment?.popBackstack() != true){
            super.onBackPressed()
        }
    }
}