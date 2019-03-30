package com.example.bpawlowski.falldetector.presentation.activity.main.contacts

import android.annotation.SuppressLint
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.example.bpawlowski.falldetector.presentation.activity.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.presentation.util.validate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@SuppressLint("CheckResult")
class FormDialogViewModel @Inject constructor() : BaseViewModel() {

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

    private fun initValidator() {
        disposable.add(
            Observables.combineLatest(nameValidator(), mobileValidator(), emailValidator()) { name, mobile, email ->
                name && mobile && email
            }.distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { dataValid.set(it) }
        )
    }

    private fun mobileValidator() =
        mobileSubject.validate(mobileError, "Mobile must contain 9 digits!") {
            it.matches(Regex("\\d{9}"))
        }

    private fun emailValidator() =
        emailSubject.validate( emailError, "Email not valid!") {
            it.matches(emailRegex) || it.isEmpty()
        }

    private fun nameValidator() =
        nameSubject.validate(nameError, "Name can`t be empty!") { it.isNotEmpty() }

    companion object {
        private val emailRegex =
            Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")
        private val TAG = FormDialogViewModel::class.java.name
    }
}