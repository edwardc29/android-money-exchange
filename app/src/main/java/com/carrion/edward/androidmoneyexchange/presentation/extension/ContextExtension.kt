package com.carrion.edward.androidmoneyexchange.presentation.extension

import android.content.Context
import com.carrion.edward.androidmoneyexchange.R

fun Context.flag(code: String): Int {
    return when (code) {
        "PEN" -> R.drawable.ic_flag_peru
        "USD" -> R.drawable.ic_flag_usa
        "JPY" -> R.drawable.ic_flag_japan
        "BOB" -> R.drawable.ic_flag_bolivia
        else -> R.drawable.ic_flag_peru
    }
}