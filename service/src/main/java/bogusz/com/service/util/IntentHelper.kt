package bogusz.com.service.util

import android.content.Context
import android.content.Intent
import android.net.Uri

fun callNumber(context: Context, number: Int) =
    with(Intent(Intent.ACTION_CALL)) {
        data = Uri.parse("tel:$number")
        context.startActivity(this)
    }