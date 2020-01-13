package com.example.bpawlowski.falldetector.screens.main.contacts

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.bpawlowski.domain.model.Contact
import com.bpawlowski.domain.model.ContactPriority
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.util.loadContactImage
import com.example.bpawlowski.falldetector.util.setVisible
import kotlinx.android.synthetic.main.contact_item.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ContactView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.contact_item, this)
    }

    @ModelProp
    fun setContact(contact: Contact) {
        if (contact.photoPath != null) {
            loadContactImage(context, Uri.parse(contact.photoPath), img_contact)
        } else {
            img_contact.setImageDrawable(context.getDrawable(R.drawable.icon_contact))
        }

        txtContactName.text = contact.name
        txtNumber.text = contact.mobile.toString()
        txtEmail.text = contact.email
        txtPriority.text = when (contact.priority) {
            ContactPriority.PRIORITY_NORMAL -> ""
            ContactPriority.PRIORITY_ICE -> "ICE"
        }
        img_email_icon.setVisible(contact.email?.isNotBlank() == true)
    }

    @CallbackProp
    fun setOnItemClick(onClickListener: OnClickListener?) {
        setOnClickListener(onClickListener)
    }

    @CallbackProp
    fun setOnCallClicked(onClickListener: OnClickListener?) {
        btnCall.setOnClickListener(onClickListener)
    }

    @CallbackProp
    fun setOnSmsClicked(onClickListener: OnClickListener?) {
        btnSms.setOnClickListener(onClickListener)
    }
}
