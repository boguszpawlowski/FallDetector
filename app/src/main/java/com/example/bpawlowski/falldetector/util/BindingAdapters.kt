package com.example.bpawlowski.falldetector.util

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.subjects.BehaviorSubject

@BindingAdapter(value = ["visible"])
fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter(value = ["rxText"])
fun EditText.rxText(subject: BehaviorSubject<String>) {
    setText(subject.value?: "")

    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable) {
            subject.onNext(editable.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = doNothing
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = doNothing
    })
}

@BindingAdapter(value = ["error"])
fun TextInputLayout.error(error: String?) = setError(error)
