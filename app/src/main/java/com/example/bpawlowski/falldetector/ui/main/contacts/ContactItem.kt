package com.example.bpawlowski.falldetector.ui.main.contacts

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.ItemTouchHelper
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.ContactItemBinding
import com.example.bpawlowski.falldetector.ui.base.recycler.Item

typealias OnContactTouchedListener = (Contact) -> Unit

class ContactItem(
    data: Contact,
    private val onDismissListener: ((Contact) -> Unit)? = null,
    private val onSelectListener: OnContactTouchedListener? = null
) : Item<Contact, ContactItemBinding>(data) {

    private lateinit var itemBackgroundDrawable: Drawable

    override val layoutResId = R.layout.contact_item

    override fun onBind() = with(binding) {
        txtName.text = data.name
        txtNumber.text = data.mobile.toString()
        txtEmail.text = data.email
        txtPriority.text = when (data.priority) {
            UserPriority.PRIORITY_NORMAL -> ""
            UserPriority.PRIORITY_ICE -> "ICE"
        }
        container.setOnClickListener { onSelectListener?.invoke(data) }
    }

    override val swipeDirs = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

    override fun onSwipeStarted() {
        itemBackgroundDrawable = itemView.background
        itemView.background = ColorDrawable(Color.WHITE)
    }

    override fun onSwipeEnded() {
        itemView.background = itemBackgroundDrawable
    }

    override fun onDismissed() {
        onDismissListener?.invoke(data)
    }

    override fun isSameAs(other: Item<*, *>): Boolean {
        val otherItem = other.data as? Contact ?: return false
        return data.id == otherItem.id
    }

    override fun hasSameContentAs(other: Item<*, *>): Boolean {
        val otherItem = other.data as? Contact ?: return false
        return data.priority == otherItem.priority &&
                data.email == otherItem.email &&
                data.mobile == otherItem.mobile &&
                data.name == otherItem.name
    }
}