package com.parsiphal.loganshopdriverhelper.recycler

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.parsiphal.loganshopdriverhelper.DB
import com.parsiphal.loganshopdriverhelper.R
import com.parsiphal.loganshopdriverhelper.data.Total
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.Collections.reverse

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
            when (items[position].dayOrMonth) {
                0 -> "D"
                1 -> "M"
                else -> "Y"
            }
        holder.dayOrMonth.setBackgroundColor(
            when (items[position].dayOrMonth) {
                0 -> Color.GREEN
                1 -> Color.CYAN
                else -> Color.RED
            }
        )
        holder.date.text = when (items[position].dayOrMonth) {
            0 -> items[position].date
            1 -> "${items[position].date[3]}${items[position].date[4]}" +
                    "${items[position].date[5]}${items[position].date[6]}${items[position].date[7]}" +
                    "${items[position].date[8]}${items[position].date[9]}"
            else -> "${items[position].date[6]}${items[position].date[7]}" +
                    "${items[position].date[8]}${items[position].date[9]}"
        }
        holder.salary.text = items[position].salary.toString()
        holder.distance.text = items[position].deltaODO.toString()
        holder.delete.setOnClickListener {
            ad.setTitle(holder.date.text)
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
        reverse(items)
        MainScope().launch {
            notifyDataSetChanged()
        }
    }
}