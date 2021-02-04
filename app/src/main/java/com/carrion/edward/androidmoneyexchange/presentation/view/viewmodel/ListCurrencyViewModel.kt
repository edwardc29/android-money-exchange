package com.carrion.edward.androidmoneyexchange.presentation.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.carrion.edward.androidmoneyexchange.data.model.CurrencyCountry
import com.carrion.edward.androidmoneyexchange.data.repository.ExchangeMoneyRepository

class ListCurrencyViewModel(repository: ExchangeMoneyRepository) : ViewModel() {

    val allCurrencyCountries: LiveData<List<CurrencyCountry>> = repository.allCurrencyCountries.asLiveData()
}