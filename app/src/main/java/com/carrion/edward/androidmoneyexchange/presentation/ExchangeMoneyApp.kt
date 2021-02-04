package com.carrion.edward.androidmoneyexchange.presentation

import android.app.Application
import com.carrion.edward.androidmoneyexchange.data.db.CurrencyDatabase
import com.carrion.edward.androidmoneyexchange.data.repository.ExchangeMoneyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ExchangeMoneyApp : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { CurrencyDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ExchangeMoneyRepository(database.currencyDao()) }
}