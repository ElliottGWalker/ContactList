package com.addressbook.contactslist

import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition, itemViewType)
    }
    return this
}