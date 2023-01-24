package com.app.examexchange.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.examexchange.R
import com.app.examexchange.databinding.ViewProgressBinding
import com.app.examexchange.model.BindingFactory

/*
 * Custom Progress Bar UI to lock the screen for back calls
 */
class ProgressView : ConstraintLayout {
    private val binding: ViewProgressBinding by lazy { BindingFactory.bind(context, R.layout.view_progress) }

    constructor(context: Context) : super(context) { initialize()}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { initialize()}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { initialize()}

    private fun initialize() {
        addView(binding.root, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
    }
}