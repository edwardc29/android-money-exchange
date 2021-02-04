package com.carrion.edward.moneyexchange.extension

import java.text.DecimalFormat

/**
 * rounds off float value to two decimal places and returns String
 */
fun Float.roundOff(): String {
    val df = DecimalFormat("#,###.##")
    return df.format(this)
}