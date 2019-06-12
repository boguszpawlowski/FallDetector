package com.example.bpawlowski.falldetector.ui.main.contacts.recycler

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import com.example.bpawlowski.falldetector.BR
import com.example.bpawlowski.falldetector.databinding.ContactItemBinding
import com.example.bpawlowski.falldetector.ui.base.recycler.BaseViewHolder
import com.example.bpawlowski.falldetector.ui.base.recycler.ItemTouchHelperViewHolder

class ContactViewHolder(
    view: View,
    private val onSelectListener: OnClickedListener? = null
) : BaseViewHolder<ContactItemBinding, Contact>(view), ItemTouchHelperViewHolder {

    private lateinit var itemBackgroundDrawable: Drawable

    override fun bindingId(): Int = BR.contact

    override fun onBind(data: Contact) = with(binding) {
        imgContact.transitionName = "tr_$adapterPosition"
        txtName.text = data.name
        txtNumber.text = data.mobile.toString()
        txtEmail.text = data.email
        txtPriority.text = when (data.priority) {
            UserPriority.PRIORITY_NORMAL -> ""
            UserPriority.PRIORITY_ICE -> "ICE"
        }

        container.setOnClickListener { onSelectListener?.invoke(data, adapterPosition) }
    }

    override fun onItemSelected() {
        itemBackgroundDrawable = itemView.background
        itemView.background = ColorDrawable(Color.WHITE)
    }

    override fun onItemClear() {
        itemView.background = itemBackgroundDrawable
    }
}