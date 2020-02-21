package com.parsiphal.loganshopdriverhelper.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parsiphal.loganshopdriverhelper.DB

import com.parsiphal.loganshopdriverhelper.R
import com.parsiphal.loganshopdriverhelper.data.*
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import com.parsiphal.loganshopdriverhelper.prefs
import kotlinx.android.synthetic.main.fragment_total_day.*
import kotlinx.coroutines.*
import moxy.MvpAppCompatFragment

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
    private var extraPay = 0
    private var qualityPay = 0
    private var penalty = 0
    private var loganMoveFromZhukova = 0
    private var loganMoveFromKulturi = 0
    private var loganMoveFromSedova = 0
    private var loganMoveFromHimikov = 0
    private var loganMoveFromPlanernaya = 0
    private var loganMoveToZhukova = 0
    private var loganMoveToKulturi = 0
    private var loganMoveToSedova = 0
    private var loganMoveToHimikov = 0
    private var loganMoveToPlanernaya = 0
    private var vestaMoveFromZhukova = 0
    private var vestaMoveFromKulturi = 0
    private var vestaMoveFromSedova = 0
    private var vestaMoveFromHimikov = 0
    private var vestaMoveFromPlanernaya = 0
    private var vestaMoveToZhukova = 0
    private var vestaMoveToKulturi = 0
    private var vestaMoveToSedova = 0
    private var vestaMoveToHimikov = 0
    private var vestaMoveToPlanernaya = 0
    private var loganTaskFromZhukova = 0
    private var loganTaskFromKulturi = 0
    private var loganTaskFromSedova = 0
    private var loganTaskFromHimikov = 0
    private var loganTaskFromPlanenaya = 0
    private var loganTaskToZhukova = 0
    private var loganTaskToKulturi = 0
    private var loganTaskToSedova = 0
    private var loganTaskToHimikov = 0
    private var loganTaskToPlanernaya = 0
    private var loganTaskElse = 0
    private var vestaTaskFromZhukova = 0
    private var vestaTaskFromKulturi = 0
    private var vestaTaskFromSedova = 0
    private var vestaTaskFromHimikov = 0
    private var vestaTaskFromPlanernaya = 0
    private var vestaTaskToZhukova = 0
    private var vestaTaskToKulturi = 0
    private var vestaTaskToSedova = 0
    private var vestaTaskToHimikov = 0
    private var vestaTaskToPlanernaya = 0
    private var vestaTaskElse = 0

    override fun onAttach(context: Context) {
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
//                saveData()
                day_progress.visibility = View.GONE
                day_data.visibility = View.VISIBLE
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
        day_logan_planernaya_move_textView.text = loganMoveToPlanernaya.toString()
        day_vesta_move_textView.text = (vestaMoveWithSalary + vestaMoveWithOutSalary).toString()
        day_vesta_zhukova_move_textView.text = vestaMoveToZhukova.toString()
        day_vesta_kulturi_move_textView.text = vestaMoveToKulturi.toString()
        day_vesta_sedova_move_textView.text = vestaMoveToSedova.toString()
        day_vesta_himikov_move_textView.text = vestaMoveToHimikov.toString()
        day_vesta_planernaya_move_textView.text = vestaMoveToPlanernaya.toString()
        day_total_move_textView.text = "${totalMoveToPay}(${totalMove})"
        day_logan_task_textView.text = (loganTaskWithSalary + loganTaskWithOutSalary).toString()
        day_logan_zhukova_task_textView.text = loganTaskToZhukova.toString()
        day_logan_kulturi_task_textView.text = loganTaskToKulturi.toString()
        day_logan_sedova_task_textView.text = loganTaskToSedova.toString()
        day_logan_himikov_task_textView.text = loganTaskToHimikov.toString()
        day_logan_planernaya_task_textView.text = loganTaskToPlanernaya.toString()
        day_logan_else_task_textView.text = loganTaskElse.toString()
        day_vesta_task_textView.text = (vestaTaskWithSalary + vestaTaskWithOutSalary).toString()
        day_vesta_zhukova_task_textView.text = vestaTaskToZhukova.toString()
        day_vesta_kulturi_task_textView.text = vestaTaskToKulturi.toString()
        day_vesta_sedova_task_textView.text = vestaTaskToSedova.toString()
        day_vesta_himikov_task_textView.text = vestaTaskToHimikov.toString()
        day_vesta_planernaya_task_textView.text = vestaTaskToPlanernaya.toString()
        day_vesta_else_task_textView.text = vestaTaskElse.toString()
        day_total_task_textView.text = "${totalTaskToPay}(${totalTask})"
        day_expenses_textView.text = (expenseFuel + expenseWash + expenseOther).toString()
        day_expenses_fuel_textView.text = expenseFuel.toString()
        day_expenses_wash_textView.text = expenseWash.toString()
        day_expenses_other_textView.text = expenseOther.toString()
        day_salary_textView.text = salary.toString()
        day_prepay_textVew.text = prepay.toString()
        day_holiday_textVew.text = holiday.toString()
        day_extraPay_textView.text = extraPay.toString()
        day_qualityPay_textView.text = qualityPay.toString()
        day_penalty_textView.text = penalty.toString()
    }

    private fun calculateSum() {
        for (position in items) {
            if (position.workType == WorkType.Delivery.i) {
                totalDeliveries++
                teaMoney += position.expense
                totalMoney += position.cost
            }
            if (position.workType == WorkType.Delivery.i && position.payType == PayType.Cash.i) {
                totalCash += position.cost
            }
            if (position.workType == WorkType.Delivery.i && position.payType == PayType.Card.i) {
                totalCard += position.cost
            }
            if (position.workType == WorkType.Delivery.i && position.deliveryType == DeliveryType.Logan.i) {
                deliveryValueLogan += 1
                loganMoney += position.cost
            }
            if (position.workType == WorkType.Delivery.i && position.deliveryType == DeliveryType.Logan.i && position.payType == PayType.Cash.i) {
                loganCash += position.cost
            }
            if (position.workType == WorkType.Delivery.i && position.deliveryType == DeliveryType.Logan.i && position.payType == PayType.Card.i) {
                loganCard += position.cost
            }
            if (position.workType == WorkType.Delivery.i && position.deliveryType == DeliveryType.Vesta.i) {
                deliveryValueVesta += 1
                vestaMoney += position.cost
            }
            if (position.workType == WorkType.Delivery.i && position.deliveryType == DeliveryType.Vesta.i && position.payType == PayType.Cash.i) {
                vestaCash += position.cost
            }
            if (position.workType == WorkType.Delivery.i && position.deliveryType == DeliveryType.Vesta.i && position.payType == PayType.Card.i) {
                vestaCard += position.cost
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Logan.i && position.ifSalary == 1) {
                loganMoveWithSalary++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Vesta.i && position.ifSalary == 1) {
                vestaMoveWithSalary++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Logan.i && position.ifSalary == 1) {
                loganTaskWithSalary++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Vesta.i && position.ifSalary == 1) {
                vestaTaskWithSalary++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Logan.i && position.ifSalary == 0) {
                loganMoveWithOutSalary++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Vesta.i && position.ifSalary == 0) {
                vestaMoveWithOutSalary++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Logan.i && position.ifSalary == 0) {
                loganTaskWithOutSalary++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Vesta.i && position.ifSalary == 0) {
                vestaTaskWithOutSalary++
            }
            if (position.workType == WorkType.Expense.i && position.expenseType == Expenses.Fuel.i) {
                expenseFuel += position.cost
            }
            if (position.workType == WorkType.Expense.i && position.expenseType == Expenses.Wash.i) {
                expenseWash += position.cost
            }
            if (position.workType == WorkType.Expense.i && position.expenseType == Expenses.Other.i) {
                expenseOther += position.cost
            }
            if (position.workType == WorkType.Salary.i && position.deliveryType == SalaryType.Prepay.i) {
                prepay += position.cost
            }
            if (position.workType == WorkType.Salary.i && position.deliveryType == SalaryType.Holiday.i) {
                holiday += position.cost
            }
            if (position.workType == WorkType.Salary.i && position.deliveryType == SalaryType.Extra.i) {
                extraPay += position.cost
            }
            if (position.workType == WorkType.Salary.i && position.deliveryType == SalaryType.Quality.i) {
                qualityPay += position.cost
            }
            if (position.workType == WorkType.Salary.i && position.deliveryType == SalaryType.Penalty.i) {
                penalty += position.cost
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Logan.i && position.moveFrom == Shops.Zhukova.i) {
                loganMoveFromZhukova++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Logan.i && position.moveFrom == Shops.Kulturi.i) {
                loganMoveFromKulturi++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Logan.i && position.moveFrom == Shops.Sedova.i) {
                loganMoveFromSedova++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Logan.i && position.moveFrom == Shops.Himikov.i) {
                loganMoveFromHimikov++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Logan.i && position.moveFrom == Shops.Planernaya.i) {
                loganMoveFromPlanernaya++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Logan.i && position.moveTo == Shops.Zhukova.i) {
                loganMoveToZhukova++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Logan.i && position.moveTo == Shops.Kulturi.i) {
                loganMoveToKulturi++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Logan.i && position.moveTo == Shops.Sedova.i) {
                loganMoveToSedova++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Logan.i && position.moveTo == Shops.Himikov.i) {
                loganMoveToHimikov++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Logan.i && position.moveTo == Shops.Planernaya.i) {
                loganMoveToPlanernaya++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Vesta.i && position.moveFrom == Shops.Zhukova.i) {
                vestaMoveFromZhukova++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Vesta.i && position.moveFrom == Shops.Kulturi.i) {
                vestaMoveFromKulturi++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Vesta.i && position.moveFrom == Shops.Sedova.i) {
                vestaMoveFromSedova++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Vesta.i && position.moveFrom == Shops.Himikov.i) {
                vestaMoveFromHimikov++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Vesta.i && position.moveFrom == Shops.Planernaya.i) {
                vestaMoveFromPlanernaya++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Vesta.i && position.moveTo == Shops.Zhukova.i) {
                vestaMoveToZhukova++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Vesta.i && position.moveTo == Shops.Kulturi.i) {
                vestaMoveToKulturi++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Vesta.i && position.moveTo == Shops.Sedova.i) {
                vestaMoveToSedova++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Vesta.i && position.moveTo == Shops.Himikov.i) {
                vestaMoveToHimikov++
            }
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Vesta.i && position.moveTo == Shops.Planernaya.i) {
                vestaMoveToPlanernaya++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Logan.i && position.moveFrom == Shops.Zhukova.i) {
                loganTaskFromZhukova++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Logan.i && position.moveFrom == Shops.Kulturi.i) {
                loganTaskFromKulturi++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Logan.i && position.moveFrom == Shops.Sedova.i) {
                loganTaskFromSedova++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Logan.i && position.moveFrom == Shops.Himikov.i) {
                loganTaskFromHimikov++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Logan.i && position.moveFrom == Shops.Planernaya.i) {
                loganTaskFromPlanenaya++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Logan.i && position.moveTo == Shops.Zhukova.i) {
                loganTaskToZhukova++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Logan.i && position.moveTo == Shops.Kulturi.i) {
                loganTaskToKulturi++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Logan.i && position.moveTo == Shops.Sedova.i) {
                loganTaskToSedova++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Logan.i && position.moveTo == Shops.Himikov.i) {
                loganTaskToHimikov++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Logan.i && position.moveTo == Shops.Planernaya.i) {
                loganTaskToPlanernaya++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Logan.i && position.moveTo == Shops.Other.i) {
                loganTaskElse++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Vesta.i && position.moveFrom == Shops.Zhukova.i) {
                vestaTaskFromZhukova++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Vesta.i && position.moveFrom == Shops.Kulturi.i) {
                vestaTaskFromKulturi++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Vesta.i && position.moveFrom == Shops.Sedova.i) {
                vestaTaskFromSedova++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Vesta.i && position.moveFrom == Shops.Himikov.i) {
                vestaTaskFromHimikov++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Vesta.i && position.moveFrom == Shops.Planernaya.i) {
                vestaTaskFromPlanernaya++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Vesta.i && position.moveTo == Shops.Zhukova.i) {
                vestaTaskToZhukova++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Vesta.i && position.moveTo == Shops.Kulturi.i) {
                vestaTaskToKulturi++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Vesta.i && position.moveTo == Shops.Sedova.i) {
                vestaTaskToSedova++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Vesta.i && position.moveTo == Shops.Himikov.i) {
                vestaTaskToHimikov++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Vesta.i && position.moveTo == Shops.Planernaya.i) {
                vestaTaskToPlanernaya++
            }
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Vesta.i && position.moveTo == Shops.Other.i) {
                vestaTaskElse++
            }
        }
        totalMoveToPay = loganMoveWithSalary + vestaMoveWithSalary
        totalTaskToPay = loganTaskWithSalary + vestaTaskWithSalary
        totalMove = totalMoveToPay + loganMoveWithOutSalary + vestaMoveWithOutSalary
        totalTask = totalTaskToPay + loganTaskWithOutSalary + vestaTaskWithOutSalary
        salary = if (prefs.status == 0) {
            (1700 + totalDeliveries * 50 + totalMoveToPay * 50 + totalTaskToPay * 50 - penalty)
        } else {
            1500
        }
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
            total.extraPay = extraPay
            total.qualityPay = qualityPay
            total.penalty = penalty
            total.expenses = day_tea_textVew.text.toString().toInt()
            total.deltaODO = deltaODO()
            total.carIndex = prefs.carPosition!!

            total.loganMoveFromZhukova = loganMoveFromZhukova
            total.loganMoveFromKulturi = loganMoveFromKulturi
            total.loganMoveFromSedova = loganMoveFromSedova
            total.loganMoveFromHimikov = loganMoveFromHimikov
            total.loganMoveFromPlanernaya = loganMoveFromPlanernaya

            total.loganMoveToZhukova = loganMoveToZhukova
            total.loganMoveToKulturi = loganMoveToKulturi
            total.loganMoveToSedova = loganMoveToSedova
            total.loganMoveToHimikov = loganMoveToHimikov
            total.loganMoveToPlanernaya = loganMoveToPlanernaya

            total.vestaMoveFromZhukova = vestaMoveFromZhukova
            total.vestaMoveFromKulturi = vestaMoveFromKulturi
            total.vestaMoveFromSedova = vestaMoveFromSedova
            total.vestaMoveFromHimikov = vestaMoveFromHimikov
            total.vestaMoveFromPlanernaya = vestaMoveFromPlanernaya

            total.vestaMoveToZhukova = vestaMoveToZhukova
            total.vestaMoveToKulturi = vestaMoveToKulturi
            total.vestaMoveToSedova = vestaMoveToSedova
            total.vestaMoveToHimikov = vestaMoveToHimikov
            total.vestaMoveToPlanernaya = vestaMoveToPlanernaya

            total.loganTaskFromZhukova = loganTaskFromZhukova
            total.loganTaskFromKulturi = loganTaskFromKulturi
            total.loganTaskFromSedova = loganTaskFromSedova
            total.loganTaskFromHimikov = loganTaskFromHimikov
            total.loganTaskFromPlanernaya = loganTaskFromPlanenaya

            total.loganTaskToZhukova = loganTaskToZhukova
            total.loganTaskToKulturi = loganTaskToKulturi
            total.loganTaskToSedova = loganTaskToSedova
            total.loganTaskToHimikov = loganTaskToHimikov
            total.loganTaskToPlanernaya = loganTaskToPlanernaya

            total.loganTaskElse = loganTaskElse
            total.vestaTaskFromZhukova = vestaTaskFromZhukova
            total.vestaTaskFromKulturi = vestaTaskFromKulturi
            total.vestaTaskFromSedova = vestaTaskFromSedova
            total.vestaTaskFromHimikov = vestaTaskFromHimikov
            total.vestaTaskFromPlanernaya = vestaTaskFromPlanernaya

            total.vestaTaskToZhukova = vestaTaskToZhukova
            total.vestaTaskToKulturi = vestaTaskToKulturi
            total.vestaTaskToSedova = vestaTaskToSedova
            total.vestaTaskToHimikov = vestaTaskToHimikov
            total.vestaTaskToPlanernaya = vestaTaskToPlanernaya

            total.vestaTaskElse = vestaTaskElse
            DB.getDao().addTotal(total)
            callBackActivity.fragmentPlace(TotalFragment())
            Snackbar.make(view!!, getString(R.string.total_calc_and_save), Snackbar.LENGTH_SHORT)
                .show()
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
        day_logan_planernaya_move_textView.text = total.loganMoveToPlanernaya.toString()

        day_vesta_move_textView.text = total.vestaMove.toString()

        day_vesta_zhukova_move_textView.text = total.vestaMoveToZhukova.toString()
        day_vesta_kulturi_move_textView.text = total.vestaMoveToKulturi.toString()
        day_vesta_sedova_move_textView.text = total.vestaMoveToSedova.toString()
        day_vesta_himikov_move_textView.text = total.vestaMoveToHimikov.toString()
        day_vesta_planernaya_move_textView.text = total.vestaMoveToPlanernaya.toString()

        day_total_move_textView.text = "${total.movesWithSalary}(${total.totalMove})"
        day_logan_task_textView.text = total.loganTask.toString()

        day_logan_zhukova_task_textView.text = total.loganTaskToZhukova.toString()
        day_logan_kulturi_task_textView.text = total.loganTaskToKulturi.toString()
        day_logan_sedova_task_textView.text = total.loganTaskToSedova.toString()
        day_logan_himikov_task_textView.text = total.loganTaskToHimikov.toString()
        day_logan_planernaya_task_textView.text = total.loganTaskToPlanernaya.toString()
        day_logan_else_task_textView.text = total.loganTaskElse.toString()

        day_vesta_task_textView.text = total.vestaTask.toString()

        day_vesta_zhukova_task_textView.text = total.vestaTaskToZhukova.toString()
        day_vesta_kulturi_task_textView.text = total.vestaTaskToKulturi.toString()
        day_vesta_sedova_task_textView.text = total.vestaTaskToSedova.toString()
        day_vesta_himikov_task_textView.text = total.vestaTaskToHimikov.toString()
        day_vesta_planernaya_task_textView.text = total.vestaTaskToPlanernaya.toString()
        day_vesta_else_task_textView.text = total.vestaTaskElse.toString()

        day_total_task_textView.text = "${total.tasksWithSalary}(${total.totalTask})"
        day_tea_textVew.text = total.expenses.toString()
        day_salary_textView.text = total.salary.toString()
        day_prepay_textVew.text = total.prepay.toString()
        day_holiday_textVew.text = total.holidayPay.toString()
        day_extraPay_textView.text = total.extraPay.toString()
        day_qualityPay_textView.text = total.qualityPay.toString()
        day_penalty_textView.text = total.penalty.toString()
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
                "${resources.getString(R.string.cash)}: ${day_logan_cash_textView.text}\n" +
                "${resources.getString(R.string.card)}: ${day_logan_card_textView.text}\n" +
                "   ${resources.getString(R.string.vesta_divider)}\n" +
                "${resources.getString(R.string.money)}/${resources.getString(R.string.deliveryValue)}: " +
                "${day_vesta_money_textView.text}/${day_vesta_delivery_value_textView.text}\n" +
                "${resources.getString(R.string.cash)}: ${day_vesta_cash_textView.text}\n" +
                "${resources.getString(R.string.card)}: ${day_vesta_card_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.total_moves)}\n" +
                "   ${resources.getString(R.string.logan_divider)}: ${day_logan_move_textView.text}\n" +
                "${resources.getString(R.string.shop_zhukova)}: ${day_logan_zhukova_move_textView.text}\n" +
                "${resources.getString(R.string.shop_kulturi)}: ${day_logan_kulturi_move_textView.text}\n" +
                "${resources.getString(R.string.shop_planernaya)}: ${day_logan_planernaya_move_textView.text}\n" +
                "${resources.getString(R.string.shop_sedova)}: ${day_logan_sedova_move_textView.text}\n" +
                "${resources.getString(R.string.shop_himikov)}: ${day_logan_himikov_move_textView.text}\n" +
                "   ${resources.getString(R.string.vesta_divider)}: ${day_vesta_move_textView.text}\n" +
                "${resources.getString(R.string.shop_zhukova)}: ${day_vesta_zhukova_move_textView.text}\n" +
                "${resources.getString(R.string.shop_kulturi)}: ${day_vesta_kulturi_move_textView.text}\n" +
                "${resources.getString(R.string.shop_planernaya)}: ${day_vesta_planernaya_move_textView.text}\n" +
                "${resources.getString(R.string.shop_sedova)}: ${day_vesta_sedova_move_textView.text}\n" +
