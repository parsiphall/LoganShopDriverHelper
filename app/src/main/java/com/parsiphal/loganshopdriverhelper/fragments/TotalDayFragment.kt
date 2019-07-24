package com.parsiphal.loganshopdriverhelper.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.parsiphal.loganshopdriverhelper.DB

import com.parsiphal.loganshopdriverhelper.R
import com.parsiphal.loganshopdriverhelper.data.Delivery
import com.parsiphal.loganshopdriverhelper.data.Total
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import com.parsiphal.loganshopdriverhelper.prefs
import kotlinx.android.synthetic.main.fragment_total_day.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TotalDayFragment : MvpAppCompatFragment() {

    private var items: List<Delivery> = ArrayList()
    private lateinit var total: Total
    private var newTotal = true
    private lateinit var callBackActivity: MainView
    private var salary = 0
    private var totalDeliveries = 0
    private var totalMove = 0
    private var totalTask = 0

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callBackActivity = context as MainView
    }

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
            calculateSum()
        } else {
            day_write.visibility = View.GONE
            placeData()
        }
        day_write.setOnClickListener {
            saveData()
//            activity?.onBackPressed()
            callBackActivity.fragmentPlace(TotalFragment())
        }
        day_share.setOnClickListener {
            shareData()
        }
    }

    private fun getData() = GlobalScope.launch {
        items = DB.getDao().getDeliveriesByDate(prefs.date!!)
    }

    private fun setData() {
        Thread.sleep(1000)
        val car = "${prefs.region} - ${prefs.car}"
        day_car_textView.text = car
        day_date_textView.text = prefs.date
        day_morning_odo_textView.text = prefs.morningODO.toString()
        day_evening_odo_textView.text = prefs.eveningODO.toString()
        day_morning_fuel_textView.text = prefs.morningFuel.toString()
        day_evening_fuel_textView.text = prefs.eveningFuel.toString()
        day_total_money_textView.text = totalMoney()
        day_total_money_cash_textView.text = totalCash()
        day_total_money_card_textView.text = totalCard()
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
        day_tea_textVew.text = teaMoney()
    }

    private fun teaMoney(): String {
        var teaMoney = 0
        for (position in items) {
            if (position.workType == 0) {
                teaMoney += position.expense
            }
        }
        return teaMoney.toString()
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
        var expenses = ""
        for (position in items) {
            if (position.workType == 3)
                expenses += "${position.comment} - ${position.cost} "
        }
        if (expenses == "") expenses = "0"
        return expenses
    }

    //    private fun salary(): String {
