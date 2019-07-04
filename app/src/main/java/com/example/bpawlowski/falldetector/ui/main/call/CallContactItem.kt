package com.example.bpawlowski.falldetector.ui.main.call

import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.ContactItemCallBinding
import com.example.bpawlowski.falldetector.ui.base.recycler.Item
import com.example.bpawlowski.falldetector.ui.main.contacts.OnContactTouchedListener

class CallContactItem(
    data: Contact,
    private val onSelectListener: OnContactTouchedListener? = null
) : Item<Contact, ContactItemCallBinding>(data) {

    override val layoutResId = R.layout.contact_item_call

    override fun onBind(viewBinding: ContactItemCallBinding) = with(viewBinding) {
        viewBinding.contact = data
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