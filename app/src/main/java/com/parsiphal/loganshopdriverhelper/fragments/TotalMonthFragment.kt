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
import com.parsiphal.loganshopdriverhelper.data.Cars
import com.parsiphal.loganshopdriverhelper.data.Total
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import com.parsiphal.loganshopdriverhelper.prefs
import kotlinx.android.synthetic.main.fragment_total_month.*
import kotlinx.coroutines.*
import moxy.MvpAppCompatFragment
import java.lang.Exception

class TotalMonthFragment : MvpAppCompatFragment() {

    private var totals: List<Total> = ArrayList()
    private lateinit var total: Total
    private var newTotal = true
    private lateinit var callBackActivity: MainView
    private var totalShifts = 0
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
    private var prepay = 0
    private var holiday = 0
    private var salary = 0
    private var extraPay = 0
    private var penalty=0
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
    private var largusExpenseFuel = 0
    private var largusExpenseWash = 0
    private var largusExpenseOther = 0
    private var largusExpenseTotal = 0
    private var sanderoExpenseFuel = 0
    private var sanderoExpenseWash = 0
    private var sanderoExpenseOther = 0
    private var sanderoExpenseTotal = 0
    private var xRayExpenseFuel = 0
    private var xRayExpenseWash = 0
    private var xRayExpenseOther = 0
    private var xRayExpenseTotal = 0

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
        return inflater.inflate(R.layout.fragment_total_month, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (newTotal) {
            val search =
                "${prefs.date!![2]}${prefs.date!![3]}${prefs.date!![4]}${prefs.date!![5]}${prefs.date!![6]}${prefs.date!![7]}${prefs.date!![8]}${prefs.date!![9]}"
            val data = GlobalScope.async {
                getData(search)
                calculateSum()
                calculateExpences()
            }
            MainScope().launch {
                data.await()
                setData()
                placeExpenses()
                delay(1000)
//                saveData()
                month_progress.visibility = View.GONE
                month_data.visibility = View.VISIBLE
            }
            month_share.visibility = View.GONE
        } else {
            val search = "${total.date[3]}${total.date[4]}${total.date[5]}${total.date[6]}${total.date[7]}${total.date[8]}${total.date[9]}"
            val data = GlobalScope.async {
                getData(search)
                calculateExpences()
            }
            MainScope().launch {
                data.await()
                placeData()
                placeExpenses()
                month_progress.visibility = View.GONE
                month_data.visibility = View.VISIBLE
            }
            month_write.visibility = View.GONE
        }
        month_write.setOnClickListener {
            saveData()
        }
        month_share.setOnClickListener {
            shareDate()
        }
    }

    private fun getData(search: String) {
        totals = DB.getDao().getTotalsByMonth("%$search%")
    }

