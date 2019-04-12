package com.example.bpawlowski.falldetector.activity.main.contacts.recycler

import android.content.Context
import android.view.View
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.activity.base.recycler.AbstractRecyclerViewAdapter
import com.example.bpawlowski.falldetector.activity.base.recycler.AbstractViewHolder
import com.example.bpawlowski.falldetector.activity.base.recycler.ItemTouchHelperAdapter
import com.example.bpawlowski.falldetector.activity.main.call.CallContactViewHolder
import com.example.bpawlowski.falldetector.activity.main.sms.MessageContactViewHolder
import bogusz.com.service.model.Contact
import java.util.*

class ContactViewAdapter(
    private val context: Context?,
    private val onDismissListener: ((Contact) -> Unit)? = null,
    private val onClickListener: OnContactClickedListener? = null,
    private val viewType: Int = R.layout.contact_item
) : AbstractRecyclerViewAdapter<Contact, AbstractViewHolder<*,Contact>>(), ItemTouchHelperAdapter {

    override fun getViewType(position: Int): Int = viewType

    override fun createHolder(inflatedView: View, viewType: Int): AbstractViewHolder<*,Contact> {
        return when(viewType){
            R.layout.contact_item -> ContactViewHolder(inflatedView, context)
            R.layout.contact_item_call -> CallContactViewHolder(inflatedView, context, onClickListener)
            R.layout.contact_item_sms -> MessageContactViewHolder(inflatedView, context, onClickListener)
            else -> CallContactViewHolder(inflatedView, context, onClickListener)
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(data, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        onDismissListener?.invoke(data[position])
        data.removeAt(position)
        notifyItemRemoved(position)
    }
}

typealias OnContactClickedListener = (Contact) ->Unit
//TODO move to different dir, add abstract VH for contact