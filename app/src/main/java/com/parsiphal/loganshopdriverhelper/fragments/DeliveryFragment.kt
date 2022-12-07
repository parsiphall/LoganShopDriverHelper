package com.parsiphal.loganshopdriverhelper.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parsiphal.loganshopdriverhelper.DB
import com.parsiphal.loganshopdriverhelper.data.Delivery
import com.parsiphal.loganshopdriverhelper.data.PayType
import com.parsiphal.loganshopdriverhelper.data.WorkType
import com.parsiphal.loganshopdriverhelper.databinding.FragmentDeliveryBinding
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import com.parsiphal.loganshopdriverhelper.prefs
import com.parsiphal.loganshopdriverhelper.recycler.DeliveryViewAdapter
import com.parsiphal.loganshopdriverhelper.recycler.DeliveryViewAdapterNew
import com.parsiphal.loganshopdriverhelper.recycler.OnItemClickListener
import com.parsiphal.loganshopdriverhelper.recycler.addOnItemClickListener
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
    private lateinit var adapterNew: DeliveryViewAdapterNew
    private var searchDate: String = ""
    private var cashSum = 0
    private var _binding: FragmentDeliveryBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBackActivity = context as MainView
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeliveryBinding.inflate(inflater, container, false)
        adapter = DeliveryViewAdapter(items, context!!)
        adapterNew = DeliveryViewAdapterNew(items, context!!)
        binding.deliveryRecycler.layoutManager = LinearLayoutManager(context)
        binding.deliveryRecycler.adapter = if (prefs.deliveryView == 0) {
            adapter
        } else {
            adapterNew
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDate()
        placeData(searchDate)
        binding.deliveryFab.setOnClickListener {
            if (prefs.addSystem == 0) {
                callBackActivity.fragmentPlace(NewDeliveryFragment())
            } else {
                callBackActivity.fragmentPlace(NewDeliveryAddFragment())
            }
        }
//        delivery_fab.setOnLongClickListener {
//            callBackActivity.fragmentPlace(MaintananceFragment())
//            return@setOnLongClickListener true
//        }
        binding.calendarFab.setOnClickListener {
            datePickerDialog()
        }
        binding.deliveryRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                val bundle = Bundle()
                bundle.putSerializable("ITEM", items[position])
                callBackActivity.fragmentPlaceWithArgs(NewDeliveryFragment(), bundle)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun placeData(date: String) {
        val data = GlobalScope.async { getData(date) }
        MainScope().launch {
            data.await()
            if (items.isEmpty()) {
                binding.noDataTextView?.visibility = View.VISIBLE
                cashSum = 0
            }
            binding.deliveryCashSum?.text = cashSum.toString()
            if (prefs.deliveryView == 0) {
                adapter.dataChanged(items)
            } else {
                adapterNew.dataChanged(items)
            }
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
        binding.dateTextView.text = searchDate
    }

    override fun onResume() {
        super.onResume()
        binding.deliveryRecycler.adapter!!.notifyDataSetChanged()
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
            binding.dateTextView.text = searchDate
            placeData(searchDate)
        }

    private fun datePickerDialog() {
        val cal = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val dayOfMonth: Int = cal.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(
            context!!,
            dateListener(),
            year,
            month,
            dayOfMonth
        ).show()
    }
}
