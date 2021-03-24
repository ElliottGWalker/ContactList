package com.addressbook.contactslist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.addressbook.contactslist.R
import com.addressbook.contactslist.listen
import com.addressbook.contactslist.models.ContactModel

class ContactsAdapter(private val contactClicked: (ContactModel) -> Unit): ListAdapter<ContactModel, ContactsAdapter.ViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.cell_contact, parent, false)
        return ViewHolder(view).listen { position, _ -> contactClicked.invoke(getItem(position)) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: ContactModel = getItem(position)
        holder.bind(data)
    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        private val nameTextView = v.findViewById<TextView>(R.id.nameTextView)

        fun bind(data: ContactModel) {
            nameTextView.text = data.getFullName()
        }
    }

    object DIFF_CALLBACK : DiffUtil.ItemCallback<ContactModel>() {
        override fun areItemsTheSame(oldItem: ContactModel, newItem: ContactModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ContactModel, newItem: ContactModel): Boolean {
            return oldItem.id == newItem.id
        }
    }

}
