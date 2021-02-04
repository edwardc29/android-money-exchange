package com.carrion.edward.moneyexchange.extension

import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.widget.EditText
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException

fun EditText.onChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            cb(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

/**
 * Extension function to input filter edit text decimal digits
 *
 * @param maxDigitsIncludingPoint maximum digits including point and without decimal places
 * @param maxDecimalPlaces maximum decimal places
 */
fun EditText.inputFilterDecimal(maxDigitsIncludingPoint: Int, maxDecimalPlaces: Int = 2) {
    try {
        filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(maxDigitsIncludingPoint, maxDecimalPlaces))
    } catch (e: PatternSyntaxException) {
        isEnabled = false
        hint = e.message
    }
}

/**
 * Class to decimal digits input filter
 */
class DecimalDigitsInputFilter(maxDigitsIncludingPoint: Int, maxDecimalPlaces: Int) : InputFilter {

    private val pattern: Pattern = Pattern.compile(
        "[0-9]{0," + (maxDigitsIncludingPoint - 1) + "}+((\\.[0-9]{0," + (maxDecimalPlaces - 1) + "})?)||(\\.)?"
    )

    override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
        dest?.apply {
            val matcher: Matcher = pattern.matcher(dest)
            return if (!matcher.matches()) "" else null
        }

        return null

    }

}