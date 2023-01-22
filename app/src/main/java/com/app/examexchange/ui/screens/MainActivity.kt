package com.app.examexchange.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
    
        binding.listBalances.adapter = balanceAdapter
        
        viewModel.listBalance.observe(this) { listBalance ->
            balanceAdapter.updateBalance(listBalance)
        }

        viewModel.allCurrencies.observe(this) { currencies ->
            val currencyNames = currencies.stream().map { it.name }.toList()
            binding.viewCell.initialize(currencyNames) {
        
            }
            binding.viewReceive.initialize(currencyNames) {
            
            }
        }
    }
}