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
import com.app.examexchange.ui.custom.SimpleDialog
import com.kotlin.exam1.BalanceAdapter
import kotlin.streams.toList

/*
 * The only activity in the task :)
 */
class MainActivity : AppCompatActivity() {
    
    private val viewModel: MainViewModel by viewModels { ViewModelFactory((application as Application).applicationModel) }
    private lateinit var binding: ActivityMainBinding
    
    private val balanceAdapter = BalanceAdapter()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUI()
        initModel()
    }
    
    private fun showSimpleDialog(title: String, message: String, block: () -> Unit) {
        this@MainActivity.runOnUiThread(SimpleDialog(this@MainActivity, title, message, block))
    }
    
    private fun initUI () {
        binding = BindingFactory.bind(this, R.layout.activity_main)
        setContentView(binding.root)
    
        binding.buttonSubmit.setOnClickListener { viewModel.verifyAndSubmitExchange() }
    
        binding.listBalances.adapter = balanceAdapter
        binding.viewCell.setup(
            { currency -> viewModel.updateSellCurrency(currency) },
            { sum -> viewModel.updateSellSum(sum) }
        )
        binding.viewReceive.setup(
            { currency-> viewModel.updateReceiveCurrency(currency) },
            { sum -> viewModel.updateReceiveSum(sum) }
        )
    }
    
    private fun initModel() {
        viewModel.listBalance.observe(this) { listBalance ->
            balanceAdapter.updateBalance(listBalance)
        }
    
        viewModel.listCurrencies.observe(this) { currencies ->
            viewModel.initDefaultExchange()
        
            val currencyNames = currencies.stream().map { it.name }.toList()
            binding.viewReceive.update(currencyNames)
            binding.viewCell.update(currencyNames)
            if (binding.viewProgressBar.visibility == View.VISIBLE) {
                binding.viewProgressBar.visibility = View.GONE
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
    
        viewModel.message.observe(this) {
            it?.let { showSimpleDialog(it.first, it.second) {} }
        }
    }
}