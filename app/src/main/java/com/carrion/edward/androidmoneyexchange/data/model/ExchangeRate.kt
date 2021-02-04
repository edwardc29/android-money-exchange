package com.carrion.edward.androidmoneyexchange.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExchangeRate(@PrimaryKey(autoGenerate = true) val id: Int, val origin: String, val destiny: String, val value: Float)