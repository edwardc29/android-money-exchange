package com.carrion.edward.androidmoneyexchange.presentation.view.viewmodel

import androidx.lifecycle.*
import com.carrion.edward.androidmoneyexchange.data.model.CurrencyCountry
import com.carrion.edward.androidmoneyexchange.data.repository.ExchangeMoneyRepository

class ExchangeMoneyViewModel(private val repository: ExchangeMoneyRepository) : ViewModel() {

    fun getCurrencyCountry(code: String): LiveData<CurrencyCountry> {
        return repository.currencyCountry(code).asLiveData()
    }

    fun getExchangeRate(origin: String, destiny: String): LiveData<Float> {
        return repository.exchangeRate(origin, destiny).asLiveData()
    }
}