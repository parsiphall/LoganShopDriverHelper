package com.parsiphal.loganshopdriverhelper.recycler

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.parsiphal.loganshopdriverhelper.databinding.DeliveryItemNewBinding

class DeliveryViewHolderNew(view: View) : RecyclerView.ViewHolder(view) {
    val binding = DeliveryItemNewBinding.bind(view)
    val workType = binding.deliveryItemWorkType
    val deliveryType = binding.deliveryItemDeliveryType
    val address = binding.deliveryItemAddress
    val comment = binding.deliveryItemComment
    val delete = binding.deliveryDeleteButton
}