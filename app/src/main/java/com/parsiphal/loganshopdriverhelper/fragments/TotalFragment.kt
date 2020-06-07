package com.parsiphal.loganshopdriverhelper.fragments

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import moxy.MvpAppCompatFragment
import java.util.Collections.reverse
import kotlin.collections.ArrayList

@ExperimentalCoroutinesApi
class TotalFragment : MvpAppCompatFragment() {

    private lateinit var callBackActivity: MainView
    private var items: List<Total> = ArrayList()
    private lateinit var adapter: TotalViewAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBackActivity = context as MainView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_total, container, false)
        adapter = TotalViewAdapter(items, context!!)
        root.total_recycler.layoutManager =
            LinearLayoutManager(context)
        root.total_recycler.adapter = adapter
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = GlobalScope.async {
            items = DB.getDao().getAllTotals()
            reverse(items)
        }
        MainScope().launch {
            data.await()
            adapter.dataChanged(items)
        }
        total_recycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                val bundle = Bundle()
                bundle.putSerializable("ITEM", items[position])
                callBackActivity.showAd()
                callBackActivity.fragmentPlaceWithArgs(
                    when (items[position].dayOrMonth) {
                        0 -> TotalDayFragment()
                        1 -> TotalMonthFragment()
                        else -> TotalYearFragment()
                    }, bundle
                )
                callBackActivity.prepareAd()
            }
        })
        total_day_button.setOnClickListener {
            callBackActivity.showAd()
            callBackActivity.fragmentPlace(TotalDayFragment())
            callBackActivity.prepareAd()
        }
        total_month_button.setOnClickListener {
            callBackActivity.showAd()
            callBackActivity.fragmentPlace(TotalMonthFragment())
            callBackActivity.prepareAd()
        }
//        total_month_button.setOnLongClickListener {
//            callBackActivity.showAd()
//            callBackActivity.fragmentPlace((TotalYearFragment()))
//            callBackActivity.prepareAd()
//            return@setOnLongClickListener true
//        }
    }
}
