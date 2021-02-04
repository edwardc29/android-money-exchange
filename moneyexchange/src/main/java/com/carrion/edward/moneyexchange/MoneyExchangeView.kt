package com.carrion.edward.moneyexchange

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import com.carrion.edward.moneyexchange.databinding.ViewMoneyExchangeBinding
import com.carrion.edward.moneyexchange.extension.inputFilterDecimal
import com.carrion.edward.moneyexchange.extension.onChange
import com.carrion.edward.moneyexchange.extension.rotate
import com.carrion.edward.moneyexchange.extension.roundOff

class MoneyExchangeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ViewMoneyExchangeBinding = ViewMoneyExchangeBinding.inflate(LayoutInflater.from(context), this)

    private lateinit var originCurrency: MoneyExchangeModel
    private lateinit var destinyCurrency: MoneyExchangeModel

    var buy = 0f
        set(value) {
            field = value
            setExchangeRateFields()

        }
    var sale = 0f
        set(value) {
            field = value

            setExchangeRateFields()
        }


    var originCurrencyField: String = ""
        set(value) {
            field = value
            binding.originCurrencyTextView.text = value
        }

    var destinyCurrencyField: String = ""
        set(value) {
            field = value
            binding.destinyCurrencyTextView.text = value
        }

    var exchangeRate: Float = 0f
        set(value) {
            field = value
            calculate()
        }

    var currenciesLiveData: MutableLiveData<Pair<MoneyExchangeModel, MoneyExchangeModel>> = MutableLiveData()

    init {

        setupUI(context)

        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        attrs?.let { attributeSet ->
            val typedArray =
                context.theme.obtainStyledAttributes(attributeSet, R.styleable.MoneyExchangeView, 0, 0)

            typedArray.getString(R.styleable.MoneyExchangeView_origin_money_field)?.let {
                binding.originCurrencyTextView.text = it
            }

            typedArray.getString(R.styleable.MoneyExchangeView_destiny_money_field)?.let {
                binding.destinyCurrencyTextView.text = it
            }

            typedArray.recycle()
        }
    }

    private fun setupUI(context: Context) {
        binding.originCurrencyEditText.inputFilterDecimal(maxDigitsIncludingPoint = 5, maxDecimalPlaces = 2)
        binding.destinyCurrencyEditText.inputFilterDecimal(maxDigitsIncludingPoint = 5, maxDecimalPlaces = 2)

        binding.swapCurrenciesImageView.setOnClickListener {
            it.rotate()
            swap()
        }

        binding.originCurrencyEditText.onChange {
            calculate()
        }
    }

    private fun calculate() {
        Log.i("-zzzz", "exchangeRate=$exchangeRate")

        val numberString = binding.originCurrencyEditText.text.toString()
        try {
            val value = exchangeRate * numberString.toFloat()
            binding.destinyCurrencyEditText.setText(value.roundOff())
        } catch (exception: Exception) {
            binding.destinyCurrencyEditText.setText("")
        }
    }

    fun setExchangeRateFields() {
        binding.buySaleTextView.text = context.getString(R.string.buy_sale, this.buy.roundOff(), sale.roundOff())
    }

    private fun swap() {
        setup(destinyCurrency, originCurrency)
        calculate()
    }

    fun setup(originCurrency: MoneyExchangeModel, destinyCurrency: MoneyExchangeModel) {
        this.originCurrency = originCurrency
        this.destinyCurrency = destinyCurrency

        currenciesLiveData.value = Pair(originCurrency, destinyCurrency)

        binding.nameCurrencyOriginTextView.text = originCurrency.description
        binding.nameCurrencyDestinyTextView.text = destinyCurrency.description
    }

    fun setOriginCurrency(currency: MoneyExchangeModel) {
        originCurrency = currency
        currenciesLiveData.value = Pair(originCurrency, destinyCurrency)

        binding.nameCurrencyOriginTextView.text = originCurrency.description
    }

    fun setDestinyCurrency(currency: MoneyExchangeModel) {
        destinyCurrency = currency
        currenciesLiveData.value = Pair(originCurrency, destinyCurrency)

        binding.nameCurrencyDestinyTextView.text = destinyCurrency.description
    }

    fun setOnLongClickOrigin(listener: OnLongClickListener) {
        binding.nameCurrencyOriginTextView.setOnLongClickListener(listener)
    }

    fun setOnLongClickDestiny(listener: OnLongClickListener) {
        binding.nameCurrencyDestinyTextView.setOnLongClickListener(listener)
    }
}