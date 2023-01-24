package com.app.examexchange.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.examexchange.R
import com.app.examexchange.databinding.ViewCurrencyBinding
import com.app.examexchange.model.BindingFactory
import kotlin.math.min


/*
 * Custom UI for sell and receive conversion
 */
class CurrencyView : ConstraintLayout {
    private val binding: ViewCurrencyBinding by lazy { BindingFactory.bind(context, R.layout.view_currency) }
    private lateinit var blockCurrency: (String) -> Unit
    private lateinit var blockSum: (Float) -> Unit
    private var currency: String? = null
    private var sum: Float? = null
    private var adapter: ArrayAdapter<String>? = null
    private var listCurrencies: List<String> = ArrayList()//listOf("EUR", "USD")

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            binding.editValue.removeTextChangedListener(this)
            try {
                val originalString = s.toString()
                val value = originalString.toFloat()
                val formattedString = String.format("%.2f", value)
                
                val position = binding.editValue.selectionEnd
                binding.editValue.setText(formattedString)
                binding.editValue.setSelection(min(position, formattedString.length))
                blockSum(value)
            } catch (nfe: NumberFormatException) {
                nfe.printStackTrace()
            }
            binding.editValue.addTextChangedListener(this)
        }
    }
    
    private val onItemSelectedListener = object : OnItemSelectedListener1 {
        override var firstItemSelectionAvoided: Boolean = false
        override fun onItemSelected(position: Int) {
            blockCurrency(adapter!!.getItem(position).toString())
        }
    
        override fun onNothingSelected(p0: AdapterView<*>?) {
            blockCurrency("")
        }
    }
    
    constructor(context: Context) : super(context) { initialize(context, null)}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { initialize(context, attrs) }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { initialize(context, attrs) }
    
    private fun initialize(context: Context, attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CurrencyViewStyleable,
            0, 0
        ).apply {
        
            try {
                val imageId = getResourceId(R.styleable.CurrencyViewStyleable_image_id, R.drawable.image_arrow_down)
                val imageColor = getColor(R.styleable.CurrencyViewStyleable_image_color, Color.RED)
                val textId = getString(R.styleable.CurrencyViewStyleable_text_id)
                val textColor = getColor(R.styleable.CurrencyViewStyleable_text_color, Color.GRAY)
                
                binding.imageType.setImageResource(imageId)
                binding.imageType.imageTintList = ColorStateList.valueOf(imageColor)
                binding.textType.text = textId
                binding.textType.setTextColor(textColor)
            } finally {
                recycle()
            }
        }
        addView(
            binding.root,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
    
        binding.editValue.addTextChangedListener(textWatcher)
    }
    
    fun setup(blockCurrency: (String) -> Unit, blockSum: (Float) -> Unit) {
        this.blockCurrency = blockCurrency
        this.blockSum = blockSum
    }
    
    fun update(listCurrencies: List<String>) {
        if (adapter != null
            && this.listCurrencies.size == listCurrencies.size
            && this.listCurrencies.containsAll(listCurrencies)
            && listCurrencies.containsAll(this.listCurrencies))
            return // do not recreate an adapter if nothing changed
        
        this.listCurrencies = listCurrencies
        
        adapter = ArrayAdapter(context, R.layout.list_item_currency_spinner, listCurrencies)
        adapter?.setDropDownViewResource(R.layout.list_item_currency_spinner)
        binding.spinnerCurrency.adapter = adapter
        
        binding.spinnerCurrency.onItemSelectedListener = onItemSelectedListener
    
        currency?.let { updateOnlyValue(it) }
        sum?.let { updateOnlyValue(it) }
    }
    
    fun updateOnlyValue(sum: Float) {
        this.sum = sum
        binding.editValue.removeTextChangedListener(textWatcher)
        
        val position = binding.editValue.selectionEnd
        val textFormatted = String.format("%.2f", sum)
        binding.editValue.setText(textFormatted)
        binding.editValue.setSelection(min(position, textFormatted.length))
        
        binding.editValue.addTextChangedListener(textWatcher)
    }
    
    fun updateOnlyValue(currency: String) {
        this.currency = currency
        
        adapter?.let {
            binding.spinnerCurrency.onItemSelectedListener = null
            
            binding.spinnerCurrency.setSelection(it.getPosition(currency))
            
            binding.spinnerCurrency.onItemSelectedListener = onItemSelectedListener
        }
    }
    
    interface OnItemSelectedListener1 : OnItemSelectedListener {
        var firstItemSelectionAvoided: Boolean
    
        abstract fun onItemSelected(position: Int)
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            if (!firstItemSelectionAvoided && position == 0) {
                firstItemSelectionAvoided = true
                return
            }
            onItemSelected(position)
        }
    }
}