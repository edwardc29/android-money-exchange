package com.carrion.edward.androidmoneyexchange.data.repository

import com.carrion.edward.androidmoneyexchange.data.dao.CurrencyDao
import com.carrion.edward.androidmoneyexchange.data.model.CurrencyCountry
import kotlinx.coroutines.flow.Flow

class ExchangeMoneyRepository(private val currencyDao: CurrencyDao) {
    val allCurrencyCountries: Flow<List<CurrencyCountry>> = currencyDao.getCurrencyCountries()

    fun exchangeRate(origin: String, destiny: String): Flow<Float> {
        return currencyDao.exchangeRate(origin, destiny)
    }

    fun currencyCountry(code: String): Flow<CurrencyCountry> {
        return currencyDao.currencyCountry(code)
    }
}