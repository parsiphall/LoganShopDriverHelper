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
    private var totalMoveToPay = 0
    private var totalTaskToPay = 0
    private var totalMove = 0
    private var totalTask = 0
    private var loganMoveWithSalary = 0
    private var loganTaskWithSalary = 0
    private var vestaMoveWithSalary = 0
    private var vestaTaskWithSalary = 0
    private var loganMoveWithOutSalary = 0
    private var loganTaskWithOutSalary = 0
    private var vestaMoveWithOutSalary = 0
    private var vestaTaskWithOutSalary = 0
    private var expenseFuel = 0
    private var expenseWash = 0
    private var expenseOther = 0

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

//    private fun expenses(): String {
//        var expenses = ""
//        for (position in items) {
//            if (position.workType == 3)
//                expenses += "${position.comment} - ${position.cost} "
//        }
//        if (expenses == "") expenses = "0"
//        return expenses
//    }
//
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
            if (position.workType == 1 && position.deliveryType == 0 && position.ifSalary == 1) {
                loganMoveWithSalary++
            }
            if (position.workType == 1 && position.deliveryType == 1 && position.ifSalary == 1) {
                vestaMoveWithSalary++
            }
            if (position.workType == 2 && position.deliveryType == 0 && position.ifSalary == 1) {
                loganTaskWithSalary++
            }
            if (position.workType == 2 && position.deliveryType == 1 && position.ifSalary == 1) {
                vestaTaskWithSalary++
            }
            if (position.workType == 1 && position.deliveryType == 0 && position.ifSalary == 0) {
                loganMoveWithOutSalary++
            }
            if (position.workType == 1 && position.deliveryType == 1 && position.ifSalary == 0) {
                vestaMoveWithOutSalary++
            }
            if (position.workType == 2 && position.deliveryType == 0 && position.ifSalary == 0) {
                loganTaskWithOutSalary++
            }
            if (position.workType == 2 && position.deliveryType == 1 && position.ifSalary == 0) {
                vestaTaskWithOutSalary++
            }
            if (position.workType == 3 && position.expenseType == 0) {
                expenseFuel += position.cost
            }
            if (position.workType == 3 && position.expenseType == 1) {
                expenseWash += position.cost
            }
            if (position.workType == 3 && position.expenseType == 2) {
                expenseOther += position.cost
            }
        }
        totalMoveToPay = loganMoveWithSalary + vestaMoveWithSalary
        totalTaskToPay = loganTaskWithSalary + vestaTaskWithSalary
        totalMove = totalMoveToPay + loganMoveWithOutSalary + vestaMoveWithOutSalary
        totalTask = totalTaskToPay + loganTaskWithOutSalary + vestaTaskWithOutSalary
        salary = (1700 + totalDeliveries * 50 + totalMoveToPay * 50 + totalTaskToPay * 50)
        day_total_deliveries_textVew.text = totalDeliveries.toString()
        day_logan_move_textView.text = (loganMoveWithSalary + loganMoveWithOutSalary).toString()
        day_vesta_move_textView.text = (vestaMoveWithSalary + vestaMoveWithOutSalary).toString()
        day_total_move_textView.text = "${totalMoveToPay}(${totalMove})"
        day_logan_task_textView.text = (loganTaskWithSalary + loganTaskWithOutSalary).toString()
        day_vesta_task_textView.text = (vestaTaskWithSalary + vestaTaskWithOutSalary).toString()
        day_total_task_textView.text = "${totalTaskToPay}(${totalTask})"

        day_expenses_textView.text = (expenseFuel + expenseWash + expenseOther).toString()
        day_expenses_fuel_textView.text = expenseFuel.toString()
        day_expenses_wash_textView.text = expenseWash.toString()
        day_expenses_other_textView.text = expenseOther.toString()

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
            total.expensesFuel = expenseFuel
            total.expensesWash = expenseWash
            total.expensesOther = expenseOther
            total.totalDeliveries = totalDeliveries
            total.loganMove = (loganMoveWithSalary + loganMoveWithOutSalary)
            total.vestaMove = (vestaMoveWithSalary + vestaMoveWithOutSalary)
            total.movesWithSalary = totalMoveToPay
            total.totalMove = totalMove
            total.loganTask = (loganTaskWithSalary + loganTaskWithOutSalary)
            total.vestaTask = (vestaTaskWithSalary + vestaTaskWithOutSalary)
            total.tasksWithSalary = totalTaskToPay
            total.totalTask = totalTask
            total.salary = day_salary_textView.text.toString().toInt()
            total.expenses = day_tea_textVew.text.toString().toInt()
            total.deltaODO = deltaODO()
            total.carIndex = prefs.carPosition!!
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
        day_expenses_textView.text = (total.expensesFuel + total.expensesWash + total.expensesOther).toString()
        day_expenses_fuel_textView.text = total.expensesFuel.toString()
        day_expenses_wash_textView.text = total.expensesWash.toString()
        day_expenses_other_textView.text = total.expensesOther.toString()
        day_total_deliveries_textVew.text = total.totalDeliveries.toString()
        day_logan_move_textView.text = total.loganMove.toString()
        day_vesta_move_textView.text = total.vestaMove.toString()
        day_total_move_textView.text = "${total.movesWithSalary}(${total.totalMove})"
        day_logan_task_textView.text = total.loganTask.toString()
        day_vesta_task_textView.text = total.vestaTask.toString()
        day_total_task_textView.text = "${total.tasksWithSalary}(${total.totalTask})"
        day_tea_textVew.text = total.expenses.toString()
        day_salary_textView.text = total.salary.toString()
    }

    private fun shareData() = GlobalScope.launch {
        val textToSend = "${day_car_textView.text} (${day_date_textView.text})\n" +
                "${resources.getString(R.string.odo_morning)} ${day_morning_odo_textView.text}\n" +
                "${resources.getString(R.string.odo_evening)} ${day_evening_odo_textView.text}\n" +
                "${resources.getString(R.string.fuel_morning)} ${day_morning_fuel_textView.text} ${resources.getString(R.string.fuel_dividers)}\n" +
                "${resources.getString(R.string.fuel_evening)} ${day_evening_fuel_textView.text} ${resources.getString(R.string.fuel_dividers)}\n" +
                "\n" +
                "${resources.getString(R.string.totalMoney)}/${resources.getString(R.string.total_deliveries)}: " +
                "${day_total_money_textView.text}/${day_total_deliveries_textVew.text}\n" +
                "   ${resources.getString(R.string.logan_divider)}\n" +
                "${resources.getString(R.string.money)}/${resources.getString(R.string.deliveryValue)}: " +
                "${day_logan_money_textView.text}/${day_logan_delivery_value_textView.text}\n" +
                "${resources.getString(R.string.cash)} ${day_logan_cash_textView.text}\n" +
                "${resources.getString(R.string.card)} ${day_logan_card_textView.text}\n" +
                "   ${resources.getString(R.string.vesta_divider)}\n" +
                "${resources.getString(R.string.money)}/${resources.getString(R.string.deliveryValue)}: " +
                "${day_vesta_money_textView.text}/${day_vesta_delivery_value_textView.text}\n" +
                "${resources.getString(R.string.cash)} ${day_vesta_cash_textView.text}\n" +
                "${resources.getString(R.string.card)} ${day_vesta_card_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.total_moves)}\n" +
                "${resources.getString(R.string.logan_divider)}: ${day_logan_move_textView.text}\n" +
                "${resources.getString(R.string.vesta_divider)}: ${day_vesta_move_textView.text}\n" +
                "${resources.getString(R.string.total_total)} ${day_total_move_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.total_tasks)}\n" +
                "${resources.getString(R.string.logan_divider)}: ${day_logan_task_textView.text}\n" +
                "${resources.getString(R.string.vesta_divider)}: ${day_vesta_task_textView.text}\n" +
                "${resources.getString(R.string.total_total)} ${day_total_task_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.expenses)}\n" +
                "${resources.getString(R.string.total_total)} ${day_expenses_textView.text}\n" +
                "${resources.getString(R.string.fuel)}: ${day_expenses_fuel_textView.text}\n" +
                "${resources.getString(R.string.wash)}: ${day_expenses_wash_textView.text}\n" +
                "${resources.getString(R.string.other)}: ${day_expenses_other_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.salary)} ${prefs.family} ${day_salary_textView.text}"
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, textToSend)
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, resources.getString(R.string.share)))
    }
}
