package com.parsiphal.loganshopdriverhelper.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import com.parsiphal.loganshopdriverhelper.R
import com.parsiphal.loganshopdriverhelper.data.Cars
import com.parsiphal.loganshopdriverhelper.databinding.FragmentShiftBinding
import com.parsiphal.loganshopdriverhelper.prefs
import moxy.MvpAppCompatFragment
import java.lang.Exception
import java.util.*

class ShiftFragment : MvpAppCompatFragment() {

    private var _binding: FragmentShiftBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShiftBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.shiftStatusTextView.text = when (prefs.status) {
            0 -> resources.getString(R.string.driver)
            else -> resources.getString(R.string.newbie)
        }
        getData()
        binding.shiftCarSpinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    Cars.Largus.i -> {
                        binding.shiftFuelMorningSpinner6.visibility = View.GONE
                        binding.shiftFuelEveningSpinner6.visibility = View.GONE
                        binding.shiftFuelMorningSpinner8.visibility = View.GONE
                        binding.shiftFuelEveningSpinner8.visibility = View.GONE
                        binding.shiftFuelMorningSpinner9.visibility = View.VISIBLE
                        binding.shiftFuelEveningSpinner9.visibility = View.VISIBLE
                    }
                    Cars.Sandero.i -> {
                        binding.shiftFuelMorningSpinner6.visibility = View.GONE
                        binding.shiftFuelEveningSpinner6.visibility = View.GONE
                        binding.shiftFuelMorningSpinner8.visibility = View.GONE
                        binding.shiftFuelEveningSpinner8.visibility = View.GONE
                        binding.shiftFuelMorningSpinner9.visibility = View.VISIBLE
                        binding.shiftFuelEveningSpinner9.visibility = View.VISIBLE
                    }
                    Cars.XRay.i -> {
                        binding.shiftFuelMorningSpinner6.visibility = View.GONE
                        binding.shiftFuelEveningSpinner6.visibility = View.GONE
                        binding.shiftFuelMorningSpinner8.visibility = View.VISIBLE
                        binding.shiftFuelEveningSpinner8.visibility = View.VISIBLE
                        binding.shiftFuelMorningSpinner9.visibility = View.GONE
                        binding.shiftFuelEveningSpinner9.visibility = View.GONE
                    }
                    Cars.LargusNew.i -> {
                        binding.shiftFuelMorningSpinner6.visibility = View.GONE
                        binding.shiftFuelEveningSpinner6.visibility = View.GONE
                        binding.shiftFuelMorningSpinner8.visibility = View.GONE
                        binding.shiftFuelEveningSpinner8.visibility = View.GONE
                        binding.shiftFuelMorningSpinner9.visibility = View.VISIBLE
                        binding.shiftFuelEveningSpinner9.visibility = View.VISIBLE
                    }
                    Cars.VestaSW.i -> {
                        binding.shiftFuelMorningSpinner6.visibility = View.VISIBLE
                        binding.shiftFuelEveningSpinner6.visibility = View.VISIBLE
                        binding.shiftFuelMorningSpinner8.visibility = View.GONE
                        binding.shiftFuelEveningSpinner8.visibility = View.GONE
                        binding.shiftFuelMorningSpinner9.visibility = View.GONE
                        binding.shiftFuelEveningSpinner9.visibility = View.GONE
                    }
                }
            }
        })
        binding.shiftWrite.setOnClickListener {
            try {
                prefs.family = binding.shiftFamilyEditText.text.toString()
                prefs.bonus = binding.shiftBonusCheckbox.isChecked
                prefs.date = binding.shiftDate.text.toString()
                prefs.region = binding.shiftRegionSpinner.selectedItem.toString()
                prefs.regionPosition = binding.shiftRegionSpinner.selectedItemPosition
                prefs.car = binding.shiftCarSpinner.selectedItem.toString()
                prefs.carPosition = binding.shiftCarSpinner.selectedItemPosition
                prefs.morningODO = binding.shiftOdoMorning.text.toString().toInt()
                prefs.eveningODO = binding.shiftOdoEvening.text.toString().toInt()
                when (binding.shiftCarSpinner.selectedItemPosition) {
                    Cars.XRay.i -> {
                        prefs.morningFuel = (binding.shiftFuelMorningSpinner8.selectedItemPosition + 1)
                        prefs.eveningFuel = (binding.shiftFuelEveningSpinner8.selectedItemPosition + 1)
                    }
                    Cars.VestaSW.i -> {
                        prefs.morningFuel = (binding.shiftFuelMorningSpinner6.selectedItemPosition + 1)
                        prefs.eveningFuel = (binding.shiftFuelEveningSpinner6.selectedItemPosition + 1)
                    }
                    else -> {
                        prefs.morningFuel = (binding.shiftFuelMorningSpinner9.selectedItemPosition + 1)
                        prefs.eveningFuel = (binding.shiftFuelEveningSpinner9.selectedItemPosition + 1)
                    }
                }
                when (binding.shiftCarSpinner.selectedItemPosition) {
                    Cars.Largus.i -> {
                        prefs.lastFuelLargus =
                            (binding.shiftFuelEveningSpinner9.selectedItemPosition + 1)
                        prefs.lastOdoLargus = binding.shiftOdoEvening.text.toString().toInt()
                    }
                    Cars.Sandero.i -> {
                        prefs.lastFuelSandero =
                            (binding.shiftFuelEveningSpinner9.selectedItemPosition + 1)
                        prefs.lastOdoSandero = binding.shiftOdoEvening.text.toString().toInt()
                    }
                    Cars.XRay.i -> {
                        prefs.lastFuelXRay = (binding.shiftFuelEveningSpinner8.selectedItemPosition + 1)
                        prefs.lastOdoXRay = binding.shiftOdoEvening.text.toString().toInt()
                    }
                    Cars.LargusNew.i -> {
                        prefs.lastFuelLargusNew =
                            (binding.shiftFuelEveningSpinner9.selectedItemPosition + 1)
                        prefs.lastOdoLargusNew = binding.shiftOdoEvening.text.toString().toInt()
                    }
                    Cars.VestaSW.i -> {
                        prefs.lastFuelVestaSW =
                            (binding.shiftFuelEveningSpinner9.selectedItemPosition + 1)
                        prefs.lastOdoVestaSW = binding.shiftOdoEvening.text.toString().toInt()
                    }
                }
                Snackbar.make(view, getString(R.string.recorded), Snackbar.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view, getString(R.string.wrongData), Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.shiftDate.setOnClickListener {
            datePickerDialog(it)
        }
        binding.shiftNewButton.setOnClickListener {
            try {
                when (binding.shiftCarSpinner.selectedItemPosition) {
                    Cars.Largus.i -> {
                        binding.shiftOdoMorning.setText(prefs.lastOdoLargus!!.toString())
                        binding.shiftOdoEvening.setText("0")
                        binding.shiftFuelMorningSpinner9.setSelection(prefs.lastFuelLargus!!.minus(1))
                    }
                    Cars.Sandero.i -> {
                        binding.shiftOdoMorning.setText(prefs.lastOdoSandero!!.toString())
                        binding.shiftOdoEvening.setText("0")
                        binding.shiftFuelMorningSpinner9.setSelection(prefs.lastFuelSandero!!.minus(1))
                    }
                    Cars.XRay.i -> {
                        binding.shiftOdoMorning.setText(prefs.lastOdoXRay!!.toString())
                        binding.shiftOdoEvening.setText("0")
                        binding.shiftFuelMorningSpinner8.setSelection(prefs.lastFuelXRay!!.minus(1))
                    }
                    Cars.LargusNew.i -> {
                        binding.shiftOdoMorning.setText(prefs.lastOdoLargusNew!!.toString())
                        binding.shiftOdoEvening.setText("0")
                        binding.shiftFuelMorningSpinner9.setSelection(prefs.lastFuelLargusNew!!.minus(1))
                    }
                    Cars.VestaSW.i -> {
                        binding.shiftOdoMorning.setText(prefs.lastOdoVestaSW!!.toString())
                        binding.shiftOdoEvening.setText("0")
                        binding.shiftFuelMorningSpinner9.setSelection(prefs.lastFuelVestaSW!!.minus(1))
                    }
                }

                Snackbar.make(view, getString(R.string.dontForgetDate), Snackbar.LENGTH_LONG).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view, getString(R.string.wrongData), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getData() {
        try {
            binding.shiftFamilyEditText.setText(prefs.family)
            binding.shiftBonusCheckbox.isChecked = prefs.bonus
            binding.shiftDate.text = prefs.date
            binding.shiftRegionSpinner.setSelection(prefs.regionPosition!!)
            binding.shiftCarSpinner.setSelection(prefs.carPosition!!)
            binding.shiftOdoMorning.setText(prefs.morningODO!!.toString())
            binding.shiftOdoEvening.setText(prefs.eveningODO!!.toString())
            when (binding.shiftCarSpinner.selectedItemPosition) {
                Cars.XRay.i -> {
                    binding.shiftFuelMorningSpinner8.setSelection(prefs.morningFuel!!.minus(1))
                    binding.shiftFuelEveningSpinner8.setSelection(prefs.eveningFuel!!.minus(1))
                }
                Cars.VestaSW.i -> {
                    binding.shiftFuelMorningSpinner6.setSelection(prefs.morningFuel!!.minus(1))
                    binding.shiftFuelEveningSpinner6.setSelection(prefs.eveningFuel!!.minus(1))
                }
                else -> {
                    binding.shiftFuelMorningSpinner9.setSelection(prefs.morningFuel!!.minus(1))
                    binding.shiftFuelEveningSpinner9.setSelection(prefs.eveningFuel!!.minus(1))
                }
            }
        } catch (e: Exception) {
            binding.shiftFamilyEditText.setText("")
            binding.shiftBonusCheckbox.isChecked = false
            binding.shiftDate.text = ""
            binding.shiftRegionSpinner.setSelection(0)
            binding.shiftCarSpinner.setSelection(0)
            binding.shiftOdoMorning.setText("")
            binding.shiftOdoEvening.setText("")
            binding.shiftFuelMorningSpinner8.setSelection(0)
            binding.shiftFuelEveningSpinner8.setSelection(0)
            binding.shiftFuelMorningSpinner9.setSelection(0)
            binding.shiftFuelEveningSpinner9.setSelection(0)
        }
    }

    private fun dateListener(v: View): DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            var myMonth = (month + 1).toString()
            var myDay = dayOfMonth.toString()
            if (month < 9) {
                myMonth = "0$myMonth"
            }
            if (dayOfMonth < 10) {
                myDay = "0$myDay"
            }
            val date = "$myDay/$myMonth/$year"
            (v as TextView).text = date
        }

    private fun datePickerDialog(v: View) {
        val cal = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val dayOfMonth: Int = cal.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(
            context!!,
            dateListener(v),
            year,
            month,
            dayOfMonth
        ).show()
    }
}
