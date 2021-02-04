package com.carrion.edward.androidmoneyexchange.presentation.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.carrion.edward.androidmoneyexchange.R
import com.carrion.edward.androidmoneyexchange.data.model.CurrencyCountry
import com.carrion.edward.androidmoneyexchange.databinding.ActivityExchangeMoneyBinding
import com.carrion.edward.androidmoneyexchange.presentation.Constant.CURRENCY_COUNTRY_KEY
import com.carrion.edward.androidmoneyexchange.presentation.ExchangeMoneyApp
import com.carrion.edward.androidmoneyexchange.presentation.view.viewmodel.ExchangeMoneyViewModel
import com.carrion.edward.androidmoneyexchange.presentation.view.viewmodel.ExchangeMoneyViewModelFactory
import com.carrion.edward.moneyexchange.MoneyExchangeModel

const val ORIGIN_CURRENCY_REQUEST_CODE = 1000
const val DESTINY_CURRENCY_REQUEST_CODE = 1001

class ExchangeMoneyActivity : AppCompatActivity() {

    private val exchangeMoneyViewModel: ExchangeMoneyViewModel by viewModels {
        ExchangeMoneyViewModelFactory((application as ExchangeMoneyApp).repository)
    }

    private lateinit var binding: ActivityExchangeMoneyBinding

    private lateinit var originCurrency: MoneyExchangeModel
    private lateinit var destinyCurrency: MoneyExchangeModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExchangeMoneyBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setupExchangeMoneyView()
    }

    private fun setupExchangeMoneyView() {
        originCurrency = MoneyExchangeModel("PEN", "Soles")
        destinyCurrency = MoneyExchangeModel("USD", "Dólares")

        binding.moneyExchangeView.originCurrencyField = getString(R.string.you_send)
        binding.moneyExchangeView.destinyCurrencyField = getString(R.string.you_get)

        binding.moneyExchangeView.setup(originCurrency, destinyCurrency)

        binding.moneyExchangeView.setOnLongClickOrigin(View.OnLongClickListener {
            startActivityForResult(ListCurrencyActivity.newIntent(this@ExchangeMoneyActivity), ORIGIN_CURRENCY_REQUEST_CODE)
            true
        })
        binding.moneyExchangeView.setOnLongClickDestiny(View.OnLongClickListener {
            startActivityForResult(ListCurrencyActivity.newIntent(this@ExchangeMoneyActivity), DESTINY_CURRENCY_REQUEST_CODE)
            true
        })

        binding.moneyExchangeView.currenciesLiveData.observe(this) {
            exchangeMoneyViewModel.getExchangeRate(it.first.code, it.second.code).observe(this) { exchangeRate ->
                binding.moneyExchangeView.exchangeRate = exchangeRate
                binding.moneyExchangeView.buy = exchangeRate
            }

            exchangeMoneyViewModel.getExchangeRate(it.second.code, it.first.code).observe(this) { exchangeRate ->
                binding.moneyExchangeView.sale = exchangeRate
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ORIGIN_CURRENCY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let { intent ->
                    intent.getParcelableExtra<CurrencyCountry>(CURRENCY_COUNTRY_KEY)?.let { currency ->
                        binding.moneyExchangeView.setOriginCurrency(MoneyExchangeModel(currency.code, currency.currency))
                    }

                }
            }
        } else if (requestCode == DESTINY_CURRENCY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let { intent ->
                    intent.getParcelableExtra<CurrencyCountry>(CURRENCY_COUNTRY_KEY)?.let { currency ->
                        binding.moneyExchangeView.setDestinyCurrency(MoneyExchangeModel(currency.code, currency.currency))
                    }

                }
            }
        }
    }
}