package com.app.examexchange.ui

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
            Toast.makeText(this@MainActivity, "Currencies are downloaded", Toast.LENGTH_SHORT).show()
        }
    }
}