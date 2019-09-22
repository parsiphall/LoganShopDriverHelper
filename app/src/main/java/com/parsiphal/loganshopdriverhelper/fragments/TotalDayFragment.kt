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
import com.parsiphal.loganshopdriverhelper.data.PayType
import com.parsiphal.loganshopdriverhelper.data.Total
import com.parsiphal.loganshopdriverhelper.data.WorkTypes
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import com.parsiphal.loganshopdriverhelper.prefs
import kotlinx.android.synthetic.main.fragment_total_day.*
import kotlinx.coroutines.*

class TotalDayFragment : MvpAppCompatFragment() {

    private var items: List<Delivery> = ArrayList()
    private lateinit var total: Total
    private var newTotal = true
    private lateinit var callBackActivity: MainView
    private var teaMoney = 0
    private var totalMoney = 0
    private var totalCash = 0
    private var totalCard = 0
    private var deliveryValueLogan = 0
    private var loganMoney = 0
    private var loganCash = 0
    private var loganCard = 0
    private var deliveryValueVesta = 0
    private var vestaMoney = 0
    private var vestaCash = 0
    private var vestaCard = 0
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
    private var prepay = 0
    private var holiday = 0
    private var loganMoveFromZhukova = 0
    private var loganMoveFromKulturi = 0
    private var loganMoveFromSedova = 0
    private var loganMoveFromHimikov = 0
    private var loganMoveToZhukova = 0
    private var loganMoveToKulturi = 0
    private var loganMoveToSedova = 0
    private var loganMoveToHimikov = 0
    private var vestaMoveFromZhukova = 0
    private var vestaMoveFromKulturi = 0
    private var vestaMoveFromSedova = 0
    private var vestaMoveFromHimikov = 0
    private var vestaMoveToZhukova = 0
    private var vestaMoveToKulturi = 0
    private var vestaMoveToSedova = 0
    private var vestaMoveToHimikov = 0
    private var loganTaskFromZhukova = 0
    private var loganTaskFromKulturi = 0
    private var loganTaskFromSedova = 0
    private var loganTaskFromHimikov = 0
    private var loganTaskToZhukova = 0
    private var loganTaskToKulturi = 0
    private var loganTaskToSedova = 0
    private var loganTaskToHimikov = 0
    private var loganTaskElse = 0
    private var vestaTaskFromZhukova = 0
    private var vestaTaskFromKulturi = 0
    private var vestaTaskFromSedova = 0
    private var vestaTaskFromHimikov = 0
    private var vestaTaskToZhukova = 0
    private var vestaTaskToKulturi = 0
    private var vestaTaskToSedova = 0
    private var vestaTaskToHimikov = 0
    private var vestaTaskElse = 0

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
            val data = GlobalScope.async { getData() }
            MainScope().launch {
                data.await()
                setData()
                delay(1000)
//                day_progress.visibility = View.GONE
//                day_data.visibility = View.VISIBLE
                saveData()
            }
            day_share.visibility = View.GONE
        } else {
            val data = GlobalScope.async { placeData() }
            MainScope().launch {
                data.await()
                day_progress.visibility = View.GONE
                day_data.visibility = View.VISIBLE
            }
            day_write.visibility = View.GONE
        }
        day_write.setOnClickListener {
            saveData()
        }
        day_share.setOnClickListener {
            shareData()
        }
    }

    private fun getData() {
        items = DB.getDao().getDeliveriesByDate(prefs.date!!)
        calculateSum()
    }

    private fun setData() {
        val car = "${prefs.region} - ${prefs.car}"
        day_car_textView.text = car
        day_date_textView.text = prefs.date
        day_morning_odo_textView.text = prefs.morningODO.toString()
        day_evening_odo_textView.text = prefs.eveningODO.toString()
        day_morning_fuel_textView.text = (prefs.morningFuel?.plus(1)).toString()
        day_evening_fuel_textView.text = (prefs.eveningFuel?.plus(1)).toString()
        day_total_money_textView.text = totalMoney.toString()
        day_total_money_cash_textView.text = totalCash.toString()
        day_total_money_card_textView.text = totalCard.toString()
        day_logan_delivery_value_textView.text = deliveryValueLogan.toString()
        day_logan_money_textView.text = loganMoney.toString()
        day_logan_cash_textView.text = loganCash.toString()
        day_logan_card_textView.text = loganCard.toString()
        day_vesta_delivery_value_textView.text = deliveryValueVesta.toString()
        day_vesta_money_textView.text = vestaMoney.toString()
        day_vesta_cash_textView.text = vestaCash.toString()
        day_vesta_card_textView.text = vestaCard.toString()
        day_tea_textVew.text = teaMoney.toString()

        day_total_deliveries_textVew.text = totalDeliveries.toString()
        day_logan_move_textView.text = (loganMoveWithSalary + loganMoveWithOutSalary).toString()
        day_logan_zhukova_move_textView.text = loganMoveToZhukova.toString()
        day_logan_kulturi_move_textView.text = loganMoveToKulturi.toString()
        day_logan_sedova_move_textView.text = loganMoveToSedova.toString()
        day_logan_himikov_move_textView.text = loganMoveToHimikov.toString()
        day_vesta_move_textView.text = (vestaMoveWithSalary + vestaMoveWithOutSalary).toString()
        day_vesta_zhukova_move_textView.text = vestaMoveToZhukova.toString()
        day_vesta_kulturi_move_textView.text = vestaMoveToKulturi.toString()
        day_vesta_sedova_move_textView.text = vestaMoveToSedova.toString()
        day_vesta_himikov_move_textView.text = vestaMoveToHimikov.toString()
        day_total_move_textView.text = "${totalMoveToPay}(${totalMove})"
        day_logan_task_textView.text = (loganTaskWithSalary + loganTaskWithOutSalary).toString()
        day_logan_zhukova_task_textView.text = loganTaskToZhukova.toString()
        day_logan_kulturi_task_textView.text = loganTaskToKulturi.toString()
        day_logan_sedova_task_textView.text = loganTaskToSedova.toString()
        day_logan_himikov_task_textView.text = loganTaskToHimikov.toString()
        day_logan_else_task_textView.text = loganTaskElse.toString()
        day_vesta_task_textView.text = (vestaTaskWithSalary + vestaTaskWithOutSalary).toString()
        day_vesta_zhukova_task_textView.text = vestaTaskToZhukova.toString()
        day_vesta_kulturi_task_textView.text = vestaTaskToKulturi.toString()
        day_vesta_sedova_task_textView.text = vestaTaskToSedova.toString()
        day_vesta_himikov_task_textView.text = vestaTaskToHimikov.toString()
        day_vesta_else_task_textView.text = vestaTaskElse.toString()
        day_total_task_textView.text = "${totalTaskToPay}(${totalTask})"

        day_expenses_textView.text = (expenseFuel + expenseWash + expenseOther).toString()
        day_expenses_fuel_textView.text = expenseFuel.toString()
        day_expenses_wash_textView.text = expenseWash.toString()
        day_expenses_other_textView.text = expenseOther.toString()

        day_salary_textView.text = salary.toString()
        day_prepay_textVew.text = prepay.toString()
        day_holiday_textVew.text = holiday.toString()
    }

    private fun calculateSum() {
        for (position in items) {
            if (position.workType == WorkTypes.Delivery.i) {
                teaMoney += position.expense
            }
            if (position.workType == WorkTypes.Delivery.i) {
                totalMoney += position.cost
            }
            if (position.workType == WorkTypes.Delivery.i && position.payType == PayType.Cash.i) {
                totalCash += position.cost
            }
            if (position.workType == 0 && position.payType == 1) {
                totalCard += position.cost
            }
            if (position.workType == 0 && position.deliveryType == 0) {
                deliveryValueLogan += 1
            }
            if (position.workType == 0 && position.deliveryType == 0) {
                loganMoney += position.cost
            }
            if (position.workType == 0 && position.deliveryType == 0 && position.payType == 0) {
                loganCash += position.cost
            }
            if (position.workType == 0 && position.deliveryType == 0 && position.payType == 1) {
                loganCard += position.cost
            }
            if (position.workType == 0 && position.deliveryType == 1) {
                deliveryValueVesta += 1
            }
            if (position.workType == 0 && position.deliveryType == 1) {
                vestaMoney += position.cost
            }
            if (position.workType == 0 && position.deliveryType == 1 && position.payType == 0) {
                vestaCash += position.cost
            }
            if (position.workType == 0 && position.deliveryType == 1 && position.payType == 1) {
                vestaCard += position.cost
            }
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
            if (position.workType == 5 && position.deliveryType == 0) {
                prepay += position.cost
            }
            if (position.workType == 5 && position.deliveryType == 1) {
                holiday += position.cost
            }
            if (position.workType == 1 && position.deliveryType == 0 && position.moveFrom == 0) {
                loganMoveFromZhukova++
            }
            if (position.workType == 1 && position.deliveryType == 0 && position.moveFrom == 1) {
                loganMoveFromKulturi++
            }
            if (position.workType == 1 && position.deliveryType == 0 && position.moveFrom == 2) {
                loganMoveFromSedova++
            }
            if (position.workType == 1 && position.deliveryType == 0 && position.moveFrom == 3) {
                loganMoveFromHimikov++
            }
            if (position.workType == 1 && position.deliveryType == 0 && position.moveTo == 0) {
                loganMoveToZhukova++
            }
            if (position.workType == 1 && position.deliveryType == 0 && position.moveTo == 1) {
                loganMoveToKulturi++
            }
            if (position.workType == 1 && position.deliveryType == 0 && position.moveTo == 2) {
                loganMoveToSedova++
            }
            if (position.workType == 1 && position.deliveryType == 0 && position.moveTo == 3) {
                loganMoveToHimikov++
            }
            if (position.workType == 1 && position.deliveryType == 1 && position.moveFrom == 0) {
                vestaMoveFromZhukova++
            }
            if (position.workType == 1 && position.deliveryType == 1 && position.moveFrom == 1) {
                vestaMoveFromKulturi++
            }
            if (position.workType == 1 && position.deliveryType == 1 && position.moveFrom == 2) {
                vestaMoveFromSedova++
            }
            if (position.workType == 1 && position.deliveryType == 1 && position.moveFrom == 3) {
                vestaMoveFromHimikov++
            }
            if (position.workType == 1 && position.deliveryType == 1 && position.moveTo == 0) {
                vestaMoveToZhukova++
            }
            if (position.workType == 1 && position.deliveryType == 1 && position.moveTo == 1) {
                vestaMoveToKulturi++
            }
            if (position.workType == 1 && position.deliveryType == 1 && position.moveTo == 2) {
                vestaMoveToSedova++
            }
            if (position.workType == 1 && position.deliveryType == 1 && position.moveTo == 3) {
                vestaMoveToHimikov++
            }
            if (position.workType == 2 && position.deliveryType == 0 && position.moveFrom == 0) {
                loganTaskFromZhukova++
            }
            if (position.workType == 2 && position.deliveryType == 0 && position.moveFrom == 1) {
                loganTaskFromKulturi++
            }
            if (position.workType == 2 && position.deliveryType == 0 && position.moveFrom == 2) {
                loganTaskFromSedova++
            }
            if (position.workType == 2 && position.deliveryType == 0 && position.moveFrom == 3) {
                loganTaskFromHimikov++
            }
            if (position.workType == 2 && position.deliveryType == 0 && position.moveTo == 0) {
                loganTaskToZhukova++
            }
            if (position.workType == 2 && position.deliveryType == 0 && position.moveTo == 1) {
                loganTaskToKulturi++
            }
            if (position.workType == 2 && position.deliveryType == 0 && position.moveTo == 2) {
                loganTaskToSedova++
            }
            if (position.workType == 2 && position.deliveryType == 0 && position.moveTo == 3) {
                loganTaskToHimikov++
            }
            if (position.workType == 2 && position.deliveryType == 0 && position.moveTo == 4) {
                loganTaskElse++
            }
            if (position.workType == 2 && position.deliveryType == 1 && position.moveFrom == 0) {
                vestaTaskFromZhukova++
            }
            if (position.workType == 2 && position.deliveryType == 1 && position.moveFrom == 1) {
                vestaTaskFromKulturi++
            }
            if (position.workType == 2 && position.deliveryType == 1 && position.moveFrom == 2) {
                vestaTaskFromSedova++
            }
            if (position.workType == 2 && position.deliveryType == 1 && position.moveFrom == 3) {
                vestaTaskFromHimikov++
            }
            if (position.workType == 2 && position.deliveryType == 1 && position.moveTo == 0) {
                vestaTaskToZhukova++
            }
            if (position.workType == 2 && position.deliveryType == 1 && position.moveTo == 1) {
                vestaTaskToKulturi++
            }
            if (position.workType == 2 && position.deliveryType == 1 && position.moveTo == 2) {
                vestaTaskToSedova++
            }
            if (position.workType == 2 && position.deliveryType == 1 && position.moveTo == 3) {
                vestaTaskToHimikov++
            }
            if (position.workType == 2 && position.deliveryType == 1 && position.moveTo == 4) {
                vestaTaskElse++
            }
        }
        totalMoveToPay = loganMoveWithSalary + vestaMoveWithSalary
        totalTaskToPay = loganTaskWithSalary + vestaTaskWithSalary
        totalMove = totalMoveToPay + loganMoveWithOutSalary + vestaMoveWithOutSalary
        totalTask = totalTaskToPay + loganTaskWithOutSalary + vestaTaskWithOutSalary
        salary = (1700 + totalDeliveries * 50 + totalMoveToPay * 50 + totalTaskToPay * 50)
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
            total.morningFuel = prefs.morningFuel!! + 1
            total.eveningFuel = prefs.eveningFuel!! + 1
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
            total.prepay = prepay
            total.holidayPay = holiday
            total.expenses = day_tea_textVew.text.toString().toInt()
            total.deltaODO = deltaODO()
            total.carIndex = prefs.carPosition!!
            total.loganMoveFromZhukova = loganMoveFromZhukova
            total.loganMoveFromKulturi = loganMoveFromKulturi
            total.loganMoveFromSedova = loganMoveFromSedova
            total.loganMoveFromHimikov = loganMoveFromHimikov
            total.loganMoveToZhukova = loganMoveToZhukova
            total.loganMoveToKulturi = loganMoveToKulturi
            total.loganMoveToSedova = loganMoveToSedova
            total.loganMoveToHimikov = loganMoveToHimikov
            total.vestaMoveFromZhukova = vestaMoveFromZhukova
            total.vestaMoveFromKulturi = vestaMoveFromKulturi
            total.vestaMoveFromSedova = vestaMoveFromSedova
            total.vestaMoveFromHimikov = vestaMoveFromHimikov
            total.vestaMoveToZhukova = vestaMoveToZhukova
            total.vestaMoveToKulturi = vestaMoveToKulturi
            total.vestaMoveToSedova = vestaMoveToSedova
            total.vestaMoveToHimikov = vestaMoveToHimikov
            total.loganTaskFromZhukova = loganTaskFromZhukova
            total.loganTaskFromKulturi = loganTaskFromKulturi
            total.loganTaskFromSedova = loganTaskFromSedova
            total.loganTaskFromHimikov = loganTaskFromHimikov
            total.loganTaskToZhukova = loganTaskToZhukova
            total.loganTaskToKulturi = loganTaskToKulturi
            total.loganTaskToSedova = loganTaskToSedova
            total.loganTaskToHimikov = loganTaskToHimikov
            total.loganTaskElse = loganTaskElse
            total.vestaTaskFromZhukova = vestaTaskFromZhukova
            total.vestaTaskFromKulturi = vestaTaskFromKulturi
            total.vestaTaskFromSedova = vestaTaskFromSedova
            total.vestaTaskFromHimikov = vestaTaskFromHimikov
            total.vestaTaskToZhukova = vestaTaskToZhukova
            total.vestaTaskToKulturi = vestaTaskToKulturi
            total.vestaTaskToSedova = vestaTaskToSedova
            total.vestaTaskToHimikov = vestaTaskToHimikov
            total.vestaTaskElse = vestaTaskElse
            DB.getDao().addTotal(total)
            callBackActivity.fragmentPlace(TotalFragment())
            Snackbar.make(view!!, getString(R.string.total_calc_and_save), Snackbar.LENGTH_SHORT).show()
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
        day_expenses_textView.text =
            (total.expensesFuel + total.expensesWash + total.expensesOther).toString()
        day_expenses_fuel_textView.text = total.expensesFuel.toString()
        day_expenses_wash_textView.text = total.expensesWash.toString()
        day_expenses_other_textView.text = total.expensesOther.toString()
        day_total_deliveries_textVew.text = total.totalDeliveries.toString()
        day_logan_move_textView.text = total.loganMove.toString()
        day_logan_zhukova_move_textView.text = total.loganMoveToZhukova.toString()
        day_logan_kulturi_move_textView.text = total.loganMoveToKulturi.toString()
        day_logan_sedova_move_textView.text = total.loganMoveToSedova.toString()
        day_logan_himikov_move_textView.text = total.loganMoveToHimikov.toString()
        day_vesta_move_textView.text = total.vestaMove.toString()
        day_vesta_zhukova_move_textView.text = total.vestaMoveToZhukova.toString()
        day_vesta_kulturi_move_textView.text = total.vestaMoveToKulturi.toString()
        day_vesta_sedova_move_textView.text = total.vestaMoveToSedova.toString()
        day_vesta_himikov_move_textView.text = total.vestaMoveToHimikov.toString()
        day_total_move_textView.text = "${total.movesWithSalary}(${total.totalMove})"
        day_logan_task_textView.text = total.loganTask.toString()
        day_logan_zhukova_task_textView.text = total.loganTaskToZhukova.toString()
        day_logan_kulturi_task_textView.text = total.loganTaskToKulturi.toString()
        day_logan_sedova_task_textView.text = total.loganTaskToSedova.toString()
        day_logan_himikov_task_textView.text = total.loganTaskToHimikov.toString()
        day_logan_else_task_textView.text = total.loganTaskElse.toString()
        day_vesta_task_textView.text = total.vestaTask.toString()
        day_vesta_zhukova_task_textView.text = total.vestaTaskToZhukova.toString()
        day_vesta_kulturi_task_textView.text = total.vestaTaskToKulturi.toString()
        day_vesta_sedova_task_textView.text = total.vestaTaskToSedova.toString()
        day_vesta_himikov_task_textView.text = total.vestaTaskToHimikov.toString()
        day_vesta_else_task_textView.text = total.vestaTaskElse.toString()
        day_total_task_textView.text = "${total.tasksWithSalary}(${total.totalTask})"
        day_tea_textVew.text = total.expenses.toString()
        day_salary_textView.text = total.salary.toString()
        day_prepay_textVew.text = total.prepay.toString()
        day_holiday_textVew.text = total.holidayPay.toString()
    }

    private fun shareData() = GlobalScope.launch {
        var textToSend = "${day_car_textView.text} (${day_date_textView.text})\n" +
                "${resources.getString(R.string.odo_morning)} ${day_morning_odo_textView.text}\n" +
                "${resources.getString(R.string.odo_evening)} ${day_evening_odo_textView.text}\n" +
                "${resources.getString(R.string.fuel_morning)} ${day_morning_fuel_textView.text} ${resources.getString(
                    R.string.fuel_dividers
                )}\n" +
                "${resources.getString(R.string.fuel_evening)} ${day_evening_fuel_textView.text} ${resources.getString(
                    R.string.fuel_dividers
                )}\n" +
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
                "   ${resources.getString(R.string.logan_divider)}: ${day_logan_move_textView.text}\n" +
                "${resources.getString(R.string.shop_zhukova)} ${day_logan_zhukova_move_textView.text}\n" +
                "${resources.getString(R.string.shop_kulturi)} ${day_logan_kulturi_move_textView.text}\n" +
                "${resources.getString(R.string.shop_sedova)} ${day_logan_sedova_move_textView.text}\n" +
                "${resources.getString(R.string.shop_himikov)} ${day_logan_himikov_move_textView.text}\n" +
                "   ${resources.getString(R.string.vesta_divider)}: ${day_vesta_move_textView.text}\n" +
                "${resources.getString(R.string.shop_zhukova)} ${day_vesta_zhukova_move_textView.text}\n" +
                "${resources.getString(R.string.shop_kulturi)} ${day_vesta_kulturi_move_textView.text}\n" +
                "${resources.getString(R.string.shop_sedova)} ${day_vesta_sedova_move_textView.text}\n" +
                "${resources.getString(R.string.shop_himikov)} ${day_vesta_himikov_move_textView.text}\n" +
                "${resources.getString(R.string.total_total)} ${day_total_move_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.total_tasks)}\n" +
                "   ${resources.getString(R.string.logan_divider)}: ${day_logan_task_textView.text}\n" +
                "${resources.getString(R.string.shop_zhukova)} ${day_logan_zhukova_task_textView.text}\n" +
                "${resources.getString(R.string.shop_kulturi)} ${day_logan_kulturi_task_textView.text}\n" +
                "${resources.getString(R.string.shop_sedova)} ${day_logan_sedova_task_textView.text}\n" +
                "${resources.getString(R.string.shop_himikov)} ${day_logan_himikov_task_textView.text}\n" +
                "${resources.getString(R.string.switch_else)}: ${day_logan_else_task_textView.text}\n" +
                "   ${resources.getString(R.string.vesta_divider)}: ${day_vesta_task_textView.text}\n" +
                "${resources.getString(R.string.shop_zhukova)} ${day_vesta_zhukova_task_textView.text}\n" +
                "${resources.getString(R.string.shop_kulturi)} ${day_vesta_kulturi_task_textView.text}\n" +
                "${resources.getString(R.string.shop_sedova)} ${day_vesta_sedova_task_textView.text}\n" +
                "${resources.getString(R.string.shop_himikov)} ${day_vesta_himikov_task_textView.text}\n" +
                "${resources.getString(R.string.switch_else)}: ${day_vesta_else_task_textView.text}\n" +
                "${resources.getString(R.string.total_total)} ${day_total_task_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.expenses)}\n" +
                "${resources.getString(R.string.total_total)} ${day_expenses_textView.text}\n" +
                "${resources.getString(R.string.fuel)}: ${day_expenses_fuel_textView.text}\n" +
                "${resources.getString(R.string.wash)}: ${day_expenses_wash_textView.text}\n" +
                "${resources.getString(R.string.other)}: ${day_expenses_other_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.salary)} ${prefs.family} ${day_salary_textView.text}"
        if (day_prepay_textVew.text.toString().toInt() != 0) {
            textToSend += "\n" +
                    "${resources.getString(R.string.prepay)} ${day_prepay_textVew.text}"
        }
        if (day_holiday_textVew.text.toString().toInt() != 0) {
            textToSend += "\n" +
                    "${resources.getString(R.string.holiday_pay)} ${day_holiday_textVew.text}"
        }
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, textToSend)
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, resources.getString(R.string.share)))
    }
}