//        var totalMoney = 0
//        for (position in items) {
//            if (position.workType == 0) {
//                totalMoney += position.cost
//            }
//        }
//        val salary = if (prefs.bonus) {
//            (1600 + totalMoney * 2 / 100)
//        } else {
//            (1500 + totalMoney * 2 / 100)
//        }
//        return salary.toString()
//    }

    private fun calculateSum() {
        for (position in items) {
            if (position.workType == 0) {
                totalDeliveries++
            }
            if (position.workType == 1) {
                totalMove++
            }
            if (position.workType == 2) {
                totalTask++
            }
        }
        salary = (1700 + totalDeliveries * 50 + totalMove * 50 + totalTask * 50)
        day_total_deliveries_textVew.text = totalDeliveries.toString()
        day_total_move_textView.text = totalMove.toString()
        day_total_task_textView.text = totalTask.toString()
        day_salary_textView.text = salary.toString()
    }

    private fun deltaODO(): Int = total.eveningODO - total.morningODO

    private fun saveData() = GlobalScope.launch {
        try {
            total.dayOrMonth = 0
            val car = "${prefs.region} - ${prefs.car}"
            total.carModel = car
            total.date = prefs.date!!
            total.morningODO = prefs.morningODO!!
            total.eveningODO = prefs.eveningODO!!
            total.morningFuel = prefs.morningFuel!!
            total.eveningFuel = prefs.eveningFuel!!
            total.totalMoney = day_total_money_textView.text.toString().toInt()
            total.totalCash = day_total_money_cash_textView.text.toString().toInt()
            total.totalCard = day_total_money_card_textView.text.toString().toInt()
            total.loganDeliveryValue = day_logan_delivery_value_textView.text.toString().toInt()
            total.loganMoney = day_logan_money_textView.text.toString().toInt()
            total.loganCash = day_logan_cash_textView.text.toString().toInt()
            total.loganCard = day_logan_card_textView.text.toString().toInt()
            total.vestaDeliveryValue = day_vesta_delivery_value_textView.text.toString().toInt()
            total.vestaMoney = day_vesta_money_textView.text.toString().toInt()
            total.vestaCash = day_vesta_cash_textView.text.toString().toInt()
            total.vestaCard = day_vesta_card_textView.text.toString().toInt()
            total.expensesString = day_expenses_textView.text.toString()
            total.totalDeliveries = totalDeliveries
            total.totalMove = totalMove
            total.totalTask = totalTask
            total.salary = day_salary_textView.text.toString().toInt()
            total.expenses = day_tea_textVew.text.toString().toInt()
            total.deltaODO = deltaODO()
            DB.getDao().addTotal(total)
        } catch (e: Exception) {
            e.printStackTrace()
            Snackbar.make(view!!, getString(R.string.someError), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun placeData() {
        day_car_textView.text = total.carModel
        day_date_textView.text = total.date
        day_morning_odo_textView.text = total.morningODO.toString()
        day_evening_odo_textView.text = total.eveningODO.toString()
        day_morning_fuel_textView.text = total.morningFuel.toString()
        day_evening_fuel_textView.text = total.eveningFuel.toString()
        day_total_money_textView.text = total.totalMoney.toString()
        day_total_money_cash_textView.text = total.totalCash.toString()
        day_total_money_card_textView.text = total.totalCard.toString()
        day_logan_delivery_value_textView.text = total.loganDeliveryValue.toString()
        day_logan_money_textView.text = total.loganMoney.toString()
        day_logan_cash_textView.text = total.loganCash.toString()
        day_logan_card_textView.text = total.loganCard.toString()
        day_vesta_delivery_value_textView.text = total.vestaDeliveryValue.toString()
        day_vesta_money_textView.text = total.vestaMoney.toString()
        day_vesta_cash_textView.text = total.vestaCash.toString()
        day_vesta_card_textView.text = total.vestaCard.toString()
        day_expenses_textView.text = total.expensesString
        day_total_deliveries_textVew.text = total.totalDeliveries.toString()
        day_total_move_textView.text = total.totalMove.toString()
        day_total_task_textView.text = total.totalTask.toString()
        day_tea_textVew.text = total.expenses.toString()
        day_family.text = prefs.family
        day_salary_textView.text = total.salary.toString()
    }

    private fun shareData() = GlobalScope.launch {
        val textToSend = "${day_car_textView.text}\n" +
                "${day_date_textView.text}\n" +
                "${resources.getString(R.string.odo_morning)} ${day_morning_odo_textView.text}\n" +
                "${resources.getString(R.string.odo_evening)} ${day_evening_odo_textView.text}\n" +
                "${resources.getString(R.string.fuel_morning)} ${day_morning_fuel_textView.text} ${resources.getString(R.string.fuel_dividers)}\n" +
                "${resources.getString(R.string.fuel_evening)} ${day_evening_fuel_textView.text} ${resources.getString(R.string.fuel_dividers)}\n" +
                "${resources.getString(R.string.totalMoney)} ${day_total_money_textView.text}\n" +
                "${resources.getString(R.string.logan_divider)}\n" +
                "${resources.getString(R.string.deliveryValue)} ${day_logan_delivery_value_textView.text}\n" +
                "${resources.getString(R.string.money)} ${day_logan_money_textView.text}\n" +
                "${resources.getString(R.string.cash)} ${day_logan_cash_textView.text}\n" +
                "${resources.getString(R.string.card)} ${day_logan_card_textView.text}\n" +
                "${resources.getString(R.string.vesta_divider)}\n" +
                "${resources.getString(R.string.deliveryValue)} ${day_vesta_delivery_value_textView.text}\n" +
                "${resources.getString(R.string.money)} ${day_vesta_money_textView.text}\n" +
                "${resources.getString(R.string.cash)} ${day_vesta_cash_textView.text}\n" +
                "${resources.getString(R.string.card)} ${day_vesta_card_textView.text}\n" +
                "${resources.getString(R.string.expenses)} ${day_expenses_textView.text}\n" +
                "${resources.getString(R.string.total_deliveries)} ${day_total_deliveries_textVew.text}\n" +
                "${resources.getString(R.string.total_move)} ${day_total_move_textView.text}\n" +
                "${resources.getString(R.string.total_task)} ${day_total_task_textView.text}\n" +
                "${resources.getString(R.string.salary)} ${prefs.family} ${day_salary_textView.text}"
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, textToSend)
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, resources.getString(R.string.share)))
    }
}
