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
import com.parsiphal.loganshopdriverhelper.data.Total
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import com.parsiphal.loganshopdriverhelper.recycler.OnItemClickListener
import com.parsiphal.loganshopdriverhelper.recycler.TotalViewAdapter
import com.parsiphal.loganshopdriverhelper.recycler.addOnItemClickListener
import kotlinx.android.synthetic.main.fragment_total.*
import kotlinx.android.synthetic.main.fragment_total.view.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

@ExperimentalCoroutinesApi
class TotalFragment : MvpAppCompatFragment() {

    private lateinit var callBackActivity: MainView
    private var items: List<Total> = ArrayList()
    private lateinit var adapter: TotalViewAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callBackActivity = context as MainView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_total, container, false)
        adapter = TotalViewAdapter(items, context!!)
        root.total_recycler.layoutManager = LinearLayoutManager(context)
        root.total_recycler.adapter = adapter
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = GlobalScope.async {
            items = DB.getDao().getAllTotals()
            Collections.reverse(items)
        }
        MainScope().launch {
            data.await()
            adapter.dataChanged(items)
        }
        total_recycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                val bundle = Bundle()
                bundle.putSerializable("ITEM", items[position])
                callBackActivity.fragmentPlaceWithArgs(
                    if (items[position].dayOrMonth == 0) {
                        TotalDayFragment()
                    } else {
                        TotalMonthFragment()
                    }, bundle
                )
            }
        })
        total_day_button.setOnClickListener {
            callBackActivity.fragmentPlace(TotalDayFragment())
        }
        total_month_button.setOnClickListener {
            callBackActivity.fragmentPlace(TotalMonthFragment())
        }
    }
}
