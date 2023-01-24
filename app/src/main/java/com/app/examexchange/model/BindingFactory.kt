package com.app.examexchange.model

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.app.examexchange.R
import com.app.examexchange.databinding.ActivityMainBinding
import com.app.examexchange.databinding.DialogSimpleBinding
import com.app.examexchange.databinding.ListItemBalanceBinding
import com.app.examexchange.databinding.ListItemCurrencySpinnerBinding
import com.app.examexchange.databinding.ViewCurrencyBinding
import com.app.examexchange.databinding.ViewProgressBinding

@Suppress("UNCHECKED_CAST")
class BindingFactory {
    companion object {
        fun <T : ViewBinding> bind(context: Context, @LayoutRes idLayout: Int): T =
            bind(LayoutInflater.from(context), idLayout)
    
        fun <T : ViewBinding> bind(layoutInflater: LayoutInflater, @LayoutRes idLayout: Int): T =
            when (idLayout) {
                // activities
                R.layout.activity_main -> ActivityMainBinding.inflate(layoutInflater)
                
                // activities
                R.layout.dialog_simple -> DialogSimpleBinding.inflate(layoutInflater)
    
                // custom views
                R.layout.view_currency -> ViewCurrencyBinding.inflate(layoutInflater)
                R.layout.view_progress -> ViewProgressBinding.inflate(layoutInflater)
                R.layout.list_item_currency_spinner -> ListItemCurrencySpinnerBinding.inflate(layoutInflater)
                R.layout.list_item_balance -> ListItemBalanceBinding.inflate(layoutInflater)
    
                else -> throw IllegalArgumentException("Layout Id not found")
            } as T
    }
}