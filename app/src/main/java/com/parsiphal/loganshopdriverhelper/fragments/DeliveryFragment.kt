package com.parsiphal.loganshopdriverhelper.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.parsiphal.loganshopdriverhelper.DB

import com.parsiphal.loganshopdriverhelper.R
import com.parsiphal.loganshopdriverhelper.data.Delivery
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import com.parsiphal.loganshopdriverhelper.recycler.DeliveryViewAdapter
import kotlinx.android.synthetic.main.fragment_delivery.*
import kotlinx.android.synthetic.main.fragment_delivery.view.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

@ExperimentalCoroutinesApi
class DeliveryFragment : MvpAppCompatFragment() {

    private lateinit var callBackActivity: MainView
    private var items: List<Delivery> = ArrayList()
    private lateinit var adapter: DeliveryViewAdapter
    private var searchDate: String = ""

    override fun onAttach(context: Context?) {
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
        setDate()
        delivery_fab.setOnClickListener {
            callBackActivity.fragmentPlace(NewDeliveryFragment())
        }
        delivery_fab.setOnLongClickListener {
            callBackActivity.fragmentPlace(MaintananceFragment())
            return@setOnLongClickListener true
        }
        calendar_fab.setOnClickListener {
            datePickerDialog()
        }
    }

    private fun getData(search: String) {
        val data = GlobalScope.async {
            items = DB.getDao().getDeliveriesByDate(search)
            Collections.reverse(items)
        }
        MainScope().launch {
            data.await()
            adapter.dataChanged(items)
        }
    }

    private fun setDate() {
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
        getData(searchDate)
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
            getData(searchDate)
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
