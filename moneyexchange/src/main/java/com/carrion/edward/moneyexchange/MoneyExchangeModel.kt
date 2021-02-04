package com.carrion.edward.moneyexchange

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoneyExchangeModel(val code: String, val description: String) : Parcelable