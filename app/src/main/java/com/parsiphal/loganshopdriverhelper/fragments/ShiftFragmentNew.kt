package com.parsiphal.loganshopdriverhelper.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import com.google.android.material.snackbar.Snackbar
import com.parsiphal.loganshopdriverhelper.R
import com.parsiphal.loganshopdriverhelper.data.Cars
import com.parsiphal.loganshopdriverhelper.data.Districts
import com.parsiphal.loganshopdriverhelper.databinding.FragmentShiftNewBinding
import com.parsiphal.loganshopdriverhelper.prefs
import moxy.MvpAppCompatFragment
import java.lang.Exception

class ShiftFragmentNew : MvpAppCompatFragment() {

    private var ifMorning = true
    private var _binding: FragmentShiftNewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShiftNewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        familyTextListener()
        startShiftListener()
        enterDateListener()
        enterCarListener()
        enterDistrictListener()
        closeShiftListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun familyTextListener() {
        binding.shiftNFamilyTextView.setOnClickListener {
            hideMainViews()
            binding.shiftNEnterFamily.visibility = View.VISIBLE
            binding.shiftNEnterFamilyWriteButton.setOnClickListener {
                prefs.family = binding.shiftNEnterFamilyEditText.text.toString()
                binding.shiftNFamilyTextView.text = binding.shiftNEnterFamilyEditText.text.toString()
                binding.shiftNEnterFamily.visibility = View.GONE
                showMainViews()
                val imm =
                    context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
            }
        }
    }

    private fun startShiftListener() {
        binding.shiftNStartShiftButton.setOnClickListener {
            ifMorning = true
            hideMainViews()
            binding.shiftNEnterDate.visibility = View.VISIBLE
        }
    }

    private fun enterDateListener() {
        binding.shiftNEnterDateNextButton.setOnClickListener {
            val day = if (binding.shiftNEnterDateDatePicker.dayOfMonth < 10) {
                "0${binding.shiftNEnterDateDatePicker.dayOfMonth}"
            } else {
                "${binding.shiftNEnterDateDatePicker.dayOfMonth}"
            }
            val month = if (binding.shiftNEnterDateDatePicker.month + 1 < 10) {
                "0${binding.shiftNEnterDateDatePicker.month + 1}"
            } else {
                "${binding.shiftNEnterDateDatePicker.month + 1}"
            }
            val year = binding.shiftNEnterDateDatePicker.year.toString()
            val date = "$day/${month}/${year}"
            prefs.date = date
            binding.shiftNDateTextView.text = date
            binding.shiftNEnterDate.visibility = View.GONE
            binding.shiftNEnterCar.visibility = View.VISIBLE
        }
    }

    private fun enterCarListener() {
        binding.shiftNEnterCarLargusButton.setOnClickListener {
            prefs.car = Cars.Largus.id
            prefs.carPosition = Cars.Largus.i
            binding.shiftNCarTextView.text = Cars.Largus.name
            binding.shiftNEnterCar.visibility = View.GONE
            binding.shiftNEnterDistrict.visibility = View.VISIBLE
        }
        binding.shiftNEnterCarSanderoButton.setOnClickListener {
            prefs.car = Cars.Sandero.id
            prefs.carPosition = Cars.Sandero.i
            binding.shiftNCarTextView.text = Cars.Sandero.name
            binding.shiftNEnterCar.visibility = View.GONE
            binding.shiftNEnterDistrict.visibility = View.VISIBLE
        }
        binding.shiftNEnterCarXrayButton.setOnClickListener {
            prefs.car = Cars.XRay.id
            prefs.carPosition = Cars.XRay.i
            binding.shiftNCarTextView.text = Cars.XRay.id
            binding.shiftNEnterCar.visibility = View.GONE
            binding.shiftNEnterDistrict.visibility = View.VISIBLE
        }
        binding.shiftNEnterCarLargusNewButton.setOnClickListener {
            prefs.car = Cars.LargusNew.id
            prefs.carPosition = Cars.LargusNew.i
            binding.shiftNCarTextView.text = Cars.LargusNew.name
            binding.shiftNEnterCar.visibility = View.GONE
            binding.shiftNEnterDistrict.visibility = View.VISIBLE
        }
        binding.shiftNEnterCarVestaSWButton.setOnClickListener {
            prefs.car = Cars.VestaSW.id
            prefs.carPosition = Cars.VestaSW.i
            binding.shiftNCarTextView.text = Cars.VestaSW.name
            binding.shiftNEnterCar.visibility = View.GONE
            binding.shiftNEnterDistrict.visibility = View.VISIBLE
        }
    }

    private fun enterDistrictListener() {
        binding.shiftNEnterDistrictNorthButton.setOnClickListener {
            prefs.region = Districts.North.ruName
            binding.shiftNDistrictTextView.text = Districts.North.ruName
            binding.shiftNEnterDistrict.visibility = View.GONE
            odoAndFuelListener()
        }
        binding.shiftNEnterDistrictCenterButton.setOnClickListener {
            prefs.region = Districts.Center.ruName
            binding.shiftNDistrictTextView.text = Districts.Center.ruName
            binding.shiftNEnterDistrict.visibility = View.GONE
            odoAndFuelListener()
        }
        binding.shiftNEnterDistrictSouthButton.setOnClickListener {
            prefs.region = Districts.South.ruName
            binding.shiftNDistrictTextView.text = Districts.Center.ruName
            binding.shiftNEnterDistrict.visibility = View.GONE
            odoAndFuelListener()
        }
    }

