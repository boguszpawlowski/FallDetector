package com.example.bpawlowski.falldetector.presentation.activity.main.call

import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.bpawlowski.falldetector.BR
import com.example.bpawlowski.falldetector.databinding.ContactItemCallBinding
import com.example.bpawlowski.falldetector.presentation.activity.base.recycler.AbstractViewHolder
import com.example.bpawlowski.falldetector.service.model.Contact
import com.example.bpawlowski.falldetector.service.model.UserPriority

class CallContactViewHolder(
    view: View,
    private val context: Context?
) : AbstractViewHolder<ContactItemCallBinding, Contact>(view) {
    override fun bindingId(): Int = BR.contact

    override fun onBind(data: Contact) {
        binding.txtName.text = data.name
        binding.txtNumber.text = data.mobile.toString()
        binding.txtEmail.text = data.email
        binding.txtPriority.text = when (data.priority) {
            UserPriority.PRIORITY_NORMAL -> ""
            UserPriority.PRIORITY_ICE -> "ICE"
        }

        binding.container.setOnClickListener { Toast.makeText(context, "On click", Toast.LENGTH_LONG).show() }
    }
}