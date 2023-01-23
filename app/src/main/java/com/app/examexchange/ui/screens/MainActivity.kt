package com.app.examexchange.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.app.examexchange.application.Application
import com.app.examexchange.R
import com.app.examexchange.databinding.ActivityMainBinding
import com.app.examexchange.model.BindingFactory
import com.app.examexchange.model.ViewModelFactory
import com.kotlin.exam1.BalanceAdapter
import kotlin.streams.toList

class MainActivity : AppCompatActivity() {
    
    private val viewModel: MainViewModel by viewModels { ViewModelFactory((application as Application).applicationModel) }
    private lateinit var binding: ActivityMainBinding
    
    private val balanceAdapter = BalanceAdapter()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    
        binding = BindingFactory.bind(this, R.layout.activity_main)
        setContentView(binding.root)
        
        binding.buttonSubmit.setOnClickListener {
        }
    
        binding.listBalances.adapter = balanceAdapter
        binding.viewCell.setup(
            { currency -> viewModel.updateSellCurrency(currency) },
            { sum -> viewModel.updateSellSum(sum) }
        )
        binding.viewReceive.setup(
            { currency-> viewModel.updateReceiveCurrency(currency) },
            { sum -> viewModel.updateReceiveSum(sum) }
        )

        viewModel.listBalance.observe(this) { listBalance ->
//            val currencyNames = listBalance.stream().map { it.currency }.toList()
            balanceAdapter.updateBalance(listBalance)
        }

        viewModel.allCurrencies.observe(this) { currencies ->
//            val currencyNames = currencies.stream().map { it.name }.toList().subList(0, Random.nextInt(0, min(20, currencies.size)))
            val currencyNames = currencies.stream().map { it.name }.toList()
            binding.viewReceive.update(currencyNames)
            binding.viewCell.update(currencyNames)
            if (binding.viewProgressBar.visibility == View.VISIBLE) {
                binding.viewProgressBar.visibility = View.GONE
                viewModel.initDefaultExchange()
            }
        }
    
        viewModel.currencySell.observe(this) { currency ->
            currency?.let { binding.viewCell.updateOnlyValue(it.name) }
        }
    
        viewModel.sumSell.observe(this) { sum ->
            binding.viewCell.updateOnlyValue(sum)
        }
    
        viewModel.currencyReceive.observe(this) { currency ->
            currency?.let { binding.viewReceive.updateOnlyValue(it.name) }
        }
    
        viewModel.sumReceive.observe(this) { sum ->
            binding.viewReceive.updateOnlyValue(sum)
        }
        
//        viewModel.exchange.observe(this) {
//            it?.let {
//                binding.viewCell.updateOnlyValues(it.sumFrom, it.currencyFrom)
//                binding.viewReceive.updateOnlyValues(it.sumTo, it.currencyTo)
//            }
//        }
    }
}