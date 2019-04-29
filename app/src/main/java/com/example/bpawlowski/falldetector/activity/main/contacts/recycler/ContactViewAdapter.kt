package com.example.bpawlowski.falldetector.activity.main.contacts.recycler

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import bogusz.com.service.model.Contact
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.activity.base.recycler.BaseRecyclerViewAdapter
import com.example.bpawlowski.falldetector.activity.base.recycler.BaseViewHolder
import com.example.bpawlowski.falldetector.activity.base.recycler.ItemTouchHelperAdapter
import com.example.bpawlowski.falldetector.activity.main.call.CallContactViewHolder
import com.example.bpawlowski.falldetector.activity.main.sms.MessageContactViewHolder
import java.util.*

class ContactViewAdapter(
    private val context: Context?,
    private val onDismissListener: OnContactTouchedListener? = null,
    private val onClickListener: OnContactTouchedListener? = null,
    private val viewType: Int = R.layout.contact_item
) : BaseRecyclerViewAdapter<Contact, BaseViewHolder<*, Contact>>(), ItemTouchHelperAdapter {

    override fun getViewType(position: Int): Int = viewType

    override fun createHolder(inflatedView: View, viewType: Int): BaseViewHolder<*, Contact> {
        return when (viewType) {
            R.layout.contact_item -> ContactViewHolder(inflatedView, context)
            R.layout.contact_item_call -> CallContactViewHolder(inflatedView, onClickListener)
            R.layout.contact_item_sms -> MessageContactViewHolder(inflatedView, onClickListener)
            else -> CallContactViewHolder(inflatedView, onClickListener)
        }
    }

    override fun getDiffCallback(value: MutableList<Contact>) = ContactDiffCallback(items, value)

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(items, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        onDismissListener?.invoke(items[position])
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}

class ContactDiffCallback(private val items: List<Contact>, private val value: List<Contact>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        items[oldItemPosition] == value[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newContact = value[newItemPosition]
        val oldContact = value[oldItemPosition]
        return newContact.email == oldContact.email &&
                newContact.mobile == oldContact.mobile &&
                newContact.name == oldContact.name &&
                newContact.priority == newContact.priority
    }

    override fun getOldListSize() = items.size

    override fun getNewListSize() = value.size
}

typealias OnContactTouchedListener = (Contact) -> Unit