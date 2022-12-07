package com.parsiphal.loganshopdriverhelper.fragments

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parsiphal.loganshopdriverhelper.DB
import com.parsiphal.loganshopdriverhelper.data.Total
import com.parsiphal.loganshopdriverhelper.databinding.FragmentTotalBinding
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import com.parsiphal.loganshopdriverhelper.recycler.OnItemClickListener
import com.parsiphal.loganshopdriverhelper.recycler.TotalViewAdapter
import com.parsiphal.loganshopdriverhelper.recycler.addOnItemClickListener
import kotlinx.coroutines.*
import moxy.MvpAppCompatFragment
import java.util.Collections.reverse
import kotlin.collections.ArrayList

@ExperimentalCoroutinesApi
class TotalFragment : MvpAppCompatFragment() {

    private lateinit var callBackActivity: MainView
    private var items: List<Total> = ArrayList()
    private lateinit var adapter: TotalViewAdapter
    private var _binding: FragmentTotalBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBackActivity = context as MainView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTotalBinding.inflate(inflater, container, false)
        adapter = TotalViewAdapter(items, context!!)
        binding.totalRecycler.layoutManager = LinearLayoutManager(context)
        binding.totalRecycler.adapter = adapter
        return binding.root
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
        binding.totalRecycler.addOnItemClickListener(object : OnItemClickListener {
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
        binding.totalDayButton.setOnClickListener {
            callBackActivity.showAd()
            callBackActivity.fragmentPlace(TotalDayFragment())
            callBackActivity.prepareAd()
        }
        binding.totalMonthButton.setOnClickListener {
            callBackActivity.showAd()
            callBackActivity.fragmentPlace(TotalMonthFragment())
            callBackActivity.prepareAd()
        }
//        binding.totalMonthButton.setOnLongClickListener {
//            callBackActivity.showAd()
//            callBackActivity.fragmentPlace((TotalYearFragment()))
//            callBackActivity.prepareAd()
//            return@setOnLongClickListener true
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
