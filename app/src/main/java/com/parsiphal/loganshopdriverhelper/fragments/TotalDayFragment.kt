package com.parsiphal.loganshopdriverhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.parsiphal.loganshopdriverhelper.DB

import com.parsiphal.loganshopdriverhelper.R
import com.parsiphal.loganshopdriverhelper.data.Delivery
import com.parsiphal.loganshopdriverhelper.data.Total
import com.parsiphal.loganshopdriverhelper.prefs
import kotlinx.android.synthetic.main.fragment_total_day.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TotalDayFragment : MvpAppCompatFragment() {

    private var items: List<Delivery> = ArrayList()
    private lateinit var total: Total
    private var newTotal = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            total = bundle.getSerializable("ITEM") as Total
            newTotal = false
        } else {
            total = Total()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_total_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (newTotal) {
            getData()
            setData()
        } else {
            day_write.visibility = View.GONE
            placeData()
        }
        day_write.setOnClickListener {
            saveData()
            activity?.onBackPressed()
        }
    }

    private fun getData() = GlobalScope.launch {
        items = DB.getDao().getDeliveriesByDate(prefs.date!!)
    }

    private fun setData() {
        Thread.sleep(1000)
        day_car_textView.text = prefs.car
        day_date_textView.text = prefs.date
        day_morning_odo_textView.text = prefs.morningODO.toString()
        day_evening_odo_textView.text = prefs.eveningODO.toString()
        day_morning_fuel_textView.text = prefs.morningFuel.toString()
        day_evening_fuel_textView.text = prefs.eveningFuel.toString()
        day_total_money_textView.text = totalMoney()
        day_logan_delivery_value_textView.text = deliveryValueLogan()
        day_logan_money_textView.text = loganMoney()
        day_logan_cash_textView.text = loganCash()
        day_logan_card_textView.text = loganCard()
        day_vesta_delivery_value_textView.text = deliveryValueVesta()
        day_vesta_money_textView.text = vestaMoney()
        day_vesta_cash_textView.text = vestaCash()
        day_vesta_card_textView.text = vestaCard()
        day_expenses_textView.text = expenses()
        day_family.text = prefs.family
        day_salary_textView.text = salary()
    }

    private fun totalMoney(): String {
        var totalMoney = 0
        for (position in items) {
            if (position.workType == 0) {
                totalMoney += position.cost
            }
        }
        return totalMoney.toString()
    }

    private fun deliveryValueLogan(): String {
        var deliveryValueLogan = 0
        for (position in items) {
            if (position.workType == 0 && position.deliveryType == 0)
                deliveryValueLogan += 1
        }
        return deliveryValueLogan.toString()
    }

    private fun loganMoney(): String {
        var loganMoney = 0
        for (position in items) {
            if (position.workType == 0 && position.deliveryType == 0)
                loganMoney += position.cost
        }
        return loganMoney.toString()
    }

    private fun loganCash(): String {
        var loganCash = 0
        for (position in items) {
            if (position.workType == 0 && position.deliveryType == 0 && position.payType == 0)
                loganCash += position.cost
        }
        return loganCash.toString()
    }

    private fun loganCard(): String {
        var loganCard = 0
        for (position in items) {
            if (position.workType == 0 && position.deliveryType == 0 && position.payType == 1)
                loganCard += position.cost
        }
        return loganCard.toString()
    }

    private fun deliveryValueVesta(): String {
        var deliveryValueVesta = 0
        for (position in items) {
            if (position.workType == 0 && position.deliveryType == 1)
                deliveryValueVesta += 1
        }
        return deliveryValueVesta.toString()
    }

    private fun vestaMoney(): String {
        var vestaMoney = 0
        for (position in items) {
            if (position.workType == 0 && position.deliveryType == 1)
                vestaMoney += position.cost
        }
        return vestaMoney.toString()
    }

    private fun vestaCash(): String {
        var vestaCash = 0
        for (position in items) {
            if (position.workType == 0 && position.deliveryType == 1 && position.payType == 0)
                vestaCash += position.cost
        }
        return vestaCash.toString()
    }

    private fun vestaCard(): String {
        var vestaCard = 0
        for (position in items) {
            if (position.workType == 0 && position.deliveryType == 1 && position.payType == 1)
                vestaCard += position.cost
        }
        return vestaCard.toString()
    }

    private fun expenses(): String {
        var expenses = 0
        for (position in items) {
            if (position.workType == 2)
                expenses += position.cost
        }
        return expenses.toString()
    }

    private fun salary(): String {
        var totalMoney = 0
        for (position in items) {
            if (position.workType == 0) {
                totalMoney += position.cost
            }
        }
        val salary: Int = (1500 + totalMoney / 100 * 2)
        return salary.toString()
    }

    private fun saveData() = GlobalScope.launch {
        total.dayOrMonth = 0
        total.carModel = prefs.car!!
        total.date = prefs.date!!
        total.morningODO = prefs.morningODO!!
        total.eveningODO = prefs.eveningODO!!
        total.morningFuel = prefs.morningFuel!!
        total.eveningFuel = prefs.eveningFuel!!
        total.totalMoney = day_total_money_textView.text.toString().toInt()
        total.loganDeliveryValue = day_logan_delivery_value_textView.text.toString().toInt()
        total.loganMoney = day_logan_money_textView.text.toString().toInt()
        total.loganCash = day_logan_cash_textView.text.toString().toInt()
        total.loganCard = day_logan_card_textView.text.toString().toInt()
        total.vestaDeliveryValue = day_vesta_delivery_value_textView.text.toString().toInt()
        total.vestaMoney = day_vesta_money_textView.text.toString().toInt()
        total.vestaCash = day_vesta_cash_textView.text.toString().toInt()
        total.vestaCard = day_vesta_card_textView.text.toString().toInt()
        total.expenses = day_expenses_textView.text.toString().toInt()
        total.salary = day_salary_textView.text.toString().toInt()
        DB.getDao().addTotal(total)
    }

    private fun placeData() {
        day_car_textView.text = total.carModel
        day_date_textView.text = total.date
        day_morning_odo_textView.text = total.morningODO.toString()
        day_evening_odo_textView.text = total.eveningODO.toString()
        day_morning_fuel_textView.text = total.morningFuel.toString()
        day_evening_fuel_textView.text = total.eveningFuel.toString()
        day_total_money_textView.text = total.totalMoney.toString()
        day_logan_delivery_value_textView.text = total.loganDeliveryValue.toString()
        day_logan_money_textView.text = total.loganMoney.toString()
        day_logan_cash_textView.text = total.loganMoney.toString()
        day_logan_card_textView.text = total.loganCard.toString()
        day_vesta_delivery_value_textView.text = total.vestaDeliveryValue.toString()
        day_vesta_money_textView.text = total.vestaMoney.toString()
        day_vesta_cash_textView.text = total.vestaCash.toString()
        day_vesta_card_textView.text = total.vestaCard.toString()
        day_expenses_textView.text = total.expenses.toString()
        day_family.text = prefs.family
        day_salary_textView.text = total.salary.toString()
    }
}
