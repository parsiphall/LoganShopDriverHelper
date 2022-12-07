package com.parsiphal.loganshopdriverhelper.recycler

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.parsiphal.loganshopdriverhelper.databinding.TotalItemBinding

class TotalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = TotalItemBinding.bind(view)
    val dayOrMonth = binding.totalItemDayOrMonth
    val date = binding.totalItemDate
    val salary = binding.totalItemSalary
    val distance = binding.totalItemDist
    val delete = binding.totalDeleteButton
}