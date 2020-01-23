package com.parsiphal.loganshopdriverhelper.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parsiphal.loganshopdriverhelper.DB

import com.parsiphal.loganshopdriverhelper.R
import com.parsiphal.loganshopdriverhelper.data.Delivery
import com.parsiphal.loganshopdriverhelper.data.PayType
import com.parsiphal.loganshopdriverhelper.data.WorkType
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import com.parsiphal.loganshopdriverhelper.prefs
import com.parsiphal.loganshopdriverhelper.recycler.DeliveryViewAdapter
import com.parsiphal.loganshopdriverhelper.recycler.OnItemClickListener
import com.parsiphal.loganshopdriverhelper.recycler.addOnItemClickListener
import kotlinx.android.synthetic.main.fragment_delivery.*
import kotlinx.android.synthetic.main.fragment_delivery.view.*
import kotlinx.coroutines.*
import moxy.MvpAppCompatFragment
import java.util.*
import java.util.Collections.reverse
import kotlin.collections.ArrayList

@ExperimentalCoroutinesApi
class DeliveryFragment : MvpAppCompatFragment() {

    private lateinit var callBackActivity: MainView
    private var items: List<Delivery> = ArrayList()
    private lateinit var adapter: DeliveryViewAdapter
    private var searchDate: String = ""
    private var cashSum = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBackActivity = context as MainView
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_delivery, container, false)
        adapter = DeliveryViewAdapter(items, context!!)
        root.delivery_recycler.layoutManager = LinearLayoutManager(context)
        root.delivery_recycler.adapter = adapter
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDate()
        placeData(searchDate)
        delivery_fab.setOnClickListener {
            if (prefs.addSystem == 0) {
                callBackActivity.fragmentPlace(NewDeliveryFragment())
            } else {
                callBackActivity.fragmentPlace(NewDeliveryAddFragment())
            }
        }
        delivery_fab.setOnLongClickListener {
            callBackActivity.fragmentPlace(MaintananceFragment())
            return@setOnLongClickListener true
        }
        calendar_fab.setOnClickListener {
            datePickerDialog()
        }
        delivery_recycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                val bundle = Bundle()
                bundle.putSerializable("ITEM", items[position])
                callBackActivity.fragmentPlaceWithArgs(NewDeliveryFragment(), bundle)
            }
        })
    }

    private fun placeData(date: String) {
        val data = GlobalScope.async { getData(date) }
        MainScope().launch {
            data.await()
            if (items.isEmpty()) {
                no_data_textView?.visibility = View.VISIBLE
                cashSum = 0
            }
            delivery_cashSum?.text = cashSum.toString()
            adapter.dataChanged(items)
        }
    }

    private fun getData(search: String) {
        cashSum = 0
        items = DB.getDao().getDeliveriesByDate(search)
        for (position in items) {
            if (position.workType == WorkType.Delivery.i && position.payType == PayType.Cash.i) {
                cashSum += position.cost
            }
        }
        reverse(items)
    }

    private fun getDate() {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)
        var myMonth = (month + 1).toString()
        var myDay = dayOfMonth.toString()
        if (month < 9) {
            myMonth = "0$myMonth"
        }
        if (dayOfMonth < 10) {
            myDay = "0$myDay"
        }
        searchDate = "$myDay/$myMonth/$year"
        date_textView.text = searchDate
    }

    override fun onResume() {
        super.onResume()
        delivery_recycler.adapter!!.notifyDataSetChanged()
    }

    private fun dateListener(): DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            var myMonth = (month + 1).toString()
            var myDay = dayOfMonth.toString()
            if (month < 9) {
                myMonth = "0$myMonth"
            }
            if (dayOfMonth < 10) {
                myDay = "0$myDay"
            }
            searchDate = "$myDay/$myMonth/$year"
            date_textView.text = searchDate
            placeData(searchDate)
        }

    private fun datePickerDialog() {
        val cal = Calendar.getInstance()
        val year: Int
        val month: Int
        val dayOfMonth: Int
        year = cal.get(Calendar.YEAR)
        month = cal.get(Calendar.MONTH)
        dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(
            context!!,
            dateListener(),
            year,
            month,
            dayOfMonth
        ).show()
    }
}
