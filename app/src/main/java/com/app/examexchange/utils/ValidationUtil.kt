package com.app.examexchange.utils

import androidx.recyclerview.widget.DiffUtil
import com.app.examexchange.database.entities.Balance

class BalanceDiffUtilCallback(private val oldList: List<Balance>, private val newList: List<Balance>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].currency == newList[newItemPosition].currency
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].value == newList[newItemPosition].value
    }
}

