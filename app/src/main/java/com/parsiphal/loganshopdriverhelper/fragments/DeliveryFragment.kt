package com.parsiphal.loganshopdriverhelper.fragments

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.parsiphal.loganshopdriverhelper.DB

import com.parsiphal.loganshopdriverhelper.R
import com.parsiphal.loganshopdriverhelper.data.Delivery
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import com.parsiphal.loganshopdriverhelper.recycler.DeliveryViewAdapter
import kotlinx.android.synthetic.main.fragment_delivery.*
import kotlinx.android.synthetic.main.fragment_delivery.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

@ExperimentalCoroutinesApi
class DeliveryFragment : MvpAppCompatFragment() {

    private lateinit var callBackActivity: MainView
    private var items: List<Delivery> = ArrayList()
    private lateinit var adapter: DeliveryViewAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callBackActivity = context as MainView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_delivery, container, false)
        adapter = DeliveryViewAdapter(items, context!!)
        root.delivery_recycler.layoutManager = LinearLayoutManager(context)
        root.delivery_recycler.adapter = adapter
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        delivery_fab.setOnClickListener {
            callBackActivity.fragmentPlace(NewDeliveryFragment())
        }
    }

    private fun getData() = GlobalScope.launch {
        items = DB.getDao().getAllDeliveries()
        Collections.reverse(items)
        MainScope().launch {
            adapter.dataChanged(items)
        }
    }

    override fun onResume() {
        super.onResume()
        delivery_recycler.adapter!!.notifyDataSetChanged()
    }
}
