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
import kotlinx.android.synthetic.main.fragment_total_month.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class TotalMonthFragment : MvpAppCompatFragment() {

    private var items: List<Delivery> = ArrayList()
    private lateinit var total: Total
    private var newTotal = true
    private lateinit var callBackActivity: MainView
    private var salary = 0
    private var teaMoney = 0
    private var deltaODO = 0
    private var totalDeliveries = 0
    private var loganMove = 0
    private var vestaMove = 0
    private var totalMove = 0
    private var totalMoveWithSalary = 0
    private var loganTask = 0
    private var vestaTask = 0
    private var totalTask = 0
    private var totalTaskWithSalary = 0
    private var largusShifts = 0
    private var sanderoShifts = 0
    private var xrayShifts = 0

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
        return inflater.inflate(R.layout.fragment_total_month, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (newTotal) {
            val search =
                "${prefs.date!![2]}${prefs.date!![3]}${prefs.date!![4]}${prefs.date!![5]}${prefs.date!![6]}${prefs.date!![7]}${prefs.date!![8]}${prefs.date!![9]}"
            getData(search)
            calculateSum(search)
            setData()
            deltaODO(search)
            month_share.visibility = View.GONE
        } else {
            month_write.visibility = View.GONE
            placeData()
        }
        month_write.setOnClickListener {
            saveData()
        }
        month_share.setOnClickListener {
            shareDate()
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
        month_total_money_textView.text = totalMoney()
        month_total_cash_textView.text = totalCash()
        month_total_card_textView.text = totalCard()
        month_total_delivery_value_logan_textView.text = totalDeliveryLogan()
        month_logan_money_textView.text = loganMoney()
        month_logan_cash_textView.text = loganCash()
        month_logan_card_textView.text = loganCard()
        month_total_delivery_value_vesta_textView.text = totalDeliveryVesta()
        month_vesta_money_textView.text = vestaMoney()
        month_vesta_cash_textView.text = vestaCash()
        month_vesta_card_textView.text = vestaCard()
        month_prepay_textView.text = prepay()
        month_holiday_pay_textView.text = holiday()
        try {
            month_mid_salary_textView.text = "${salary / totalShifts().toInt()}"
            month_to_recieve_textView.text =
                "${salary - month_prepay_textView.text.toString().toInt() - month_holiday_pay_textView.text.toString().toInt()}"
        } catch (e: Exception) {
            e.printStackTrace()
            Snackbar.make(view!!, getString(R.string.error), Snackbar.LENGTH_SHORT).show()
        }

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

    private fun totalDeliveryLogan(): String {
        var totalDeliveryLogan = 0
        for (position in items) {
            if (position.workType == 0 && position.deliveryType == 0) {
                totalDeliveryLogan += 1
            }
        }
        return totalDeliveryLogan.toString()
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

    private fun totalDeliveryVesta(): String {
        var totalDeliveryVesta = 0
        for (position in items) {
            if (position.workType == 0 && position.deliveryType == 1) {
                totalDeliveryVesta += 1
            }
        }
        return totalDeliveryVesta.toString()
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

    private fun prepay(): String {
        var prepay = 0
        for (position in items) {
            if (position.workType == 5 && position.deliveryType == 0) {
                prepay += position.cost
            }
        }
        return prepay.toString()
    }

    private fun holiday(): String {
        var holiday = 0
        for (position in items) {
            if (position.workType == 5 && position.deliveryType == 1) {
                holiday += position.cost
            }
        }
        return holiday.toString()
    }

    private fun calculateSum(search: String) = GlobalScope.launch {
        val totals = DB.getDao().getTotalsByMonth("%$search%")
        for (position in totals) {
            when {
                position.carIndex == 0 -> largusShifts++
                position.carIndex == 1 -> sanderoShifts++
                position.carIndex == 2 -> xrayShifts++
            }
            salary += position.salary
            teaMoney += position.expenses
            totalDeliveries += position.totalDeliveries
            loganMove += position.loganMove
            vestaMove += position.vestaMove
            totalMove += position.totalMove
            totalMoveWithSalary += position.movesWithSalary
            loganTask += position.loganTask
            vestaTask += position.vestaTask
            totalTask += position.totalTask
            totalTaskWithSalary += position.tasksWithSalary
        }
        month_largus_count_textView.text = largusShifts.toString()
        month_sandero_count_textView.text = sanderoShifts.toString()
        month_xray_count_textView.text = xrayShifts.toString()
        month_total_delivery_value_textView.text = totalDeliveries.toString()
        month_logan_move_textView.text = loganMove.toString()
        month_vesta_move_textView.text = vestaMove.toString()
        month_total_move_textView.text = "${totalMoveWithSalary}(${totalMove})"
        month_logan_task_textView.text = loganTask.toString()
        month_vesta_task_textView.text = vestaTask.toString()
        month_total_task_textView.text = "${totalTaskWithSalary}(${totalTask})"
        month_salary_textView.text = salary.toString()
        month_tea_textView.text = teaMoney.toString()
    }

    private fun deltaODO(search: String) = GlobalScope.launch {
        val totals = DB.getDao().getTotalsByMonth("%$search%")
        for (position in totals) {
            deltaODO += position.deltaODO
        }
    }

    private fun saveData() = GlobalScope.launch {
        try {
            total.dayOrMonth = 1
            total.date = prefs.date!!
            total.totalShifts = month_total_shifts_value_textView.text.toString().toInt()
            total.totalDeliveries = totalDeliveries
            total.movesWithSalary = totalMoveWithSalary
            total.loganMove = loganMove
            total.vestaMove = vestaMove
            total.totalMove = totalMove
            total.tasksWithSalary = totalTaskWithSalary
            total.loganTask = loganTask
            total.vestaTask = vestaTask
            total.totalTask = totalTask
            total.totalMoney = month_total_money_textView.text.toString().toInt()
            total.totalCash = month_total_cash_textView.text.toString().toInt()
            total.totalCard = month_total_card_textView.text.toString().toInt()
            total.loganDeliveryValue =
                month_total_delivery_value_logan_textView.text.toString().toInt()
            total.loganMoney = month_logan_money_textView.text.toString().toInt()
            total.loganCash = month_logan_cash_textView.text.toString().toInt()
            total.loganCard = month_logan_card_textView.text.toString().toInt()
            total.vestaDeliveryValue =
                month_total_delivery_value_vesta_textView.text.toString().toInt()
            total.vestaMoney = month_vesta_money_textView.text.toString().toInt()
            total.vestaCash = month_vesta_cash_textView.text.toString().toInt()
            total.vestaCard = month_vesta_card_textView.text.toString().toInt()
            total.salary = month_salary_textView.text.toString().toInt()
            total.expenses = month_tea_textView.text.toString().toInt()
            total.deltaODO = deltaODO
            total.prepay = month_prepay_textView.text.toString().toInt()
            total.holidayPay = month_holiday_pay_textView.text.toString().toInt()
            total.largusShifts = month_largus_count_textView.text.toString().toInt()
            total.sanderoShifts = month_sandero_count_textView.text.toString().toInt()
            total.xrayShifts = month_xray_count_textView.text.toString().toInt()
            DB.getDao().addTotal(total)
            callBackActivity.fragmentPlace(TotalFragment())
        } catch (e: Exception) {
            e.printStackTrace()
            Snackbar.make(view!!, getString(R.string.someError), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun placeData() {
        month_month.text = "${total.date[3]}${total.date[4]}"
        month_year.text = "${total.date[6]}${total.date[7]}${total.date[8]}${total.date[9]}"
        month_family_textView.text = prefs.family
        month_total_shifts_value_textView.text = total.totalShifts.toString()
        month_total_delivery_value_textView.text = total.totalDeliveries.toString()
        month_logan_move_textView.text = total.loganMove.toString()
        month_vesta_move_textView.text = total.vestaMove.toString()
        month_total_move_textView.text = "${total.movesWithSalary}(${total.totalMove})"
        month_logan_task_textView.text = total.loganTask.toString()
        month_vesta_task_textView.text = total.vestaTask.toString()
        month_total_task_textView.text = "${total.tasksWithSalary}(${total.totalTask})"
        month_total_money_textView.text = total.totalMoney.toString()
        month_total_cash_textView.text = total.totalCash.toString()
        month_total_card_textView.text = total.totalCard.toString()
        month_total_delivery_value_logan_textView.text = total.loganDeliveryValue.toString()
        month_logan_money_textView.text = total.loganMoney.toString()
        month_logan_cash_textView.text = total.loganCash.toString()
        month_logan_card_textView.text = total.loganCard.toString()
        month_total_delivery_value_vesta_textView.text = total.vestaDeliveryValue.toString()
        month_vesta_money_textView.text = total.vestaMoney.toString()
        month_vesta_cash_textView.text = total.vestaCash.toString()
        month_vesta_card_textView.text = total.vestaCard.toString()
        month_salary_textView.text = total.salary.toString()
        month_mid_salary_textView.text = "${total.salary / total.totalShifts}"
        month_tea_textView.text = total.expenses.toString()
        month_prepay_textView.text = total.prepay.toString()
        month_holiday_pay_textView.text = total.holidayPay.toString()
        month_to_recieve_textView.text = "${total.salary - total.prepay - total.holidayPay}"
        month_largus_count_textView.text = total.largusShifts.toString()
        month_sandero_count_textView.text = total.sanderoShifts.toString()
        month_xray_count_textView.text = total.xrayShifts.toString()
    }

    private fun shareDate() = GlobalScope.launch {
        val textToSend = "${month_month.text} / ${month_year.text}\n" +
                "${prefs.family}\n" +
                "${resources.getString(R.string.shiftsValue)} ${month_total_shifts_value_textView.text}\n" +
                "${resources.getString(R.string.car_largus)}: ${month_largus_count_textView.text}\n" +
                "${resources.getString(R.string.car_sandero)}: ${month_sandero_count_textView.text}\n" +
                "${resources.getString(R.string.car_x_ray)}: ${month_xray_count_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.deliveryValue)} ${month_total_delivery_value_textView.text}\n" +
                "${resources.getString(R.string.totalMoney)} ${month_total_money_textView.text}\n" +
                "${resources.getString(R.string.cash)} ${month_total_cash_textView.text}\n" +
                "${resources.getString(R.string.card)} ${month_total_card_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.logan_divider)}\n" +
                "${resources.getString(R.string.deliveryValue)} ${month_total_delivery_value_logan_textView.text}\n" +
                "${resources.getString(R.string.money)} ${month_logan_money_textView.text}\n" +
                "${resources.getString(R.string.cash)} ${month_logan_cash_textView.text}\n" +
                "${resources.getString(R.string.card)} ${month_logan_card_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.vesta_divider)}\n" +
                "${resources.getString(R.string.deliveryValue)} ${month_total_delivery_value_vesta_textView.text}\n" +
                "${resources.getString(R.string.money)} ${month_vesta_money_textView.text}\n" +
                "${resources.getString(R.string.cash)} ${month_vesta_cash_textView.text}\n" +
                "${resources.getString(R.string.card)} ${month_vesta_card_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.total_moves)}\n" +
                "${resources.getString(R.string.logan_divider)}: ${month_logan_move_textView.text}\n" +
                "${resources.getString(R.string.vesta_divider)}: ${month_vesta_move_textView.text}\n" +
                "${resources.getString(R.string.total_moves)} ${month_total_move_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.total_tasks)}\n" +
                "${resources.getString(R.string.logan_divider)}: ${month_logan_task_textView.text}\n" +
                "${resources.getString(R.string.vesta_divider)}: ${month_vesta_task_textView.text}\n" +
                "${resources.getString(R.string.total_tasks)} ${month_total_task_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.salary)} ${month_salary_textView.text}\n" +
                "${resources.getString(R.string.prepay)} ${month_prepay_textView.text}\n" +
                "${resources.getString(R.string.holiday_pay)} ${month_holiday_pay_textView.text}\n" +
                "${resources.getString(R.string.money_to_recieve)} ${month_to_recieve_textView.text}"
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, textToSend)
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, resources.getString(R.string.share)))
    }
}
