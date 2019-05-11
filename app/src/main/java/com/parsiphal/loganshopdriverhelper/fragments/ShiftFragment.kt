package com.parsiphal.loganshopdriverhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment

import com.parsiphal.loganshopdriverhelper.R
import kotlinx.android.synthetic.main.fragment_shift.*

class ShiftFragment : MvpAppCompatFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shift, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shift_bottom_bar.selectedItemId = R.id.shift_close_shift
    }
}
