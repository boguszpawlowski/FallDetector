package com.example.bpawlowski.falldetector.activity.main.sms

import android.content.Context
import android.view.View
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import com.example.bpawlowski.falldetector.BR
import com.example.bpawlowski.falldetector.activity.base.recycler.AbstractViewHolder
import com.example.bpawlowski.falldetector.activity.main.contacts.recycler.OnContactClickedListener
import com.example.bpawlowski.falldetector.databinding.ContactItemSmsBinding

class MessageContactViewHolder(
    view: View,
    private val context: Context?,
    private var onClickListener: OnContactClickedListener?
) : AbstractViewHolder<ContactItemSmsBinding, Contact>(view) {
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