package com.example.bpawlowski.falldetector.ui.main.contacts

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.ContactItemBinding
import com.example.bpawlowski.falldetector.ui.base.recycler.Item

typealias OnContactTouchedListener = (Contact) -> Unit

class ContactItem(
    data: Contact,
    onDismissListener: ((Contact) -> Unit)? = null,
    private val onSelectListener: OnContactTouchedListener? = null
) : Item<Contact, ContactItemBinding>(data, onDismissListener) {

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

    override fun onSwipeStarted() {
        itemBackgroundDrawable = itemView.background
        itemView.background = ColorDrawable(Color.WHITE)
    }

    override fun onSwipeEnded() {
        itemView.background = itemBackgroundDrawable
    }
}