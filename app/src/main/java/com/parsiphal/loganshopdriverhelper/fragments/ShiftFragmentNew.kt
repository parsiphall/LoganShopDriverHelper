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
import com.parsiphal.loganshopdriverhelper.prefs
import kotlinx.android.synthetic.main.fragment_shift_new.*
import moxy.MvpAppCompatFragment
import java.lang.Exception

class ShiftFragmentNew : MvpAppCompatFragment() {

    var ifMorning = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shift_new, container, false)
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

    private fun familyTextListener() {
        shiftN_family_textView.setOnClickListener {
            hideMainViews()
            shiftN_enter_family.visibility = View.VISIBLE
            shiftN_enter_family_write_button.setOnClickListener {
                prefs.family = shiftN_enter_family_editText.text.toString()
                shiftN_family_textView.text = shiftN_enter_family_editText.text.toString()
                shiftN_enter_family.visibility = View.GONE
                showMainViews()
                val imm =
                    context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
            }
        }
    }

    private fun startShiftListener() {
        shiftN_start_shift_button.setOnClickListener {
            ifMorning = true
            hideMainViews()
            shiftN_enter_date.visibility = View.VISIBLE
        }
    }

    private fun enterDateListener() {
        shiftN_enter_date_next_button.setOnClickListener {
            val day = if (shiftN_enter_date_datePicker.dayOfMonth < 10) {
                "0${shiftN_enter_date_datePicker.dayOfMonth}"
            } else {
                "${shiftN_enter_date_datePicker.dayOfMonth}"
            }
            val month = if (shiftN_enter_date_datePicker.month + 1 < 10) {
                "0${shiftN_enter_date_datePicker.month + 1}"
            } else {
                "${shiftN_enter_date_datePicker.month + 1}"
            }
            val year = shiftN_enter_date_datePicker.year.toString()
            val date = "$day/${month}/${year}"
            prefs.date = date
            shiftN_date_textView.text = date
            shiftN_enter_date.visibility = View.GONE
            shiftN_enter_car.visibility = View.VISIBLE
        }
    }

    private fun enterCarListener() {
        shiftN_enter_car_largus_button.setOnClickListener {
            prefs.car = Cars.Largus.id
            prefs.carPosition = Cars.Largus.i
            shiftN_car_textView.text = Cars.Largus.name
            shiftN_enter_car.visibility = View.GONE
            shiftN_enter_district.visibility = View.VISIBLE
        }
        shiftN_enter_car_sandero_button.setOnClickListener {
            prefs.car = Cars.Sandero.id
            prefs.carPosition = Cars.Sandero.i
            shiftN_car_textView.text = Cars.Sandero.name
            shiftN_enter_car.visibility = View.GONE
            shiftN_enter_district.visibility = View.VISIBLE
        }
        shiftN_enter_car_xray_button.setOnClickListener {
            prefs.car = Cars.XRay.id
            prefs.carPosition = Cars.XRay.i
            shiftN_car_textView.text = Cars.XRay.id
            shiftN_enter_car.visibility = View.GONE
            shiftN_enter_district.visibility = View.VISIBLE
        }
        shiftN_enter_car_largusNew_button.setOnClickListener {
            prefs.car = Cars.LargusNew.id
            prefs.carPosition = Cars.LargusNew.i
            shiftN_car_textView.text = Cars.LargusNew.name
            shiftN_enter_car.visibility = View.GONE
            shiftN_enter_district.visibility = View.VISIBLE
        }
    }

    private fun enterDistrictListener() {
        shiftN_enter_district_north_button.setOnClickListener {
            prefs.region = Districts.North.ruName
            shiftN_district_textView.text = Districts.North.ruName
            shiftN_enter_district.visibility = View.GONE
            odoAndFuelListener()
        }
        shiftN_enter_district_center_button.setOnClickListener {
            prefs.region = Districts.Center.ruName
            shiftN_district_textView.text = Districts.Center.ruName
            shiftN_enter_district.visibility = View.GONE
            odoAndFuelListener()
        }
        shiftN_enter_district_south_button.setOnClickListener {
            prefs.region = Districts.South.ruName
            shiftN_district_textView.text = Districts.Center.ruName
            shiftN_enter_district.visibility = View.GONE
            odoAndFuelListener()
        }
    }

    private fun odoAndFuelListener() {
        shiftN_enter_odo_and_fuel.visibility = View.VISIBLE
        if (shiftN_car_textView.text == Cars.XRay.id) {
            shiftN_fuel_seekbar_9.visibility = View.GONE
        } else {
            shiftN_fuel_seekbar_8.visibility = View.GONE
        }
        shiftN_fuel_seekbar_8.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                shiftN_fuel_textView.text = shiftN_fuel_seekbar_8.progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        shiftN_fuel_seekbar_9.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                shiftN_fuel_textView.text = shiftN_fuel_seekbar_9.progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        shiftN_enter_odo_and_fuel_write_button.setOnClickListener {
            if (ifMorning) {
                prefs.morningODO = shiftN_odo_editText.text.toString().toInt()
                shiftN_odo_morning_textView.text = shiftN_odo_editText.text.toString()
                if (shiftN_car_textView.text == Cars.XRay.id) {
                    prefs.morningFuel = shiftN_fuel_seekbar_8.progress
                    shiftN_fuel_morning_textView.text = shiftN_fuel_seekbar_8.progress.toString()
                } else {
                    prefs.morningFuel = shiftN_fuel_seekbar_9.progress
                    shiftN_fuel_morning_textView.text = shiftN_fuel_seekbar_9.progress.toString()
                }
            } else {
                prefs.eveningODO = shiftN_odo_editText.text.toString().toInt()
                shiftN_odo_evening_textView.text = shiftN_odo_editText.text.toString()
                if (shiftN_car_textView.text == Cars.XRay.id) {
                    prefs.eveningFuel = shiftN_fuel_seekbar_8.progress
                    shiftN_fuel_evening_textView.text = shiftN_fuel_seekbar_8.progress.toString()
                } else {
                    prefs.eveningFuel = shiftN_fuel_seekbar_9.progress
                    shiftN_fuel_evening_textView.text = shiftN_fuel_seekbar_9.progress.toString()
                }
            }
            shiftN_enter_odo_and_fuel.visibility = View.GONE
            val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
            showMainViews()
        }
    }

    private fun closeShiftListener() {
        shiftN_finish_shift_button.setOnClickListener {
            ifMorning = false
            shiftN_fuel_seekbar_8.progress = 0
            shiftN_fuel_seekbar_9.progress = 0
            shiftN_odo_editText.setText("")
            hideMainViews()
            odoAndFuelListener()
        }
    }

    private fun getData() {
        try {
            shiftN_status_textView.text = when (prefs.status) {
                0 -> resources.getString(R.string.driver)
                else -> resources.getString(R.string.newbie)
            }
            shiftN_family_textView.text = prefs.family
            shiftN_date_textView.text = prefs.date
            shiftN_car_textView.text = prefs.car
            shiftN_district_textView.text = prefs.region
            shiftN_odo_morning_textView.text = prefs.morningODO.toString()
            shiftN_fuel_morning_textView.text = prefs.morningFuel.toString()
            shiftN_odo_evening_textView.text = prefs.eveningODO.toString()
            shiftN_fuel_evening_textView.text = prefs.eveningFuel.toString()
        } catch (e: Exception) {
            Snackbar.make(view!!, getString(R.string.shiftError), Snackbar.LENGTH_LONG).show()
        }
    }

    private fun hideMainViews() {
        shiftN_family.visibility = View.GONE
        shiftN_date_and_car.visibility = View.GONE
        shiftN_morning_divider.visibility = View.GONE
        shiftN_morning_odo.visibility = View.GONE
        shiftN_morning_fuel.visibility = View.GONE
        shiftN_evening_divider.visibility = View.GONE
        shiftN_evening_odo.visibility = View.GONE
        shiftN_evening_fuel.visibility = View.GONE
        shiftN_buttons.visibility = View.GONE
    }

    private fun showMainViews() {
        shiftN_family.visibility = View.VISIBLE
        shiftN_date_and_car.visibility = View.VISIBLE
        shiftN_morning_divider.visibility = View.VISIBLE
        shiftN_morning_odo.visibility = View.VISIBLE
        shiftN_morning_fuel.visibility = View.VISIBLE
        shiftN_evening_divider.visibility = View.VISIBLE
        shiftN_evening_odo.visibility = View.VISIBLE
        shiftN_evening_fuel.visibility = View.VISIBLE
        shiftN_buttons.visibility = View.VISIBLE
    }
}