    private fun setData() {
        month_month.text = "${prefs.date!![3]}${prefs.date!![4]}"
        month_year.text = "${prefs.date!![6]}${prefs.date!![7]}${prefs.date!![8]}${prefs.date!![9]}"
        month_family_textView.text = prefs.family
        month_total_shifts_value_textView.text = totalShifts.toString()
        month_total_money_textView.text = totalMoney.toString()
        month_total_cash_textView.text = totalCash.toString()
        month_total_card_textView.text = totalCard.toString()
        month_total_delivery_value_logan_textView.text = deliveryValueLogan.toString()
        month_logan_money_textView.text = loganMoney.toString()
        month_logan_cash_textView.text = loganCash.toString()
        month_logan_card_textView.text = loganCard.toString()
        month_total_delivery_value_vesta_textView.text = deliveryValueVesta.toString()
        month_vesta_money_textView.text = vestaMoney.toString()
        month_vesta_cash_textView.text = vestaCash.toString()
        month_vesta_card_textView.text = vestaCard.toString()
        month_prepay_textView.text = prepay.toString()
        month_holiday_pay_textView.text = holiday.toString()
        month_largus_count_textView.text = largusShifts.toString()
        month_sandero_count_textView.text = sanderoShifts.toString()
        month_xray_count_textView.text = xrayShifts.toString()
        month_total_delivery_value_textView.text = totalDeliveries.toString()
        month_logan_move_textView.text = loganMove.toString()
        month_logan_zhukova_move_textView.text = loganMoveToZhukova.toString()
        month_logan_kulturi_move_textView.text = loganMoveToKulturi.toString()
        month_logan_sedova_move_textView.text = loganMoveToSedova.toString()
        month_logan_himikov_move_textView.text = loganMoveToHimikov.toString()
        month_vesta_move_textView.text = vestaMove.toString()
        month_vesta_zhukova_move_textView.text = vestaMoveToZhukova.toString()
        month_vesta_kulturi_move_textView.text = vestaMoveToKulturi.toString()
        month_vesta_sedova_move_textView.text = vestaMoveToSedova.toString()
        month_vesta_himikov_move_textView.text = vestaMoveToHimikov.toString()
        month_total_move_textView.text = "${totalMoveWithSalary}(${totalMove})"
        month_logan_task_textView.text = loganTask.toString()
        month_logan_zhukova_task_textView.text = loganTaskToZhukova.toString()
        month_logan_kulturi_task_textView.text = loganTaskToKulturi.toString()
        month_logan_sedova_task_textView.text = loganTaskToSedova.toString()
        month_logan_himikov_task_textView.text = loganTaskToHimikov.toString()
        month_logan_else_task_textView.text = loganTaskElse.toString()
        month_vesta_task_textView.text = vestaTask.toString()
        month_vesta_zhukova_task_textView.text = vestaTaskToZhukova.toString()
        month_vesta_kulturi_task_textView.text = vestaTaskToKulturi.toString()
        month_vesta_sedova_task_textView.text = vestaTaskToSedova.toString()
        month_vesta_himikov_task_textView.text = vestaTaskToHimikov.toString()
        month_vesta_else_task_textView.text = vestaTaskElse.toString()
        month_total_task_textView.text = "${totalTaskWithSalary}(${totalTask})"
        month_salary_textView.text = salary.toString()
        month_tea_textView.text = teaMoney.toString()
        month_extraPay_textView.text = extraPay.toString()
        month_penalty_textView.text=penalty.toString()

        try {
            month_mid_salary_textView.text = if (totalShifts != 0) {
                "${salary / totalShifts}"
            } else "0"
            month_to_recieve_textView.text =
                "${salary - month_prepay_textView.text.toString().toInt()}"
        } catch (e: Exception) {
            e.printStackTrace()
            Snackbar.make(view!!, getString(R.string.error), Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun calculateSum() {
        totalShifts = totals.size
        for (position in totals) {
            when {
                position.carIndex == Cars.Largus.i -> largusShifts++
                position.carIndex == Cars.Sandero.i -> sanderoShifts++
                position.carIndex == Cars.XRay.i -> xrayShifts++
            }
            totalMoney += position.totalMoney
            totalCash += position.totalCash
            totalCard += position.totalCard
            deliveryValueLogan += position.loganDeliveryValue
            loganMoney += position.loganMoney
            loganCash += position.loganCash
            loganCard += position.loganCard
            deliveryValueVesta += position.vestaDeliveryValue
            vestaMoney += position.vestaMoney
            vestaCash += position.vestaCash
            vestaCard += position.vestaCard
            prepay += position.prepay
            holiday += position.holidayPay
            extraPay += position.extraPay
            penalty+=position.penalty
            deltaODO += position.deltaODO
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
            loganMoveFromZhukova += position.loganMoveFromZhukova
            loganMoveFromKulturi += position.loganMoveFromKulturi
            loganMoveFromSedova += position.loganMoveFromSedova
            loganMoveFromHimikov += position.loganMoveFromHimikov
            loganMoveToZhukova += position.loganMoveToZhukova
            loganMoveToKulturi += position.loganMoveToKulturi
            loganMoveToSedova += position.loganMoveToSedova
            loganMoveToHimikov += position.loganMoveToHimikov
            vestaMoveFromZhukova += position.vestaMoveFromZhukova
            vestaMoveFromKulturi += position.vestaMoveFromKulturi
            vestaMoveFromSedova += position.vestaMoveFromSedova
            vestaMoveFromHimikov += position.vestaMoveFromHimikov
            vestaMoveToZhukova += position.vestaMoveToZhukova
            vestaMoveToKulturi += position.vestaMoveToKulturi
            vestaMoveToSedova += position.vestaMoveToSedova
            vestaMoveToHimikov += position.vestaMoveToHimikov
            loganTaskFromZhukova += position.loganTaskFromZhukova
            loganTaskFromKulturi += position.loganTaskFromKulturi
            loganTaskFromSedova += position.loganTaskFromSedova
            loganTaskFromHimikov += position.loganTaskFromHimikov
            loganTaskToZhukova += position.loganTaskToZhukova
            loganTaskToKulturi += position.loganTaskToKulturi
            loganTaskToSedova += position.loganTaskToSedova
            loganTaskToHimikov += position.loganTaskToHimikov
            loganTaskElse += position.loganTaskElse
            vestaTaskFromZhukova += position.vestaTaskFromZhukova
            vestaTaskFromKulturi += position.vestaTaskFromKulturi
            vestaTaskFromSedova += position.vestaTaskFromSedova
            vestaTaskFromHimikov += position.vestaTaskFromHimikov
            vestaTaskToZhukova += position.vestaTaskToZhukova
            vestaTaskToKulturi += position.vestaTaskToKulturi
            vestaTaskToSedova += position.vestaTaskToSedova
            vestaTaskToHimikov += position.vestaTaskToHimikov
            vestaTaskElse += position.vestaTaskElse
        }
    }

    private fun calculateExpences() {
        for (position in totals) {
            when (position.carIndex) {
                Cars.Largus.i -> {
                    largusExpenseFuel += position.expensesFuel
                    largusExpenseWash += position.expensesWash
                    largusExpenseOther += position.expensesOther
                }
                Cars.Sandero.i -> {
                    sanderoExpenseFuel += position.expensesFuel
                    sanderoExpenseWash += position.expensesWash
                    sanderoExpenseOther += position.expensesOther
                }
                Cars.XRay.i -> {
                    xRayExpenseFuel += position.expensesFuel
                    xRayExpenseWash += position.expensesWash
                    xRayExpenseOther += position.expensesOther
                }
            }
        }
        largusExpenseTotal = largusExpenseFuel + largusExpenseWash + largusExpenseOther
        sanderoExpenseTotal = sanderoExpenseFuel + sanderoExpenseWash + sanderoExpenseOther
        xRayExpenseTotal = xRayExpenseFuel + xRayExpenseWash + xRayExpenseOther
    }

    private fun placeExpenses() {
        month_largus_total_expenses_textView.text = largusExpenseTotal.toString()
        month_largus_expenses_fuel_textView.text = largusExpenseFuel.toString()
        month_largus_expenses_wash_textView.text = largusExpenseWash.toString()
        month_largus_expenses_other_textView.text = largusExpenseOther.toString()
        month_sandero_total_expenses_textView.text = sanderoExpenseTotal.toString()
        month_sandero_expenses_fuel_textView.text = sanderoExpenseFuel.toString()
        month_sandero_expenses_wash_textView.text = sanderoExpenseWash.toString()
        month_sandero_expenses_other_textView.text = sanderoExpenseOther.toString()
        month_xray_total_expenses_textView.text = xRayExpenseTotal.toString()
        month_xray_expenses_fuel_textView.text = xRayExpenseFuel.toString()
        month_xray_expenses_wash_textView.text = xRayExpenseWash.toString()
        month_xray_expenses_other_textView.text = xRayExpenseOther.toString()
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
            total.extraPay = month_extraPay_textView.text.toString().toInt()
            total.penalty=month_penalty_textView.text.toString().toInt()
            total.deltaODO = deltaODO
            total.prepay = month_prepay_textView.text.toString().toInt()
            total.holidayPay = month_holiday_pay_textView.text.toString().toInt()
            total.largusShifts = month_largus_count_textView.text.toString().toInt()
            total.sanderoShifts = month_sandero_count_textView.text.toString().toInt()
            total.xrayShifts = month_xray_count_textView.text.toString().toInt()
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
            Snackbar.make(view!!, getString(R.string.total_calc_and_save), Snackbar.LENGTH_SHORT)
                .show()
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
        month_logan_zhukova_move_textView.text = total.loganMoveToZhukova.toString()
        month_logan_kulturi_move_textView.text = total.loganMoveToKulturi.toString()
        month_logan_sedova_move_textView.text = total.loganMoveToSedova.toString()
        month_logan_himikov_move_textView.text = total.loganMoveToHimikov.toString()
        month_vesta_move_textView.text = total.vestaMove.toString()
        month_vesta_zhukova_move_textView.text = total.vestaMoveToZhukova.toString()
        month_vesta_kulturi_move_textView.text = total.vestaMoveToKulturi.toString()
        month_vesta_sedova_move_textView.text = total.vestaMoveToSedova.toString()
        month_vesta_himikov_move_textView.text = total.vestaMoveToHimikov.toString()
        month_total_move_textView.text = "${total.movesWithSalary}(${total.totalMove})"
        month_logan_task_textView.text = total.loganTask.toString()
        month_logan_zhukova_task_textView.text = total.loganTaskToZhukova.toString()
        month_logan_kulturi_task_textView.text = total.loganTaskToKulturi.toString()
        month_logan_sedova_task_textView.text = total.loganTaskToSedova.toString()
        month_logan_himikov_task_textView.text = total.loganTaskToHimikov.toString()
        month_logan_else_task_textView.text = total.loganTaskElse.toString()
        month_vesta_task_textView.text = total.vestaTask.toString()
        month_vesta_zhukova_task_textView.text = total.vestaTaskToZhukova.toString()
        month_vesta_kulturi_task_textView.text = total.vestaTaskToKulturi.toString()
        month_vesta_sedova_task_textView.text = total.vestaTaskToSedova.toString()
        month_vesta_himikov_task_textView.text = total.vestaTaskToHimikov.toString()
        month_vesta_else_task_textView.text = total.vestaTaskElse.toString()
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
        month_mid_salary_textView.text = if (total.totalShifts != 0) {
            "${total.salary / total.totalShifts}"
        } else "0"
        month_tea_textView.text = total.expenses.toString()
        month_prepay_textView.text = total.prepay.toString()
        month_holiday_pay_textView.text = total.holidayPay.toString()
        month_extraPay_textView.text = total.extraPay.toString()
        month_penalty_textView.text=total.penalty.toString()
        month_to_recieve_textView.text = "${total.salary - total.prepay}"
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
                "${resources.getString(R.string.deliveryValue)}: ${month_total_delivery_value_textView.text}\n" +
                "${resources.getString(R.string.totalMoney)}: ${month_total_money_textView.text}\n" +
                "${resources.getString(R.string.cash)}: ${month_total_cash_textView.text}\n" +
                "${resources.getString(R.string.card)}: ${month_total_card_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.logan_divider)}\n" +
                "${resources.getString(R.string.deliveryValue)}: ${month_total_delivery_value_logan_textView.text}\n" +
                "${resources.getString(R.string.money)}: ${month_logan_money_textView.text}\n" +
                "${resources.getString(R.string.cash)}: ${month_logan_cash_textView.text}\n" +
                "${resources.getString(R.string.card)}: ${month_logan_card_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.vesta_divider)}\n" +
                "${resources.getString(R.string.deliveryValue)}: ${month_total_delivery_value_vesta_textView.text}\n" +
                "${resources.getString(R.string.money)}: ${month_vesta_money_textView.text}\n" +
                "${resources.getString(R.string.cash)}: ${month_vesta_cash_textView.text}\n" +
                "${resources.getString(R.string.card)}: ${month_vesta_card_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.total_moves)}\n" +
                "   ${resources.getString(R.string.logan_divider)}: ${month_logan_move_textView.text}\n" +
                "${resources.getString(R.string.shop_zhukova)}: ${month_logan_zhukova_move_textView.text}\n" +
                "${resources.getString(R.string.shop_kulturi)}: ${month_logan_kulturi_move_textView.text}\n" +
                "${resources.getString(R.string.shop_sedova)}: ${month_logan_sedova_move_textView.text}\n" +
                "${resources.getString(R.string.shop_himikov)}: ${month_logan_himikov_move_textView.text}\n" +
                "   ${resources.getString(R.string.vesta_divider)}: ${month_vesta_move_textView.text}\n" +
                "${resources.getString(R.string.shop_zhukova)}: ${month_vesta_zhukova_move_textView.text}\n" +
                "${resources.getString(R.string.shop_kulturi)}: ${month_vesta_kulturi_move_textView.text}\n" +
                "${resources.getString(R.string.shop_sedova)}: ${month_vesta_sedova_move_textView.text}\n" +
//                "${resources.getString(R.string.shop_himikov)}: ${month_vesta_himikov_move_textView.text}\n" +
                "${resources.getString(R.string.total_total)} ${month_total_move_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.total_tasks)}\n" +
                "   ${resources.getString(R.string.logan_divider)}: ${month_logan_task_textView.text}\n" +
                "${resources.getString(R.string.shop_zhukova)}: ${month_logan_zhukova_task_textView.text}\n" +
                "${resources.getString(R.string.shop_kulturi)} :${month_logan_kulturi_task_textView.text}\n" +
                "${resources.getString(R.string.shop_sedova)}: ${month_logan_sedova_task_textView.text}\n" +
                "${resources.getString(R.string.shop_himikov)}: ${month_logan_himikov_task_textView.text}\n" +
                "${resources.getString(R.string.switch_else)}: ${month_logan_else_task_textView.text}\n" +
                "   ${resources.getString(R.string.vesta_divider)}: ${month_vesta_task_textView.text}\n" +
                "${resources.getString(R.string.shop_zhukova)}: ${month_vesta_zhukova_task_textView.text}\n" +
                "${resources.getString(R.string.shop_kulturi)}: ${month_vesta_kulturi_task_textView.text}\n" +
                "${resources.getString(R.string.shop_sedova)}: ${month_vesta_sedova_task_textView.text}\n" +
//                "${resources.getString(R.string.shop_himikov)}: ${month_vesta_himikov_task_textView.text}\n" +
                "${resources.getString(R.string.switch_else)}: ${month_vesta_else_task_textView.text}\n" +
                "${resources.getString(R.string.total_total)} ${month_total_task_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.expenses)}\n" +
                "   ${resources.getString(R.string.car_largus)}: ${month_largus_total_expenses_textView.text}\n" +
                "${resources.getString(R.string.fuel)} ${month_largus_expenses_fuel_textView.text}\n" +
                "${resources.getString(R.string.wash)}: ${month_largus_expenses_wash_textView.text}\n" +
                "${resources.getString(R.string.other)}: ${month_largus_expenses_other_textView.text}\n" +
                "   ${resources.getString(R.string.car_sandero)}: ${month_sandero_total_expenses_textView.text}\n" +
                "${resources.getString(R.string.fuel)} ${month_sandero_expenses_fuel_textView.text}\n" +
                "${resources.getString(R.string.wash)}: ${month_sandero_expenses_wash_textView.text}\n" +
                "${resources.getString(R.string.other)}: ${month_sandero_expenses_other_textView.text}\n" +
                "   ${resources.getString(R.string.car_x_ray)}: ${month_xray_total_expenses_textView.text}\n" +
                "${resources.getString(R.string.fuel)} ${month_xray_expenses_fuel_textView.text}\n" +
                "${resources.getString(R.string.wash)}: ${month_xray_expenses_wash_textView.text}\n" +
                "${resources.getString(R.string.other)}: ${month_xray_expenses_other_textView.text}\n" +
                "\n" +
                "${resources.getString(R.string.salary)} ${month_salary_textView.text}\n" +
                "${resources.getString(R.string.prepay)}: ${month_prepay_textView.text}\n" +
                "${resources.getString(R.string.holiday_pay)}: ${month_holiday_pay_textView.text}\n" +
                "${resources.getString(R.string.extraPay)}: ${month_extraPay_textView.text}\n" +
                "${resources.getString(R.string.penalty)}: ${month_penalty_textView.text}\n" +
                "${resources.getString(R.string.money_to_recieve)} ${month_to_recieve_textView.text}"
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, textToSend)
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, resources.getString(R.string.share)))
    }
}
