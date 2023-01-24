package com.app.examexchange.ui.custom

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.app.examexchange.R
import com.app.examexchange.databinding.DialogSimpleBinding
import com.app.examexchange.model.BindingFactory

class SimpleDialog(context: Context?, title: String, message: String, private val block: () -> Unit) : Runnable {
    private val dialog: Dialog

    override fun run() {
        show()
    }

    private fun show() {
        dialog.show()
    }

    init {
        val binding: DialogSimpleBinding = BindingFactory.bind(LayoutInflater.from(context), R.layout.dialog_simple)

        dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialog.setContentView(binding.root)

        dialog.setCancelable(true)

        dialog.setOnDismissListener { block() }

        binding.buttonClose.setOnClickListener {
            block()
            dialog.dismiss()
        }
        binding.textDialog.text = message
        binding.titleDialog.text = title
    }
}