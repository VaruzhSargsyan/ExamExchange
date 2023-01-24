package com.kotlin.exam1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.examexchange.R
import com.app.examexchange.database.entities.Balance
import com.app.examexchange.databinding.ListItemBalanceBinding
import com.app.examexchange.model.BindingFactory
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
        val binding: ListItemBalanceBinding = BindingFactory.bind(parent.context, R.layout.list_item_balance)
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

