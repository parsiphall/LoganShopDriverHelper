package com.parsiphal.loganshopdriverhelper.fragments


import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gmail.parsiphall.importexportdb.ExportDB
import com.gmail.parsiphall.importexportdb.ImportDB
import com.google.android.material.snackbar.Snackbar
import com.parsiphal.loganshopdriverhelper.*

import kotlinx.android.synthetic.main.fragment_maintanance.*
import kotlinx.coroutines.*
import moxy.MvpAppCompatFragment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel

@ExperimentalCoroutinesApi
class MaintananceFragment : MvpAppCompatFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maintanance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        maint_export.setOnClickListener {
            GlobalScope.launch { ExportDB.export(context!!, DB_NAME) }
        }
        maint_import.setOnClickListener {
            GlobalScope.launch { ImportDB.import(context!!, DB_NAME) }
        }
        maint_old_add_system.setOnClickListener {
            prefs.addSystem = 0
            Snackbar.make(view, getString(R.string.oldAddSystemChosen), Snackbar.LENGTH_SHORT)
                .show()
        }
        maint_new_add_system.setOnClickListener {
            prefs.addSystem = 1
            Snackbar.make(view, getString(R.string.newAddSystemChosen), Snackbar.LENGTH_SHORT)
                .show()
        }
        maint_driver.setOnClickListener {
            prefs.status = 0
            Snackbar.make(view, getString(R.string.driverStatusChosen), Snackbar.LENGTH_SHORT)
                .show()
        }
        maint_newbie.setOnClickListener {
            prefs.status = 1
            Snackbar.make(view, getString(R.string.newbieStatusChosen), Snackbar.LENGTH_SHORT)
                .show()
        }
        maint_deliveryOld.setOnClickListener {
            prefs.deliveryView = 0
            Snackbar.make(view, getString(R.string.oldDeliveryListChosen), Snackbar.LENGTH_SHORT)
                .show()
        }
        maint_deliveryNew.setOnClickListener {
            prefs.deliveryView = 1
            Snackbar.make(view, getString(R.string.newDeliveryListChosen), Snackbar.LENGTH_SHORT)
                .show()
        }
        maint_old_shift_system.setOnClickListener {
            prefs.shiftSystem = 0
            Snackbar.make(view, getString(R.string.oldShiftSystemChosen), Snackbar.LENGTH_SHORT)
                .show()
        }
        maint_new_shift_system.setOnClickListener {
            prefs.shiftSystem = 1
            Snackbar.make(view, getString(R.string.newShiftSystemChosen), Snackbar.LENGTH_SHORT)
                .show()
        }
    }
}
