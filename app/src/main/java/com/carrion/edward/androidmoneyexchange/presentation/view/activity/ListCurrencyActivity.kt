package com.carrion.edward.androidmoneyexchange.presentation.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.carrion.edward.androidmoneyexchange.databinding.ActivityListCurrencyBinding
import com.carrion.edward.androidmoneyexchange.presentation.Constant
import com.carrion.edward.androidmoneyexchange.presentation.ExchangeMoneyApp
import com.carrion.edward.androidmoneyexchange.presentation.view.adapter.CurrencyAdapter
import com.carrion.edward.androidmoneyexchange.presentation.view.viewmodel.ExchangeMoneyViewModelFactory
import com.carrion.edward.androidmoneyexchange.presentation.view.viewmodel.ListCurrencyViewModel

class ListCurrencyActivity : AppCompatActivity() {

    private val listCurrencyViewModel: ListCurrencyViewModel by viewModels {
        ExchangeMoneyViewModelFactory((application as ExchangeMoneyApp).repository)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ListCurrencyActivity::class.java)
        }
    }

    private lateinit var binding: ActivityListCurrencyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCurrencyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listCurrencyViewModel.allCurrencyCountries.observe(this) { currencyCountries ->
            val adapter = CurrencyAdapter(this, currencyCountries) {
                val intent = Intent()
                intent.putExtra(Constant.CURRENCY_COUNTRY_KEY, it)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            binding.currencyRecyclerView.adapter = adapter
            binding.currencyRecyclerView.setHasFixedSize(true)

            binding.currencyRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    this,
                    (binding.currencyRecyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
        }
    }
}