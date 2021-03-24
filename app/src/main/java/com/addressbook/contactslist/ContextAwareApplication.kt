package com.addressbook.contactslist

import android.app.Application
import android.content.Context

class ContextAwareApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: ContextAwareApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}