package com.example.bpawlowski.falldetector.ui.main.sms

import android.view.View
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import com.example.bpawlowski.falldetector.BR
import com.example.bpawlowski.falldetector.ui.base.recycler.BaseViewHolder
import com.example.bpawlowski.falldetector.ui.main.contacts.recycler.OnContactTouchedListener
import com.example.bpawlowski.falldetector.databinding.ContactItemSmsBinding

class MessageContactViewHolder(
    view: View,
    private var onClickListener: OnContactTouchedListener?
) : BaseViewHolder<ContactItemSmsBinding, Contact>(view) {
    override fun bindingId(): Int = BR.contact

    override fun onBind(data: Contact) {
        binding.txtName.text = data.name
        binding.txtNumber.text = data.mobile.toString()
        binding.txtEmail.text = data.email
        binding.txtPriority.text = when (data.priority) {
            UserPriority.PRIORITY_NORMAL -> ""
            UserPriority.PRIORITY_ICE -> "ICE"
        }

        binding.container.setOnClickListener { onClickListener?.invoke(data) }
    }
}