package com.carrion.edward.androidmoneyexchange.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.carrion.edward.androidmoneyexchange.data.model.CurrencyCountry
import com.carrion.edward.androidmoneyexchange.data.model.ExchangeRate
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Query("SELECT value FROM exchangerate WHERE origin = :origin AND destiny = :destiny")
    fun exchangeRate(origin: String, destiny: String): Flow<Float>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(exchangeRate: ExchangeRate)

    @Query("DELETE FROM exchangerate")
    suspend fun deleteAll()

    @Query("SELECT * FROM currencycountry")
    fun getCurrencyCountries(): Flow<List<CurrencyCountry>>

    @Query("SELECT * FROM currencycountry WHERE code = :code")
    fun currencyCountry(code: String): Flow<CurrencyCountry>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCurrencyCountry(currencyCountry: CurrencyCountry)


    @Query("DELETE FROM currencycountry")
    suspend fun deleteAllCurrencyCountry()
}