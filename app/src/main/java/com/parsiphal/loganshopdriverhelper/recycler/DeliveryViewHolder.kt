package com.parsiphal.loganshopdriverhelper.recycler

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.delivery_item.view.*

class DeliveryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val deliveryDate = view.delivery_item_date!!
    val workType = view.delivery_item_work_type!!
    val deliveryType = view.delivery_item_delivery_type!!
    val payType = view.delivery_item_pay_type!!
    val address = view.delivery_item_address!!
    val cost = view.delivery_item_cost!!
    val comment = view.delivery_item_comment!!
    val delete = view.delivery_delete_button!!
}