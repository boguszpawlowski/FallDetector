package com.example.bpawlowski.falldetector.ui.custom

import android.content.Context
import android.preference.ListPreference
import android.util.AttributeSet
import android.view.View
import com.example.bpawlowski.falldetector.R

class ListDialogPreference @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ListPreference(context, attrs, defStyleAttr, defStyleRes) { //FIXME crashes

    override fun onBindDialogView(view: View?) {
        super.onBindDialogView(view)

        dialog.window?.setBackgroundDrawable(context.getDrawable(R.drawable.backgroud_rounded))
    }
}