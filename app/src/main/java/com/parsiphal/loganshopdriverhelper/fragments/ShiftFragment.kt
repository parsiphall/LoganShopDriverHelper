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
import com.parsiphal.loganshopdriverhelper.prefs
import kotlinx.android.synthetic.main.fragment_shift.*
import moxy.MvpAppCompatFragment
import java.lang.Exception
import java.util.*

class ShiftFragment : MvpAppCompatFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shift, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shift_status_textView.text = when (prefs.status) {
            0 -> resources.getString(R.string.driver)
            else -> resources.getString(R.string.newbie)
        }
        getData()
        shift_car_spinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
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
                        shift_fuel_morning_spinner_8.visibility = View.GONE
                        shift_fuel_evening_spinner_8.visibility = View.GONE
                        shift_fuel_morning_spinner_9.visibility = View.VISIBLE
                        shift_fuel_evening_spinner_9.visibility = View.VISIBLE
                    }
                    Cars.Sandero.i -> {
                        shift_fuel_morning_spinner_8.visibility = View.GONE
                        shift_fuel_evening_spinner_8.visibility = View.GONE
                        shift_fuel_morning_spinner_9.visibility = View.VISIBLE
                        shift_fuel_evening_spinner_9.visibility = View.VISIBLE
                    }
                    Cars.XRay.i -> {
                        shift_fuel_morning_spinner_8.visibility = View.VISIBLE
                        shift_fuel_evening_spinner_8.visibility = View.VISIBLE
                        shift_fuel_morning_spinner_9.visibility = View.GONE
                        shift_fuel_evening_spinner_9.visibility = View.GONE
                    }
                }
            }
        })
        shift_write.setOnClickListener {
            try {
                prefs.family = shift_family_editText.text.toString()
                prefs.bonus = shift_bonus_checkbox.isChecked
                prefs.date = shift_date.text.toString()
                prefs.region = shift_region_spinner.selectedItem.toString()
                prefs.regionPosition = shift_region_spinner.selectedItemPosition
                prefs.car = shift_car_spinner.selectedItem.toString()
                prefs.carPosition = shift_car_spinner.selectedItemPosition
                prefs.morningODO = shift_odo_morning.text.toString().toInt()
                prefs.eveningODO = shift_odo_evening.text.toString().toInt()
                if (shift_car_spinner.selectedItemPosition == Cars.XRay.i) {
                    prefs.morningFuel = (shift_fuel_morning_spinner_8.selectedItemPosition + 1)
                    prefs.eveningFuel = (shift_fuel_evening_spinner_8.selectedItemPosition + 1)
                } else {
                    prefs.morningFuel = (shift_fuel_morning_spinner_9.selectedItemPosition + 1)
                    prefs.eveningFuel = (shift_fuel_evening_spinner_9.selectedItemPosition + 1)
                }
                when (shift_car_spinner.selectedItemPosition) {
                    Cars.Largus.i -> {
                        prefs.lastFuelLargus = (shift_fuel_evening_spinner_9.selectedItemPosition + 1)
                        prefs.lastOdoLargus = shift_odo_evening.text.toString().toInt()
                    }
                    Cars.Sandero.i -> {
                        prefs.lastFuelSandero = (shift_fuel_evening_spinner_9.selectedItemPosition + 1)
                        prefs.lastOdoSandero = shift_odo_evening.text.toString().toInt()
                    }
                    Cars.XRay.i -> {
                        prefs.lastFuelXRay = (shift_fuel_evening_spinner_8.selectedItemPosition + 1)
                        prefs.lastOdoXRay = shift_odo_evening.text.toString().toInt()
                    }
                }
                Snackbar.make(view, getString(R.string.recorded), Snackbar.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view, getString(R.string.wrongData), Snackbar.LENGTH_SHORT).show()
            }
        }
        shift_date.setOnClickListener {
            datePickerDialog(it)
        }
        shift_new_button.setOnClickListener {
            try {
                when (shift_car_spinner.selectedItemPosition) {
                    Cars.Largus.i -> {
                        shift_odo_morning.setText(prefs.lastOdoLargus!!.toString())
                        shift_odo_evening.setText("0")
                        shift_fuel_morning_spinner_9.setSelection(prefs.lastFuelLargus!!.minus(1))
                    }
                    Cars.Sandero.i -> {
                        shift_odo_morning.setText(prefs.lastOdoSandero!!.toString())
                        shift_odo_evening.setText("0")
                        shift_fuel_morning_spinner_9.setSelection(prefs.lastFuelSandero!!.minus(1))
                    }
                    Cars.XRay.i -> {
                        shift_odo_morning.setText(prefs.lastOdoXRay!!.toString())
                        shift_odo_evening.setText("0")
                        shift_fuel_morning_spinner_8.setSelection(prefs.lastFuelXRay!!.minus(1))
                    }
                }

                Snackbar.make(view, getString(R.string.dontForgetDate), Snackbar.LENGTH_LONG).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view, getString(R.string.wrongData), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun getData() {
        try {
            shift_family_editText.setText(prefs.family)
            shift_bonus_checkbox.isChecked = prefs.bonus
            shift_date.text = prefs.date
            shift_region_spinner.setSelection(prefs.regionPosition!!)
            shift_car_spinner.setSelection(prefs.carPosition!!)
            shift_odo_morning.setText(prefs.morningODO!!.toString())
            shift_odo_evening.setText(prefs.eveningODO!!.toString())
            if (shift_car_spinner.selectedItemPosition == Cars.XRay.i) {
                shift_fuel_morning_spinner_8.setSelection(prefs.morningFuel!!.minus(1))
                shift_fuel_evening_spinner_8.setSelection(prefs.eveningFuel!!.minus(1))
            } else {
                shift_fuel_morning_spinner_9.setSelection(prefs.morningFuel!!.minus(1))
                shift_fuel_evening_spinner_9.setSelection(prefs.eveningFuel!!.minus(1))
            }
        } catch (e: Exception) {
            shift_family_editText.setText("")
            shift_bonus_checkbox.isChecked = false
            shift_date.text = ""
            shift_region_spinner.setSelection(0)
            shift_car_spinner.setSelection(0)
            shift_odo_morning.setText("")
            shift_odo_evening.setText("")
            shift_fuel_morning_spinner_8.setSelection(0)
            shift_fuel_evening_spinner_8.setSelection(0)
            shift_fuel_morning_spinner_9.setSelection(0)
            shift_fuel_evening_spinner_9.setSelection(0)
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
        val year: Int
        val month: Int
        val dayOfMonth: Int
        year = cal.get(Calendar.YEAR)
        month = cal.get(Calendar.MONTH)
        dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(
            context!!,
            dateListener(v),
            year,
            month,
            dayOfMonth
        ).show()
    }
}
