package com.app.examexchange.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.examexchange.R
import com.app.examexchange.databinding.ViewCurrencyBinding
import com.app.examexchange.model.BindingFactory
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class CurrencyView : ConstraintLayout {
    private val binding: ViewCurrencyBinding by lazy { BindingFactory.bind(context, R.layout.view_currency) }
    
    val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            binding.editValue.removeTextChangedListener(this)
            try {
                var originalString = s.toString()
                val longval: Long
                if (originalString.contains(".")) {
                    originalString = originalString.replace(",".toRegex(), "")
                }
                longval = originalString.toLong()
                val formatter: DecimalFormat =
                    NumberFormat.getInstance(Locale.US) as DecimalFormat
                formatter.applyPattern("#.##")
                val formattedString: String = formatter.format(longval)
                
                //setting text after format to EditText
                binding.editValue.setText(formattedString)
                binding.editValue.setSelection(binding.editValue.text.length)
            } catch (nfe: NumberFormatException) {
                nfe.printStackTrace()
            }
            binding.editValue.addTextChangedListener(this)
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
                val textColor = getColor(R.styleable.CurrencyViewStyleable_text_color, Color.BLACK)
                
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
    
    fun initialize(listCurrencies: List<String>, block: (String) -> Unit) {
        binding.spinnerCurrency.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, listCurrencies)
    }
}