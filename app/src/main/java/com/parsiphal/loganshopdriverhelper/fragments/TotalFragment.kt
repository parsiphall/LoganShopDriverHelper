package com.parsiphal.loganshopdriverhelper.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment

import com.parsiphal.loganshopdriverhelper.R
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import kotlinx.android.synthetic.main.fragment_total.*

class TotalFragment : MvpAppCompatFragment() {

    private lateinit var callBackActivity: MainView

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callBackActivity = context as MainView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_total, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        total_day_button.setOnClickListener {
            callBackActivity.fragmentPlace(TotalDayFragment())
        }
        total_month_button.setOnClickListener {
            callBackActivity.fragmentPlace(TotalMonthFragment())
        }
    }
}
