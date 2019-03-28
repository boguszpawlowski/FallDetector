package com.example.bpawlowski.falldetector.presentation.activity.main.contacts.recycler

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.example.bpawlowski.falldetector.BR
import com.example.bpawlowski.falldetector.databinding.ContactItemBinding
import com.example.bpawlowski.falldetector.presentation.activity.base.recycler.AbstractViewHolder
import com.example.bpawlowski.falldetector.presentation.activity.base.recycler.ItemTouchHelperViewHolder
import com.example.bpawlowski.falldetector.service.model.Contact
import com.example.bpawlowski.falldetector.service.model.UserPriority

class ContactViewHolder(
    view: View,
    private val context: Context?
) : AbstractViewHolder<ContactItemBinding, Contact>(view), ItemTouchHelperViewHolder {
    override fun bindingId(): Int = BR.contact

    override fun onBind(data: Contact) {
        binding.txtName.text = data.name
        binding.txtNumber.text = data.mobile.toString()
        binding.txtEmail.text = data.email
        binding.txtPriority.text = when(data.priority){
            UserPriority.PRIORITY_NORMAL -> ""
            UserPriority.PRIORITY_ICE -> "ICE"
        }

        binding.container.setOnClickListener { Toast.makeText(context, "On click", Toast.LENGTH_LONG).show() }
    }

    override fun onItemSelected() = itemView.setBackgroundColor(Color.LTGRAY)

    override fun onItemClear() = itemView.setBackgroundColor(0)
}

//TODO generic binding