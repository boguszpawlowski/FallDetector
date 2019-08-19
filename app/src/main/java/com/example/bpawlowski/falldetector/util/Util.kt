package com.example.bpawlowski.falldetector.util

import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.bpawlowski.database.util.doNothing
import com.example.bpawlowski.falldetector.base.fragment.BaseFragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.toast(message: String, length: Int = Toast.LENGTH_SHORT) =
	Toast.makeText(requireContext(), message, length).show()

fun <T : ViewDataBinding> BaseFragment<T>.snackbar(
	source: View = binding.root,
	message: String,
	length: Int = Snackbar.LENGTH_LONG,
	actionListener: Int,
	listener: (View) -> Unit = { doNothing }
) =
	with(Snackbar.make(source, message, length)) {
		setAction(actionListener, listener)
		show()
	}

fun <T : ViewDataBinding> BaseFragment<T>.snackbar(source: View = binding.root, message: String, length: Int = Snackbar.LENGTH_LONG) =
	Snackbar.make(source, message, length).show() //todo snackbar above navigation