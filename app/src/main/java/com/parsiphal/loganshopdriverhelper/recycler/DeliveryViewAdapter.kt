package com.parsiphal.loganshopdriverhelper.recycler

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.parsiphal.loganshopdriverhelper.DB
import com.parsiphal.loganshopdriverhelper.R
import com.parsiphal.loganshopdriverhelper.data.Delivery
import com.parsiphal.loganshopdriverhelper.fragments.DeliveryFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.Collections.reverse

@ExperimentalCoroutinesApi
class DeliveryViewAdapter(
    private var items: List<Delivery>,
    private val context: Context
) : RecyclerView.Adapter<DeliveryViewHolder>() {

    private val ad = AlertDialog.Builder(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DeliveryViewHolder(
        LayoutInflater.from(context)
            .inflate(R.layout.delivery_item, parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.deliveryDate.text = items[position].deliveryDate
        holder.workType.text =
            context.resources.getStringArray(R.array.work_type)[items[position].workType]
        holder.deliveryType.text = if (items[position].workType == 5) {
            context.resources.getStringArray(R.array.salary_type)[items[position].deliveryType]
        } else {
            context.resources.getStringArray(R.array.delivery_type)[items[position].deliveryType]
        }
        holder.payType.text =
            context.resources.getStringArray(R.array.payType)[items[position].payType]
        holder.address.text = items[position].address
        holder.cost.text = items[position].cost.toString()
        holder.comment.text = items[position].comment
        holder.delete.setOnClickListener {
            ad.setTitle(context.resources.getStringArray(R.array.work_type)[items[position].workType])
            ad.setMessage(context.getString(R.string.adMessage))
            val btn1 = context.getString(R.string.adBtn1)
            val btn2 = context.getString(R.string.adBtn2)
            ad.setPositiveButton(btn1) { _, _ ->
                delete(position)
            }
            ad.setNegativeButton(btn2) { dialog, _ ->
                dialog.cancel()
            }
            ad.show()
        }
    }

    fun dataChanged(newItems: List<Delivery>) {
        items = newItems
        notifyDataSetChanged()
    }

    private fun delete(position: Int) = GlobalScope.launch {
        val date = items[position].deliveryDate
        DB.getDao().deleteDelivery(items[position])
        items = DB.getDao().getDeliveriesByDate(date)
        reverse(items)
        MainScope().launch {
            notifyDataSetChanged()
        }
    }
}