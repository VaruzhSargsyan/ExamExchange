package com.app.examexchange.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.app.examexchange.application.Application
import com.app.examexchange.R

class MainActivity : AppCompatActivity() {
    
    private val viewModel: MainViewModel by viewModels { ModelFactory((application as Application).repository) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
     
        viewModel.allCurrencies.observe(this) { currencies ->
            Toast.makeText(this@MainActivity, "Currencies are downloaded", Toast.LENGTH_LONG ).show()
        }
        
        viewModel.downloadCurrencies()
    }
}