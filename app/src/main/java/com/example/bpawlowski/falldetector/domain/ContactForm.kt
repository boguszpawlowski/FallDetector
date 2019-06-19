package com.example.bpawlowski.falldetector.domain

import android.util.Patterns
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.example.bpawlowski.falldetector.util.empty

class ContactForm : BaseObservable() {

    @Bindable
    var name: String = String.empty
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
            notifyPropertyChanged(BR.valid)
            notifyPropertyChanged(BR.nameError)
        }

    @Bindable
    var mobile: String = String.empty
        set(value) {
            field = value
            notifyPropertyChanged(BR.mobile)
            notifyPropertyChanged(BR.valid)
            notifyPropertyChanged(BR.mobileError)
        }

    @Bindable
    var email: String = String.empty
        set(value) {
            field = value
            notifyPropertyChanged(BR.email)
            notifyPropertyChanged(BR.valid)
            notifyPropertyChanged(BR.emailError)
        }

    @Bindable
    var priority: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.priority)
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

    private fun nameIsValid() = name.isNotBlank()
    private fun mobileIsValid() = mobile.matches(Regex("\\d{9}"))
    private fun emailIsValid() = email.matches(Patterns.EMAIL_ADDRESS.toRegex()) or email.isEmpty()
}