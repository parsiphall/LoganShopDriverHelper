package com.parsiphal.loganshopdriverhelper.recycler

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.parsiphal.loganshopdriverhelper.databinding.DeliveryItemBinding

class DeliveryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = DeliveryItemBinding.bind(view)
    val deliveryDate = binding.deliveryItemDate
    val workType = binding.deliveryItemWorkType
    val deliveryType = binding.deliveryItemDeliveryType
    val payType = binding.deliveryItemPayType
    val address = binding.deliveryItemAddress
    val cost = binding.deliveryItemCost
    val comment = binding.deliveryItemComment
    val delete = binding.deliveryDeleteButton
}