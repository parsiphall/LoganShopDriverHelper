package com.parsiphal.loganshopdriverhelper.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatFragment

import com.parsiphal.loganshopdriverhelper.R
import com.parsiphal.loganshopdriverhelper.prefs
import kotlinx.android.synthetic.main.fragment_shift.*
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
        getData()
        shift_write.setOnClickListener {
            prefs.family = shift_family_editText.text.toString()
            prefs.date = shift_date.text.toString()
            prefs.car = shift_car_spinner.selectedItem.toString()
            prefs.carPosition = shift_car_spinner.selectedItemPosition
            prefs.morningODO = shift_odo_morning.text.toString().toInt()
            prefs.morningFuel = shift_fuel_morning.text.toString().toInt()
            prefs.eveningODO = shift_odo_evening.text.toString().toInt()
            prefs.eveningFuel = shift_fuel_evening.text.toString().toInt()
            Snackbar.make(view, getString(R.string.recorded), Snackbar.LENGTH_SHORT).show()
        }
        shift_date.setOnClickListener {
            datePickerDialog(it)
        }
        shift_new_button.setOnClickListener {
            shift_odo_morning.setText(shift_odo_evening.text.toString())
            shift_fuel_morning.setText(shift_fuel_evening.text.toString())
            shift_odo_evening.setText("0")
            shift_fuel_evening.setText("0")
            Snackbar.make(view, getString(R.string.dontForgetDate), Snackbar.LENGTH_LONG).show()
        }
    }

    private fun getData() {
        shift_family_editText.setText(prefs.family)
        shift_date.text = prefs.date
        shift_car_spinner.setSelection(prefs.carPosition!!)
        shift_odo_morning.setText(prefs.morningODO!!.toString())
        shift_fuel_morning.setText(prefs.morningFuel!!.toString())
        shift_odo_evening.setText(prefs.eveningODO!!.toString())
        shift_fuel_evening.setText(prefs.eveningFuel!!.toString())
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
