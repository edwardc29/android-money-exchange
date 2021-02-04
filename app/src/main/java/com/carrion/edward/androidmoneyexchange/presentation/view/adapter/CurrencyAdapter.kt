package com.carrion.edward.androidmoneyexchange.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.carrion.edward.androidmoneyexchange.data.model.CurrencyCountry
import com.carrion.edward.androidmoneyexchange.databinding.ItemCurrencyBinding

class CurrencyAdapter(
    private val items: List<CurrencyCountry>,
    private val listener: (CurrencyCountry) -> Unit
) : RecyclerView.Adapter<CurrencyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding = ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        with(items[position]) {
            holder.onBind(this, listener)
        }
    }

}