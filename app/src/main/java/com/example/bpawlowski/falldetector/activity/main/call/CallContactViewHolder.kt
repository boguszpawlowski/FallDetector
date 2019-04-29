package com.example.bpawlowski.falldetector.activity.main.call

import android.view.View
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import com.example.bpawlowski.falldetector.BR
import com.example.bpawlowski.falldetector.activity.base.recycler.BaseViewHolder
import com.example.bpawlowski.falldetector.activity.main.contacts.recycler.OnContactTouchedListener
import com.example.bpawlowski.falldetector.databinding.ContactItemCallBinding

class CallContactViewHolder(
    view: View,
    private val onContactClickedListener: OnContactTouchedListener?
) : BaseViewHolder<ContactItemCallBinding, Contact>(view) {
    override fun bindingId(): Int = BR.contact

    override fun onBind(data: Contact) {
        binding.txtName.text = data.name
        binding.txtNumber.text = data.mobile.toString()
        binding.txtEmail.text = data.email
        binding.txtPriority.text = when (data.priority) {
            UserPriority.PRIORITY_NORMAL -> ""
            UserPriority.PRIORITY_ICE -> "ICE"
        }

        binding.container.setOnClickListener { onContactClickedListener?.invoke(data) }
    }
}