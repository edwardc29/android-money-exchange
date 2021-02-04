package com.carrion.edward.androidmoneyexchange.presentation.extension

import com.carrion.edward.androidmoneyexchange.R
import com.carrion.edward.androidmoneyexchange.presentation.CurrencyEnum

fun flag(code: String): Int {
    return when (code) {
        CurrencyEnum.PERU.code -> R.drawable.ic_flag_peru
        CurrencyEnum.USA.code -> R.drawable.ic_flag_usa
        CurrencyEnum.JAPAN.code -> R.drawable.ic_flag_japan
        CurrencyEnum.BOLIVIA.code -> R.drawable.ic_flag_bolivia
        else -> R.drawable.ic_flag_peru
    }
}