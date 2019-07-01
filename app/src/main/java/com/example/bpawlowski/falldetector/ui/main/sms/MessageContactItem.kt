package com.example.bpawlowski.falldetector.ui.main.sms

import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.ContactItemSmsBinding
import com.example.bpawlowski.falldetector.ui.base.recycler.Item
import com.example.bpawlowski.falldetector.ui.main.contacts.OnContactTouchedListener

class MessageContactItem(
    data: Contact,
    private val onSelectListener: OnContactTouchedListener? = null
) : Item<Contact, ContactItemSmsBinding>(data) {

    override val layoutResId = R.layout.contact_item_sms

    override fun onBind() = with(binding) {
        binding.contact = data
        txtName.text = data.name
        txtNumber.text = data.mobile.toString()
        txtEmail.text = data.email
        txtPriority.text = when (data.priority) {
            UserPriority.PRIORITY_NORMAL -> ""
            UserPriority.PRIORITY_ICE -> "ICE"
        }
        container.setOnClickListener { onSelectListener?.invoke(data) }
    }
}