package com.example.bpawlowski.falldetector.ui.main.contacts.recycler

import android.view.View
import bogusz.com.service.model.Contact
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.ui.base.recycler.BaseRecyclerViewAdapter
import com.example.bpawlowski.falldetector.ui.base.recycler.BaseViewHolder
import com.example.bpawlowski.falldetector.ui.base.recycler.ItemTouchHelperAdapter
import com.example.bpawlowski.falldetector.ui.main.call.CallContactViewHolder
import com.example.bpawlowski.falldetector.ui.main.sms.MessageContactViewHolder

class ContactViewAdapter(
    private val onDismissListener: OnContactTouchedListener? = null,
    private val onClickListener: OnContactTouchedListener? = null,
    private val onSelectListener: OnClickedListener? = null,
    private val viewType: Int = R.layout.contact_item
) : BaseRecyclerViewAdapter<Contact, BaseViewHolder<*, Contact>>(), ItemTouchHelperAdapter {

    override fun getViewType(position: Int): Int = viewType

    override fun createHolder(inflatedView: View, viewType: Int): BaseViewHolder<*, Contact> {
        return when (viewType) {
            R.layout.contact_item -> ContactViewHolder(inflatedView, onSelectListener)
            R.layout.contact_item_call -> CallContactViewHolder(inflatedView, onClickListener)
            R.layout.contact_item_sms -> MessageContactViewHolder(inflatedView, onClickListener)
            else -> CallContactViewHolder(inflatedView, onClickListener)
        }
    }

    override fun onItemDismiss(position: Int) {
        onDismissListener?.invoke(items[position])
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun areItemsSame(oldItem: Contact, newItem: Contact) =
        oldItem.id == newItem.id

    override fun areContentsSame(oldItem: Contact, newItem: Contact) =
        oldItem.email == newItem.email &&
                oldItem.mobile == newItem.mobile &&
                oldItem.name == newItem.name &&
                oldItem.priority == newItem.priority
}

typealias OnContactTouchedListener = (Contact) -> Unit
typealias OnClickedListener = (Contact, Int) -> Unit