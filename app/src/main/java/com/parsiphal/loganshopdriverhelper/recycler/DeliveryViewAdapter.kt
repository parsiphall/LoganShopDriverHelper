package com.parsiphal.loganshopdriverhelper.recycler

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parsiphal.loganshopdriverhelper.DB
import com.parsiphal.loganshopdriverhelper.R
import com.parsiphal.loganshopdriverhelper.data.Delivery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.*

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
        if (items[position].workType != 0) {
            holder.deliveryType.visibility = View.GONE
            holder.payType.visibility = View.GONE
            holder.address.visibility = View.GONE
            if (items[position].workType == 1 && items[position].workType == 3) {
                holder.cost.visibility = View.GONE
            }
        }
        holder.deliveryDate.text = items[position].deliveryDate
        holder.workType.text = context.resources.getStringArray(R.array.work_type)[items[position].workType]
        holder.deliveryType.text = context.resources.getStringArray(R.array.delivery_type)[items[position].deliveryType]
        holder.payType.text = context.resources.getStringArray(R.array.payType)[items[position].payType]
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
        DB.getDao().deleteDelivery(items[position])
        items = DB.getDao().getAllDeliveries()
        Collections.reverse(items)
        MainScope().launch {
            notifyDataSetChanged()
        }
    }
}