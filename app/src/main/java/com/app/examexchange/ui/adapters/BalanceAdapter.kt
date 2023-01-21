package com.kotlin.exam1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.examexchange.database.entities.Balance
import com.app.examexchange.databinding.ListItemBalanceBinding
import com.app.examexchange.utils.BalanceDiffUtilCallback

class BalanceAdapter : RecyclerView.Adapter<BalanceListViewHolder>() {

    private var listBalance = mutableListOf<Balance>()

    fun updateBalance(listBalance: List<Balance>) {
        val diffResult = DiffUtil.calculateDiff(BalanceDiffUtilCallback(this.listBalance, listBalance))

        this.listBalance.clear()
        this.listBalance.addAll(listBalance.toMutableList())

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBalanceBinding.inflate(inflater, parent, false)
        return BalanceListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BalanceListViewHolder, position: Int) {
        holder.binding.textCurrency.text = listBalance[position].currency
        holder.binding.textValue.text = listBalance[position].getFormattedValueString()
    }

    override fun getItemCount(): Int {
        return listBalance.size
    }
}

class BalanceListViewHolder(val binding: ListItemBalanceBinding) : RecyclerView.ViewHolder(binding.root)

