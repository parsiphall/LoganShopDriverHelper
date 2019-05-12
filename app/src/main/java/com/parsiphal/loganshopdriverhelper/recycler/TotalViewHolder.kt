package com.parsiphal.loganshopdriverhelper.recycler

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.total_item.view.*

class TotalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val dayOrMonth = view.total_item_dayOrMonth!!
    val date = view.total_item_date!!
    val salary = view.total_item_salary!!
    val distance = view.total_item_dist!!
    val delete = view.total_delete_button!!
}