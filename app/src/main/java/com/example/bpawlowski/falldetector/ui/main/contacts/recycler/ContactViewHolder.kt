package com.example.bpawlowski.falldetector.ui.main.contacts.recycler

import android.graphics.Color
import android.view.View
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import com.example.bpawlowski.falldetector.BR
import com.example.bpawlowski.falldetector.databinding.ContactItemBinding
import com.example.bpawlowski.falldetector.ui.base.recycler.BaseViewHolder
import com.example.bpawlowski.falldetector.ui.base.recycler.ItemTouchHelperViewHolder

class ContactViewHolder(
    view: View,
    private val onSelectListener: OnContactTouchedListener? = null
) : BaseViewHolder<ContactItemBinding, Contact>(view), ItemTouchHelperViewHolder {
    override fun bindingId(): Int = BR.contact

    override fun onBind(data: Contact) {
        binding.txtName.text = data.name
        binding.txtNumber.text = data.mobile.toString()
        binding.txtEmail.text = data.email
        binding.txtPriority.text = when (data.priority) {
            UserPriority.PRIORITY_NORMAL -> ""
            UserPriority.PRIORITY_ICE -> "ICE"
        }

        binding.container.setOnLongClickListener { onSelectListener?.invoke(data); true }
    }

    override fun onItemSelected() = itemView.setBackgroundColor(Color.LTGRAY)

    override fun onItemClear() = itemView.setBackgroundColor(0)
}