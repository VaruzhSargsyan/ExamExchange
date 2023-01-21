package com.app.examexchange.model

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.app.examexchange.R
import com.app.examexchange.databinding.ActivityMainBinding

@Suppress("UNCHECKED_CAST")
class BindingFactory {
    companion object {
        fun <T : ViewBinding> bind(context: Context, @LayoutRes idLayout: Int): T =
            bind(LayoutInflater.from(context), idLayout)
    
        fun <T : ViewBinding> bind(layoutInflater: LayoutInflater, @LayoutRes idLayout: Int): T =
            when (idLayout) {
                R.layout.activity_main -> ActivityMainBinding.inflate(layoutInflater)
                else -> throw IllegalArgumentException("Layout Id not found")
            } as T
    }
}