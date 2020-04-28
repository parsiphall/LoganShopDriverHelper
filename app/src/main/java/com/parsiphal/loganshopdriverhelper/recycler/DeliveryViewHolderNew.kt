package com.parsiphal.loganshopdriverhelper.recycler

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.delivery_item.view.*

class DeliveryViewHolderNew(view: View) : RecyclerView.ViewHolder(view) {
    val workType = view.delivery_item_work_type!!
    val deliveryType = view.delivery_item_delivery_type!!
    val address = view.delivery_item_address!!
    val comment = view.delivery_item_comment!!
    val delete = view.delivery_delete_button!!
}