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
import kotlinx.android.synthetic.main.fragment_total_month.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class TotalMonthFragment : MvpAppCompatFragment() {

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
        return inflater.inflate(R.layout.fragment_total_month, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (newTotal) {
            val search = "${prefs.date!![2]}${prefs.date!![3]}${prefs.date!![4]}${prefs.date!![5]}"
            getData(search)
            setData()
        } else {
            month_write.visibility = View.GONE
            placeData()
        }
        month_write.setOnClickListener {
            saveData()
            activity?.onBackPressed()
        }
    }

    private fun getData(search: String) = GlobalScope.launch {
        items = DB.getDao().getDeliveriesByMonth("%$search%")
    }

    private fun setData() {
        Thread.sleep(1000)
        month_month.text = "${prefs.date!![3]}${prefs.date!![4]}"
        month_year.text = "${prefs.date!![6]}${prefs.date!![7]}${prefs.date!![8]}${prefs.date!![9]}"
        month_family_textView.text = prefs.family
        month_total_shifts_value_textView.text = totalShifts()
        month_total_delivery_value_textView.text = totalDeliveryValue()
        month_total_money_textView.text = totalMoney()
        month_total_cash_textView.text = totalCash()
        month_total_card_textView.text = totalCard()
        month_logan_money_textView.text = loganMoney()
        month_logan_cash_textView.text = loganCash()
        month_logan_card_textView.text = loganCard()
        month_vesta_money_textView.text = vestaMoney()
        month_vesta_cash_textView.text = vestaCash()
        month_vesta_card_textView.text = vestaCard()
        month_salary_textView.text = salary()
    }

    private fun totalShifts(): String {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        var dayOfMonth = 1
        var myMonth = (month + 1).toString()
        var myDay: String
        if (month < 9) {
            myMonth = "0$myMonth"
        }
        var totalShifts = 0
        var dayCount = 0
        do {
            myDay = if (dayOfMonth < 10) {
                "0$dayOfMonth"
            } else {
                dayOfMonth.toString()
            }
            val date = "$myDay/$myMonth/$year"
            for (position in items) {
                if (position.deliveryDate == date) {
                    dayCount++
                }
            }
            if (dayCount != 0) {
                totalShifts++
                dayCount = 0
            }
            dayOfMonth++
        } while (dayOfMonth < 32)
        return totalShifts.toString()
    }

    private fun totalDeliveryValue(): String {
        var totalDeliveryValue = 0
        for (position in items) {
            if (position.workType == 0) {
                totalDeliveryValue += 1
            }
        }
        return totalDeliveryValue.toString()
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

    private fun totalCash(): String {
        var totalCash = 0
        for (position in items) {
            if (position.workType == 0 && position.payType == 0)
                totalCash += position.cost
        }
        return totalCash.toString()
    }

    private fun totalCard(): String {
        var totalCard = 0
        for (position in items) {
            if (position.workType == 0 && position.payType == 1)
                totalCard += position.cost
        }
        return totalCard.toString()
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

    private fun salary(): String {
        var totalMoney = 0
        for (position in items) {
            if (position.workType == 0) {
                totalMoney += position.cost
            }
        }
        val salary: Int = (1500 * totalShifts().toInt() + totalMoney / 100 * 2)
        return salary.toString()
    }

    private fun placeData() {
        month_month.text = "${total.date[3]}${total.date[4]}"
        month_year.text = "${total.date[6]}${total.date[7]}${total.date[8]}${total.date[9]}"
        month_family_textView.text = prefs.family
        month_total_shifts_value_textView.text = total.totalShifts.toString()
        month_total_money_textView.text = total.totalMoney.toString()
        month_total_cash_textView.text = total.totalCash.toString()
        month_total_card_textView.text = total.totalCard.toString()
        month_logan_money_textView.text = total.loganMoney.toString()
        month_logan_cash_textView.text = total.loganCash.toString()
        month_logan_card_textView.text = total.loganCard.toString()
        month_vesta_money_textView.text = total.vestaMoney.toString()
        month_vesta_cash_textView.text = total.vestaCash.toString()
        month_vesta_card_textView.text = total.vestaCard.toString()
        month_salary_textView.text = total.salary.toString()
    }

    private fun saveData() = GlobalScope.launch {
        total.dayOrMonth = 1
        total.date = prefs.date!!
        total.totalShifts = month_total_shifts_value_textView.text.toString().toInt()
        total.totalMoney = month_total_money_textView.text.toString().toInt()
        total.totalCash = month_total_cash_textView.text.toString().toInt()
        total.totalCard = month_total_card_textView.text.toString().toInt()
        total.loganMoney = month_logan_money_textView.text.toString().toInt()
        total.loganCash = month_logan_cash_textView.text.toString().toInt()
        total.loganCard = month_logan_card_textView.text.toString().toInt()
        total.vestaMoney = month_vesta_money_textView.text.toString().toInt()
        total.vestaCash = month_vesta_cash_textView.text.toString().toInt()
        total.vestaCard = month_vesta_card_textView.text.toString().toInt()
        total.salary = month_salary_textView.text.toString().toInt()
        DB.getDao().addTotal(total)
    }
}
