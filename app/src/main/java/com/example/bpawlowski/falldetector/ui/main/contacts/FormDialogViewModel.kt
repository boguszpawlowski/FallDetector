package com.example.bpawlowski.falldetector.ui.main.contacts

import android.annotation.SuppressLint
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import bogusz.com.service.database.repository.ContactRepository
import bogusz.com.service.model.Contact
import bogusz.com.service.rx.SchedulerProvider
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.util.validate
import io.reactivex.Single
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@SuppressLint("CheckResult")
class FormDialogViewModel @Inject constructor(
    private val contactsRepository: ContactRepository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    val dataValid = ObservableBoolean(false)
    val nameError = ObservableField<String>()
    val mobileError = ObservableField<String>()
    val emailError = ObservableField<String>()
    val nameSubject = BehaviorSubject.createDefault("")
    val mobileSubject = BehaviorSubject.createDefault("")
    val emailSubject = BehaviorSubject.createDefault("")

    init {
        initValidator()
    }

    fun checkIfIceExists(): Single<Boolean> =
        contactsRepository.isIceContactExisting()
            .observeOn(schedulerProvider.MAIN)

    fun initEditingData(id: Long): Single<Contact> =
        contactsRepository.getContact(id)
            .observeOn(schedulerProvider.MAIN)

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