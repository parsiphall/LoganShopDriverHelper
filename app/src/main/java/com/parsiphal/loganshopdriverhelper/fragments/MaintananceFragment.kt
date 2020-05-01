package com.parsiphal.loganshopdriverhelper.fragments


import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.parsiphal.loganshopdriverhelper.*
import com.parsiphal.loganshopdriverhelper.data.Dao

import kotlinx.android.synthetic.main.fragment_maintanance.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
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
            export()
        }
        maint_import.setOnClickListener {
            import()
        }
        maint_old_add_system.setOnClickListener {
            prefs.addSystem = 0
            Snackbar.make(view, getString(R.string.oldAddSystemChosen), Snackbar.LENGTH_SHORT).show()
        }
        maint_new_add_system.setOnClickListener {
            prefs.addSystem = 1
            Snackbar.make(view, getString(R.string.newAddSystemChosen), Snackbar.LENGTH_SHORT).show()
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
            Snackbar.make(view, getString(R.string.oldShiftSystemChosen), Snackbar.LENGTH_SHORT).show()
        }
        maint_new_shift_system.setOnClickListener {
            prefs.shiftSystem = 1
            Snackbar.make(view, getString(R.string.newShiftSystemChosen), Snackbar.LENGTH_SHORT).show()
        }
        maint_repair.setOnClickListener {
            repairDB()
            Snackbar.make(view, getString(R.string.repaired), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun repairDB() = GlobalScope.launch {
        val db = DB.getDao().getAllDeliveries()
        for (delivery in db) {
            if (delivery.deliveryDate.length < 10) {
                delivery.deliveryDate = "0${delivery.deliveryDate}"
                DB.getDao().updateDelivery(delivery)
            }
        }
    }

    private fun import() = GlobalScope.launch {
        val sd =
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                    .toString() + "/LSDH/Export"
            )
        var source: FileChannel? = null
        var destination: FileChannel? = null
        val newDB = File(sd, "/backupDB")
        val newSHM = File(sd, "/backupSHM")
        val newWAL = File(sd, "/backupWAL")
        val oldDB = File(context?.getDatabasePath(DB_NAME).toString())
        val oldSHM = File(context?.getDatabasePath(DB_SHM).toString())
        val oldWAL = File(context?.getDatabasePath(DB_WAL).toString())
        try {
            source = FileInputStream(newDB).channel
            destination = FileOutputStream(oldDB).channel
            destination!!.transferFrom(source, 0, source!!.size())
            MainScope().launch {
                Toast.makeText(context, "DB Imported!", Toast.LENGTH_LONG).show()
            }
            source = FileInputStream(newSHM).channel
            destination = FileOutputStream(oldSHM).channel
            destination!!.transferFrom(source, 0, source!!.size())
            MainScope().launch {
                Toast.makeText(context, "SHM Imported!", Toast.LENGTH_LONG).show()
            }
            source = FileInputStream(newWAL).channel
            destination = FileOutputStream(oldWAL).channel
            destination!!.transferFrom(source, 0, source!!.size())
            MainScope().launch {
                Toast.makeText(context, "WAL Imported!", Toast.LENGTH_LONG).show()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            MainScope().launch {
                Toast.makeText(context, "Error!", Toast.LENGTH_LONG).show()
            }
        } finally {
            source?.close()
            destination?.close()
        }
    }

    private fun export() = GlobalScope.launch {
        val sd =
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                    .toString() + "/LSDH/Export"
            )
        var source: FileChannel? = null
        var destination: FileChannel? = null
        val currentDB = File(context?.getDatabasePath(DB_NAME).toString())
        val currentSHM = File(context?.getDatabasePath(DB_SHM).toString())
        val currentWAL = File(context?.getDatabasePath(DB_WAL).toString())
        val backupDB = File(sd.absolutePath + "/backupDB")
        val backupSHM = File(sd.absolutePath + "/backupSHM")
        val backupWAL = File(sd.absolutePath + "/backupWAL")
        if (!sd.exists()) {
            sd.mkdirs()
        }
        if (!backupDB.exists()) {
            backupDB.createNewFile()
            backupSHM.createNewFile()
            backupWAL.createNewFile()
        }
        try {
            source = FileInputStream(currentDB).channel
            destination = FileOutputStream(backupDB).channel
            destination!!.transferFrom(source, 0, source!!.size())
            MainScope().launch {
                Toast.makeText(context, "DB Exported!", Toast.LENGTH_LONG).show()
            }
            source = FileInputStream(currentSHM).channel
            destination = FileOutputStream(backupSHM).channel
            destination!!.transferFrom(source, 0, source!!.size())
            MainScope().launch {
                Toast.makeText(context, "SHM Exported!", Toast.LENGTH_LONG).show()
            }
            source = FileInputStream(currentWAL).channel
            destination = FileOutputStream(backupWAL).channel
            destination!!.transferFrom(source, 0, source!!.size())
            MainScope().launch {
                Toast.makeText(context, "WAL Exported!", Toast.LENGTH_LONG).show()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            MainScope().launch {
                Toast.makeText(context, "Error!", Toast.LENGTH_LONG).show()
            }
        } finally {
            source?.close()
            destination?.close()
        }
    }
}
