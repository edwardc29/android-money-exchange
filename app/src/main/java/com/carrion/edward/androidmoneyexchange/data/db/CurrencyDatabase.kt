package com.carrion.edward.androidmoneyexchange.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.carrion.edward.androidmoneyexchange.data.dao.CurrencyDao
import com.carrion.edward.androidmoneyexchange.data.model.CurrencyCountry
import com.carrion.edward.androidmoneyexchange.data.model.ExchangeRate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [ExchangeRate::class, CurrencyCountry::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CurrencyDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): CurrencyDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE
                ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, CurrencyDatabase::class.java, "currency_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(
                        WordDatabaseCallback(
                            scope
                        )
                    )
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(
                            database.currencyDao()
                        )
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(currencyDao: CurrencyDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            currencyDao.deleteAllCurrencyCountry()

            //PEN
            var currency = ExchangeRate(1, "PEN", "PEN", 1f)
            currencyDao.insert(currency)
            currency = ExchangeRate(2, "PEN", "USD", 0.273f)
            currencyDao.insert(currency)
            currency = ExchangeRate(3, "PEN", "JPY", 28.85f)
            currencyDao.insert(currency)
            currency = ExchangeRate(4, "PEN", "BOB", 1.894f)
            currencyDao.insert(currency)

            //USD
            currency = ExchangeRate(12, "USD", "PEN", 3.624f)
            currencyDao.insert(currency)
            currency = ExchangeRate(22, "USD", "USD", 1f)
            currencyDao.insert(currency)
            currency = ExchangeRate(32, "USD", "JPY", 105.019f)
            currencyDao.insert(currency)
            currency = ExchangeRate(42, "USD", "BOB", 6.897f)
            currencyDao.insert(currency)

            //JPY
            currency = ExchangeRate(13, "JPY", "PEN", 0.034f)
            currencyDao.insert(currency)
            currency = ExchangeRate(23, "JPY", "USD", 0.009f)
            currencyDao.insert(currency)
            currency = ExchangeRate(33, "JPY", "JPY", 1f)
            currencyDao.insert(currency)
            currency = ExchangeRate(43, "JPY", "BOB", 0.065f)
            currencyDao.insert(currency)

            //BOB
            currency = ExchangeRate(14, "BOB", "PEN", 0.527f)
            currencyDao.insert(currency)
            currency = ExchangeRate(24, "BOB", "USD", 0.144f)
            currencyDao.insert(currency)
            currency = ExchangeRate(34, "BOB", "JPY", 15.225f)
            currencyDao.insert(currency)
            currency = ExchangeRate(44, "BOB", "BOB", 1f)
            currencyDao.insert(currency)

            //------------------------------------------------------------------------------------

            currencyDao.deleteAllCurrencyCountry()

            var currencyCountryModel = CurrencyCountry("PEN", "Soles", "Perú")
            currencyDao.insertCurrencyCountry(currencyCountryModel)
            currencyCountryModel = CurrencyCountry("USD", "Dólares", "Estados Unidos de América")
            currencyDao.insertCurrencyCountry(currencyCountryModel)
            currencyCountryModel = CurrencyCountry("JPY", "Yenes", "Japón")
            currencyDao.insertCurrencyCountry(currencyCountryModel)
            currencyCountryModel = CurrencyCountry("BOB", "Boliviano", "Bolivia")
            currencyDao.insertCurrencyCountry(currencyCountryModel)
        }
    }
}