//                "${resources.getString(R.string.shop_himikov)}: ${day_vesta_himikov_move_textView.text}\n" +
                "${resources.getString(R.string.total_total)} ${day_total_move_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.total_tasks)}\n" +
                "   ${resources.getString(R.string.logan_divider)}: ${day_logan_task_textView.text}\n" +
                "${resources.getString(R.string.shop_zhukova)}: ${day_logan_zhukova_task_textView.text}\n" +
                "${resources.getString(R.string.shop_kulturi)}: ${day_logan_kulturi_task_textView.text}\n" +
                "${resources.getString(R.string.shop_planernaya)}: ${day_logan_planernaya_task_textView.text}\n" +
                "${resources.getString(R.string.shop_sedova)}: ${day_logan_sedova_task_textView.text}\n" +
                "${resources.getString(R.string.shop_himikov)}: ${day_logan_himikov_task_textView.text}\n" +
                "${resources.getString(R.string.switch_else)}: ${day_logan_else_task_textView.text}\n" +
                "   ${resources.getString(R.string.vesta_divider)}: ${day_vesta_task_textView.text}\n" +
                "${resources.getString(R.string.shop_zhukova)}: ${day_vesta_zhukova_task_textView.text}\n" +
                "${resources.getString(R.string.shop_kulturi)}: ${day_vesta_kulturi_task_textView.text}\n" +
                "${resources.getString(R.string.shop_planernaya)}: ${day_vesta_planernaya_task_textView.text}\n" +
                "${resources.getString(R.string.shop_sedova)}: ${day_vesta_sedova_task_textView.text}\n" +
//                "${resources.getString(R.string.shop_himikov)}: ${day_vesta_himikov_task_textView.text}\n" +
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
                    "${resources.getString(R.string.prepay)}: ${day_prepay_textVew.text}"
        }
        if (day_holiday_textVew.text.toString().toInt() != 0) {
            textToSend += "\n" +
                    "${resources.getString(R.string.holiday_pay)}: ${day_holiday_textVew.text}"
        }
        if (day_qualityPay_textView.text.toString().toInt() != 0) {
            textToSend += "\n" +
                    "${resources.getText(R.string.qualityPay)}: ${day_qualityPay_textView.text}"
        }
        if (day_penalty_textView.text.toString().toInt() != 0) {
            textToSend += "\n" +
                    "${resources.getText(R.string.penalty)}: ${day_penalty_textView.text}"
        }
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, textToSend)
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, resources.getString(R.string.share)))
    }
}
