package com.example.bpawlowski.falldetector.domain

import android.util.Patterns
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.bpawlowski.database.entity.Contact
import com.bpawlowski.database.entity.UserPriority
import com.example.bpawlowski.falldetector.util.empty

class ContactFormModel : BaseObservable() {

	private var initialData: Contact? = null

	@Bindable
	var name: String = String.empty
		set(value) {
			field = value
			notifyPropertyChanged(BR.name)
			notifyPropertyChanged(BR.valid)
			notifyPropertyChanged(BR.nameError)
			notifyPropertyChanged(BR.hasChanged)
		}

	@Bindable
	var mobile: String = String.empty
		set(value) {
			field = value
			notifyPropertyChanged(BR.mobile)
			notifyPropertyChanged(BR.valid)
			notifyPropertyChanged(BR.mobileError)
			notifyPropertyChanged(BR.hasChanged)
		}

	@Bindable
	var email: String = String.empty
		set(value) {
			field = value
			notifyPropertyChanged(BR.email)
			notifyPropertyChanged(BR.valid)
			notifyPropertyChanged(BR.emailError)
			notifyPropertyChanged(BR.hasChanged)
		}

	@Bindable
	var priority: Boolean = false
		set(value) {
			field = value
			notifyPropertyChanged(BR.priority)
			notifyPropertyChanged(BR.hasChanged)
		}

	@Bindable
	var filePath: String? = null
		set(value) {
			field = value
			notifyPropertyChanged(BR.filePath)
			notifyPropertyChanged(BR.hasChanged)
		}

	val nameError: String?
		@Bindable
		get() = if (nameIsValid().not()) "Name can't be empty" else null

	val mobileError: String?
		@Bindable
		get() = if (mobileIsValid().not()) "Mobile must contain 9 digits" else null

	val emailError: String?
		@Bindable
		get() = if (emailIsValid().not()) "Email is not valid" else null

	val isValid: Boolean
		@Bindable
		get() = nameIsValid() && mobileIsValid() && emailIsValid()

	val hasChanged: Boolean
		@Bindable
		get() = if (initialData == null) {
			false
		} else {
			email != initialData?.email ||
					mobile != initialData?.mobile.toString() ||
					filePath != initialData?.photoPath ||
					name != initialData?.name ||
					priority != (initialData?.priority == UserPriority.PRIORITY_ICE)
		}

	fun initData(contact: Contact) {
		initialData = contact
		name = contact.name
		mobile = contact.mobile.toString()
		email = contact.email ?: String.empty
		priority = contact.priority == UserPriority.PRIORITY_ICE
		filePath = contact.photoPath
	}

	fun resetData(){
		initialData?.let {
			name = it.name
			mobile = it.mobile.toString()
			email = it.email ?: String.empty
			priority = it.priority == UserPriority.PRIORITY_ICE
			filePath = it.photoPath
		}
	}

	private fun nameIsValid() = name.isNotBlank()
	private fun mobileIsValid() = mobile.matches(Regex("\\d{9}"))
	private fun emailIsValid() = email.matches(Patterns.EMAIL_ADDRESS.toRegex()) or email.isEmpty()
}