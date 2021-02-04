package com.carrion.edward.androidmoneyexchange.presentation.view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.carrion.edward.androidmoneyexchange.data.model.CurrencyCountry
import com.carrion.edward.androidmoneyexchange.databinding.ItemCurrencyBinding
import com.carrion.edward.androidmoneyexchange.presentation.extension.flag

class CurrencyViewHolder(private val itemCurrencyBinding: ItemCurrencyBinding) : RecyclerView.ViewHolder(itemCurrencyBinding.root) {

    fun onBind(currency: CurrencyCountry, listener: (CurrencyCountry) -> Unit) = with(itemView) {
        itemCurrencyBinding.flagImageView.setBackgroundResource(flag(currency.code))
        itemCurrencyBinding.currencyCountryTextView.text = currency.country
        itemCurrencyBinding.currencyTextView.text = currency.currency

        setOnClickListener {
            listener(currency)
        }
    }
}