    private fun odoAndFuelListener() {
        binding.shiftNEnterOdoAndFuel.visibility = View.VISIBLE
        when (binding.shiftNCarTextView.text) {
            Cars.XRay.id -> {
                binding.shiftNFuelSeekbar6.visibility = View.GONE
                binding.shiftNFuelSeekbar9.visibility = View.GONE
            }
            Cars.VestaSW.id -> {
                binding.shiftNFuelSeekbar8.visibility = View.GONE
                binding.shiftNFuelSeekbar9.visibility = View.GONE
            }
            else -> {
                binding.shiftNFuelSeekbar6.visibility = View.GONE
                binding.shiftNFuelSeekbar8.visibility = View.GONE
            }
        }
        binding.shiftNFuelSeekbar6.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.shiftNFuelTextView.text = binding.shiftNFuelSeekbar6.progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        binding.shiftNFuelSeekbar8.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.shiftNFuelTextView.text = binding.shiftNFuelSeekbar8.progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        binding.shiftNFuelSeekbar9.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.shiftNFuelTextView.text = binding.shiftNFuelSeekbar9.progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        binding.shiftNEnterOdoAndFuelWriteButton.setOnClickListener {
            if (ifMorning) {
                prefs.morningODO = binding.shiftNOdoEditText.text.toString().toInt()
                binding.shiftNOdoMorningTextView.text = binding.shiftNOdoEditText.text.toString()
                when (binding.shiftNCarTextView.text) {
                    Cars.XRay.id -> {
                        prefs.morningFuel = binding.shiftNFuelSeekbar8.progress
                        binding.shiftNFuelMorningTextView.text =
                            binding.shiftNFuelSeekbar8.progress.toString()
                    }
                    Cars.VestaSW.id -> {
                        prefs.morningFuel = binding.shiftNFuelSeekbar6.progress
                        binding.shiftNFuelMorningTextView.text =
                            binding.shiftNFuelSeekbar6.progress.toString()
                    }
                    else -> {
                        prefs.morningFuel = binding.shiftNFuelSeekbar9.progress
                        binding.shiftNFuelMorningTextView.text =
                            binding.shiftNFuelSeekbar9.progress.toString()
                    }
                }
            } else {
                prefs.eveningODO = binding.shiftNOdoEditText.text.toString().toInt()
                binding.shiftNOdoEveningTextView.text = binding.shiftNOdoEditText.text.toString()
                when (binding.shiftNCarTextView.text) {
                    Cars.XRay.id -> {
                        prefs.eveningFuel = binding.shiftNFuelSeekbar8.progress
                        binding.shiftNFuelEveningTextView.text =
                            binding.shiftNFuelSeekbar8.progress.toString()
                    }
                    Cars.VestaSW.id -> {
                        prefs.morningFuel = binding.shiftNFuelSeekbar6.progress
                        binding.shiftNFuelMorningTextView.text =
                            binding.shiftNFuelSeekbar6.progress.toString()
                    }
                    else -> {
                        prefs.eveningFuel = binding.shiftNFuelSeekbar9.progress
                        binding.shiftNFuelEveningTextView.text =
                            binding.shiftNFuelSeekbar9.progress.toString()
                    }
                }
            }
            binding.shiftNEnterOdoAndFuel.visibility = View.GONE
            val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
            showMainViews()
        }
    }

    private fun closeShiftListener() {
        binding.shiftNFinishShiftButton.setOnClickListener {
            ifMorning = false
            binding.shiftNFuelSeekbar6.progress = 0
            binding.shiftNFuelSeekbar8.progress = 0
            binding.shiftNFuelSeekbar9.progress = 0
            binding.shiftNOdoEditText.setText("")
            hideMainViews()
            odoAndFuelListener()
        }
    }

    private fun getData() {
        try {
            binding.shiftNStatusTextView.text = when (prefs.status) {
                0 -> resources.getString(R.string.driver)
                else -> resources.getString(R.string.newbie)
            }
            binding.shiftNFamilyTextView.text = prefs.family
            binding.shiftNDateTextView.text = prefs.date
            binding.shiftNCarTextView.text = prefs.car
            binding.shiftNDistrictTextView.text = prefs.region
            binding.shiftNOdoMorningTextView.text = prefs.morningODO.toString()
            binding.shiftNFuelMorningTextView.text = prefs.morningFuel.toString()
            binding.shiftNOdoEveningTextView.text = prefs.eveningODO.toString()
            binding.shiftNFuelEveningTextView.text = prefs.eveningFuel.toString()
        } catch (e: Exception) {
            Snackbar.make(view!!, getString(R.string.shiftError), Snackbar.LENGTH_LONG).show()
        }
    }

    private fun hideMainViews() {
        binding.shiftNFamily.visibility = View.GONE
        binding.shiftNDateAndCar.visibility = View.GONE
        binding.shiftNMorningDivider.visibility = View.GONE
        binding.shiftNMorningOdo.visibility = View.GONE
        binding.shiftNMorningFuel.visibility = View.GONE
        binding.shiftNEveningDivider.visibility = View.GONE
        binding.shiftNEveningOdo.visibility = View.GONE
        binding.shiftNEveningFuel.visibility = View.GONE
        binding.shiftNButtons.visibility = View.GONE
    }

    private fun showMainViews() {
        binding.shiftNFamily.visibility = View.VISIBLE
        binding.shiftNDateAndCar.visibility = View.VISIBLE
        binding.shiftNMorningDivider.visibility = View.VISIBLE
        binding.shiftNMorningOdo.visibility = View.VISIBLE
        binding.shiftNMorningFuel.visibility = View.VISIBLE
        binding.shiftNEveningDivider.visibility = View.VISIBLE
        binding.shiftNEveningOdo.visibility = View.VISIBLE
        binding.shiftNEveningFuel.visibility = View.VISIBLE
        binding.shiftNButtons.visibility = View.VISIBLE
    }
}
