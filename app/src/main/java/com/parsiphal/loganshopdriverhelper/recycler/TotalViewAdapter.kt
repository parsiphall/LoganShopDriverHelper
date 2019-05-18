package com.parsiphal.loganshopdriverhelper.recycler

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.parsiphal.loganshopdriverhelper.DB
import com.parsiphal.loganshopdriverhelper.R
import com.parsiphal.loganshopdriverhelper.data.Total
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalCoroutinesApi
class TotalViewAdapter(
    private var items: List<Total>,
    private val context: Context
) : RecyclerView.Adapter<TotalViewHolder>() {

    private val ad = AlertDialog.Builder(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TotalViewHolder(
        LayoutInflater.from(context)
            .inflate(R.layout.total_item, parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: TotalViewHolder, position: Int) {
        holder.dayOrMonth.text =
            if (items[position].dayOrMonth == 0) {
                "D"
            } else {
                "M"
            }
        holder.dayOrMonth.setBackgroundColor(
            if (items[position].dayOrMonth == 0) {
                Color.GREEN
            } else {
                Color.CYAN
            }
        )
        holder.date.text = items[position].date
        holder.salary.text = items[position].salary.toString()
        holder.distance.text = (items[position].eveningODO - items[position].morningODO).toString()
        holder.delete.setOnClickListener {
            ad.setTitle(items[position].date)
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

    fun dataChanged(newItems: List<Total>) {
        items = newItems
        notifyDataSetChanged()
    }

    private fun delete(position: Int) = GlobalScope.launch {
        DB.getDao().deleteTotal(items[position])
        items = DB.getDao().getAllTotals()
        Collections.reverse(items)
        MainScope().launch {
            notifyDataSetChanged()
        }
    }
}