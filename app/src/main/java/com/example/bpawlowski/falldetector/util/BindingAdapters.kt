package com.example.bpawlowski.falldetector.util

import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter(value = ["visible"])
fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter(value = ["error"])
fun TextInputLayout.error(error: String?) = setError(error)

@BindingAdapter(value = ["mobile"])
fun EditText.mobile(mobile: MutableLiveData<Int>) {
    if (value != mobile.value.toString()) {
        setText(mobile.value.toString())
    }

    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable) {
            mobile.value = editable.toString().toInt()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = doNothing
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = doNothing
    })
}

@BindingAdapter("file_src")
fun ImageView.fileSrc(filepath: String?) {
	filepath?.let { loadContactImage(context, Uri.parse(it), this) }
}