package com.carrion.edward.androidmoneyexchange.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class CurrencyCountry(@PrimaryKey val code: String = "", val currency: String = "", val country: String = "") : Parcelable {
    @Ignore
    constructor() : this("")
}