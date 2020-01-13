package com.example.bpawlowski.falldetector.util

import android.content.Context
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.list.listItems
import com.example.bpawlowski.falldetector.R

const val DIALOG_DISMISS_DELAY = 300L

inline fun showBottomSheetDialog(context: Context, crossinline onItemSelectedListener: (Int) -> Unit) =
    MaterialDialog(context, BottomSheet(LayoutMode.WRAP_CONTENT))
        .show {
            listItems(R.array.bottom_sheet_items) { _, index, _ ->
                dismiss()
                postDelayed(DIALOG_DISMISS_DELAY) {
                    onItemSelectedListener(index)
                }
            }
            cornerRadius(res = R.dimen.bottom_sheet_corner_radius)
            view.clearFocus()
        }
