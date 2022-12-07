package com.parsiphal.loganshopdriverhelper.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.parsiphall.importexportdb.ExportDB
import com.gmail.parsiphall.importexportdb.ImportDB
import com.google.android.material.snackbar.Snackbar
import com.parsiphal.loganshopdriverhelper.*
import com.parsiphal.loganshopdriverhelper.databinding.FragmentMaintananceBinding
import kotlinx.coroutines.*
import moxy.MvpAppCompatFragment

@ExperimentalCoroutinesApi
class MaintananceFragment : MvpAppCompatFragment() {

    private var _binding: FragmentMaintananceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMaintananceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.maintExport.setOnClickListener {
            GlobalScope.launch { ExportDB.export(context!!, DB_NAME) }
        }
        binding.maintImport.setOnClickListener {
            GlobalScope.launch { ImportDB.import(context!!, DB_NAME) }
        }
        binding.maintOldAddSystem.setOnClickListener {
            prefs.addSystem = 0
            Snackbar.make(view, getString(R.string.oldAddSystemChosen), Snackbar.LENGTH_SHORT)
                .show()
        }
        binding.maintNewAddSystem.setOnClickListener {
            prefs.addSystem = 1
            Snackbar.make(view, getString(R.string.newAddSystemChosen), Snackbar.LENGTH_SHORT)
                .show()
        }
        binding.maintDriver.setOnClickListener {
            prefs.status = 0
            Snackbar.make(view, getString(R.string.driverStatusChosen), Snackbar.LENGTH_SHORT)
                .show()
        }
        binding.maintNewbie.setOnClickListener {
            prefs.status = 1
            Snackbar.make(view, getString(R.string.newbieStatusChosen), Snackbar.LENGTH_SHORT)
                .show()
        }
        binding.maintDeliveryOld.setOnClickListener {
            prefs.deliveryView = 0
            Snackbar.make(view, getString(R.string.oldDeliveryListChosen), Snackbar.LENGTH_SHORT)
                .show()
        }
        binding.maintDeliveryNew.setOnClickListener {
            prefs.deliveryView = 1
            Snackbar.make(view, getString(R.string.newDeliveryListChosen), Snackbar.LENGTH_SHORT)
                .show()
        }
        binding.maintOldShiftSystem.setOnClickListener {
            prefs.shiftSystem = 0
            Snackbar.make(view, getString(R.string.oldShiftSystemChosen), Snackbar.LENGTH_SHORT)
                .show()
        }
        binding.maintNewShiftSystem.setOnClickListener {
            prefs.shiftSystem = 1
            Snackbar.make(view, getString(R.string.newShiftSystemChosen), Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
