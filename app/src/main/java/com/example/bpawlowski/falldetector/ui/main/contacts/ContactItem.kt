package com.example.bpawlowski.falldetector.ui.main.contacts

import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.ContactItemBinding
import com.example.bpawlowski.falldetector.ui.base.recycler.Item
import com.example.bpawlowski.falldetector.ui.base.recycler.SwipeableItem
import com.example.bpawlowski.falldetector.util.loadContactImage

typealias OnContactTouchedListener = (Contact) -> Unit

class ContactItem(
	data: Contact,
	private val onDismissListener: OnContactTouchedListener? = null,
	private val onSelectListener: OnContactTouchedListener? = null,
	private val onCallClickListener: OnContactTouchedListener? = null,
	private val onSmsClickListener: OnContactTouchedListener? = null
) : Item<Contact, ContactItemBinding>(data), SwipeableItem {

	private lateinit var itemBackgroundDrawable: Drawable

	override val layoutResId = R.layout.contact_item

	override fun onBind(viewBinding: ContactItemBinding) = with(viewBinding) {
		viewBinding.contact = data

		data.photoPath?.let {
			val radius = itemView.resources.getDimensionPixelSize(R.dimen.corner_radius)

			loadContactImage(itemView.context, Uri.parse(it), imgContact, radius)
		}

		txtName.text = data.name
		txtNumber.text = data.mobile.toString()
		txtEmail.text = data.email
		txtPriority.text = when (data.priority) {
			UserPriority.PRIORITY_NORMAL -> ""
			UserPriority.PRIORITY_ICE -> "ICE"
		}
		container.setOnClickListener { onSelectListener?.invoke(data) }
		btnCall.setOnClickListener { onCallClickListener?.invoke(data) }
		btnSms.setOnClickListener { onSmsClickListener?.invoke(data) }
	}

	override val swipeDirs = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

	override fun onSwipeStarted() {
		itemBackgroundDrawable = itemView.background
		itemView.background = ContextCompat.getDrawable(itemView.context, R.drawable.background_rounded_small)
	}

	override fun onSwipeEnded() {
		itemView.background = itemBackgroundDrawable
	}

	override fun onDismissed() {
		onDismissListener?.invoke(data)
	}

	override fun isSameAs(other: Item<*, *>): Boolean {
		val otherData = other.data as? Contact ?: return false
		return data.id == otherData.id
	}

	override fun hasSameContentAs(other: Item<*, *>): Boolean {
		val otherData = other.data as? Contact ?: return false
		return data.priority == otherData.priority &&
				data.email == otherData.email &&
				data.mobile == otherData.mobile &&
				data.photoPath == otherData.photoPath &&
				data.name == otherData.name
	}
}