package com.carrion.edward.androidmoneyexchange.presentation.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.carrion.edward.androidmoneyexchange.data.repository.ExchangeMoneyRepository

class ExchangeMoneyViewModelFactory(private val repository: ExchangeMoneyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExchangeMoneyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExchangeMoneyViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ListCurrencyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListCurrencyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}