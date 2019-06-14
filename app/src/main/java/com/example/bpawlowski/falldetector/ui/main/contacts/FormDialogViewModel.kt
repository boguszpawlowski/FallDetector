package com.example.bpawlowski.falldetector.ui.main.contacts

import android.annotation.SuppressLint
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import bogusz.com.service.database.onFailure
import bogusz.com.service.database.onSuccess
import bogusz.com.service.database.repository.ContactRepository
import bogusz.com.service.model.Contact
import bogusz.com.service.rx.SchedulerProvider
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.util.validate
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class FormDialogViewModel @Inject constructor(
    private val contactsRepository: ContactRepository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() { //TODO exception handling: Snackbar/Toast?

    val dataValid = ObservableBoolean(false)
    val nameError = ObservableField<String>()
    val mobileError = ObservableField<String>()
    val emailError = ObservableField<String>()
    val nameSubject = BehaviorSubject.createDefault("")
    val mobileSubject = BehaviorSubject.createDefault("")
    val emailSubject = BehaviorSubject.createDefault("")

    private val _addContactResultData = MutableLiveData<Boolean>()
    val addContactResultData: LiveData<Boolean>
        get() = _addContactResultData

    private val _initialContactData = MutableLiveData<Contact>() //TODO binding - one model for it
    val initialContactData: LiveData<Contact>
        get() = _initialContactData

    init {
        initValidator()
    }

    fun initEditingData(id: Long) = viewModelScope.launch {
        contactsRepository.getContact(id)
            .onSuccess { _initialContactData.postValue(it) }
            .onFailure { Timber.e(it) }
    }

    fun tryToAddContact(contact: Contact) = viewModelScope.launch {
        contactsRepository.addContact(contact)
            .onSuccess { _addContactResultData.postValue(true) }
            .onFailure {
                Timber.e(it)
                _addContactResultData.postValue(false)
            }
    }

    private fun initValidator() {
        disposable.add(
            Observables.combineLatest(nameValidator(), mobileValidator(), emailValidator()) { name, mobile, email ->
                name && mobile && email
            }.distinctUntilChanged()
                .observeOn(schedulerProvider.MAIN)
                .subscribe { dataValid.set(it) }
        )
    }

    private fun mobileValidator() =
        mobileSubject.validate(mobileError, "Mobile must contain 9 digits!") {
            it.matches(Regex("\\d{9}"))
        }

    private fun emailValidator() =
        emailSubject.validate(emailError, "Email not valid!") {
            it.matches(emailRegex) || it.isEmpty()
        }

    private fun nameValidator() =
        nameSubject.validate(nameError, "Name can`t be empty!") { it.isNotEmpty() }

    companion object {
        private val emailRegex =
            Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")
    }
}