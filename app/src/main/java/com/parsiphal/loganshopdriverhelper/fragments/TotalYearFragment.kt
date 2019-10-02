package com.parsiphal.loganshopdriverhelper.fragments

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.parsiphal.loganshopdriverhelper.DB
import com.parsiphal.loganshopdriverhelper.R
import com.parsiphal.loganshopdriverhelper.data.Cars
import com.parsiphal.loganshopdriverhelper.data.Total
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import com.parsiphal.loganshopdriverhelper.prefs
import kotlinx.android.synthetic.main.fragment_total_year.*
import kotlinx.coroutines.*


class TotalYearFragment : MvpAppCompatFragment() {

    private var totalsMonth: List<Total> = ArrayList()
    private var totalsDays: List<Total> = ArrayList()
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
        return inflater.inflate(R.layout.fragment_total_year, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (newTotal) {
            val search =
                "${prefs.date!![6]}${prefs.date!![7]}${prefs.date!![8]}${prefs.date!![9]}"
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
                saveData()
                year_progress.visibility = View.GONE
                year_data.visibility = View.VISIBLE
            }
        } else {
            val search = "${total.date[3]}${total.date[4]}"
            val data = GlobalScope.async {
                getData(search)
                calculateExpences()
            }
            MainScope().launch {
                data.await()
                placeData()
                placeExpenses()
                year_progress.visibility = View.GONE
                year_data.visibility = View.VISIBLE
            }
        }
    }

    private fun getData(search: String) {
        totalsMonth = DB.getDao().getMonthTotals("%$search%")
        totalsDays = DB.getDao().getTotalsByMonth("%$search%")
    }

    private fun setData() {
        year_textView.text =
            "${prefs.date!![6]}${prefs.date!![7]}${prefs.date!![8]}${prefs.date!![9]}"
        year_total_shifts_value_textView.text = totalShifts.toString()
        year_total_money_textView.text = totalMoney.toString()
        year_total_cash_textView.text = totalCash.toString()
        year_total_card_textView.text = totalCard.toString()
        year_total_delivery_value_logan_textView.text = deliveryValueLogan.toString()
        year_logan_money_textView.text = loganMoney.toString()
        year_logan_cash_textView.text = loganCash.toString()
        year_logan_card_textView.text = loganCard.toString()
        year_total_delivery_value_vesta_textView.text = deliveryValueVesta.toString()
        year_vesta_money_textView.text = vestaMoney.toString()
        year_vesta_cash_textView.text = vestaCash.toString()
        year_vesta_card_textView.text = vestaCard.toString()
        year_largus_count_textView.text = largusShifts.toString()
        year_sandero_count_textView.text = sanderoShifts.toString()
        year_xray_count_textView.text = xrayShifts.toString()
        year_total_delivery_value_textView.text = totalDeliveries.toString()
        year_logan_move_textView.text = loganMove.toString()
        year_logan_zhukova_move_textView.text = loganMoveToZhukova.toString()
        year_logan_kulturi_move_textView.text = loganMoveToKulturi.toString()
        year_logan_sedova_move_textView.text = loganMoveToSedova.toString()
        year_logan_himikov_move_textView.text = loganMoveToHimikov.toString()
        year_vesta_move_textView.text = vestaMove.toString()
        year_vesta_zhukova_move_textView.text = vestaMoveToZhukova.toString()
        year_vesta_kulturi_move_textView.text = vestaMoveToKulturi.toString()
        year_vesta_sedova_move_textView.text = vestaMoveToSedova.toString()
        year_vesta_himikov_move_textView.text = vestaMoveToHimikov.toString()
        year_total_move_textView.text = "${totalMoveWithSalary}(${totalMove})"
        year_logan_task_textView.text = loganTask.toString()
        year_logan_zhukova_task_textView.text = loganTaskToZhukova.toString()
        year_logan_kulturi_task_textView.text = loganTaskToKulturi.toString()
        year_logan_sedova_task_textView.text = loganTaskToSedova.toString()
        year_logan_himikov_task_textView.text = loganTaskToHimikov.toString()
        year_logan_else_task_textView.text = loganTaskElse.toString()
        year_vesta_task_textView.text = vestaTask.toString()
        year_vesta_zhukova_task_textView.text = vestaTaskToZhukova.toString()
        year_vesta_kulturi_task_textView.text = vestaTaskToKulturi.toString()
        year_vesta_sedova_task_textView.text = vestaTaskToSedova.toString()
        year_vesta_himikov_task_textView.text = vestaTaskToHimikov.toString()
        year_vesta_else_task_textView.text = vestaTaskElse.toString()
        year_total_task_textView.text = "${totalTaskWithSalary}(${totalTask})"
        year_salary_textView.text = salary.toString()
        year_tea_textView.text = teaMoney.toString()
        year_largus_total_expenses_textView.text = largusExpenseTotal.toString()
        year_largus_expenses_fuel_textView.text = largusExpenseFuel.toString()
        year_largus_expenses_wash_textView.text = largusExpenseWash.toString()
        year_largus_expenses_other_textView.text = largusExpenseOther.toString()
        year_sandero_total_expenses_textView.text = sanderoExpenseTotal.toString()
        year_sandero_expenses_fuel_textView.text = sanderoExpenseFuel.toString()
        year_sandero_expenses_wash_textView.text = sanderoExpenseWash.toString()
        year_sandero_expenses_other_textView.text = sanderoExpenseOther.toString()
        year_xray_total_expenses_textView.text = xRayExpenseTotal.toString()
        year_xray_expenses_fuel_textView.text = xRayExpenseFuel.toString()
        year_xray_expenses_wash_textView.text = xRayExpenseWash.toString()
        year_xray_expenses_other_textView.text = xRayExpenseOther.toString()
    }

    private fun calculateSum() {
        for (position in totalsMonth) {
            totalShifts += position.totalShifts
            largusShifts += position.largusShifts
            sanderoShifts += position.sanderoShifts
            xrayShifts += position.xrayShifts
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
        for (position in totalsDays) {
            when {
                position.carIndex == Cars.Largus.i -> {
                    largusExpenseFuel += position.expensesFuel
                    largusExpenseWash += position.expensesWash
                    largusExpenseOther += position.expensesOther
                }
                position.carIndex == Cars.Sandero.i -> {
                    sanderoExpenseFuel += position.expensesFuel
                    sanderoExpenseWash += position.expensesWash
                    sanderoExpenseOther += position.expensesOther
                }
                position.carIndex == Cars.XRay.i -> {
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
        year_largus_total_expenses_textView.text = largusExpenseTotal.toString()
        year_largus_expenses_fuel_textView.text = largusExpenseFuel.toString()
        year_largus_expenses_wash_textView.text = largusExpenseWash.toString()
        year_largus_expenses_other_textView.text = largusExpenseOther.toString()
        year_sandero_total_expenses_textView.text = sanderoExpenseTotal.toString()
        year_sandero_expenses_fuel_textView.text = sanderoExpenseFuel.toString()
        year_sandero_expenses_wash_textView.text = sanderoExpenseWash.toString()
        year_sandero_expenses_other_textView.text = sanderoExpenseOther.toString()
        year_xray_total_expenses_textView.text = xRayExpenseTotal.toString()
        year_xray_expenses_fuel_textView.text = xRayExpenseFuel.toString()
        year_xray_expenses_wash_textView.text = xRayExpenseWash.toString()
        year_xray_expenses_other_textView.text = xRayExpenseOther.toString()
    }

    private fun saveData() = GlobalScope.launch {
        try {
            total.dayOrMonth = 2
            total.date = prefs.date!!
            total.totalShifts = year_total_shifts_value_textView.text.toString().toInt()
            total.totalDeliveries = totalDeliveries
            total.movesWithSalary = totalMoveWithSalary
            total.loganMove = loganMove
            total.vestaMove = vestaMove
            total.totalMove = totalMove
            total.tasksWithSalary = totalTaskWithSalary
            total.loganTask = loganTask
            total.vestaTask = vestaTask
            total.totalTask = totalTask
            total.totalMoney = year_total_money_textView.text.toString().toInt()
            total.totalCash = year_total_cash_textView.text.toString().toInt()
            total.totalCard = year_total_card_textView.text.toString().toInt()
            total.loganDeliveryValue =
                year_total_delivery_value_logan_textView.text.toString().toInt()
            total.loganMoney = year_logan_money_textView.text.toString().toInt()
            total.loganCash = year_logan_cash_textView.text.toString().toInt()
            total.loganCard = year_logan_card_textView.text.toString().toInt()
            total.vestaDeliveryValue =
                year_total_delivery_value_vesta_textView.text.toString().toInt()
            total.vestaMoney = year_vesta_money_textView.text.toString().toInt()
            total.vestaCash = year_vesta_cash_textView.text.toString().toInt()
            total.vestaCard = year_vesta_card_textView.text.toString().toInt()
            total.salary = year_salary_textView.text.toString().toInt()
            total.expenses = year_tea_textView.text.toString().toInt()
            total.deltaODO = deltaODO
            total.largusShifts = year_largus_count_textView.text.toString().toInt()
            total.sanderoShifts = year_sandero_count_textView.text.toString().toInt()
            total.xrayShifts = year_xray_count_textView.text.toString().toInt()
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
        year_textView.text = "${total.date[6]}${total.date[7]}${total.date[8]}${total.date[9]}"
        year_total_shifts_value_textView.text = total.totalShifts.toString()
        year_total_delivery_value_textView.text = total.totalDeliveries.toString()
        year_logan_move_textView.text = total.loganMove.toString()
        year_logan_zhukova_move_textView.text = total.loganMoveToZhukova.toString()
        year_logan_kulturi_move_textView.text = total.loganMoveToKulturi.toString()
        year_logan_sedova_move_textView.text = total.loganMoveToSedova.toString()
        year_logan_himikov_move_textView.text = total.loganMoveToHimikov.toString()
        year_vesta_move_textView.text = total.vestaMove.toString()
        year_vesta_zhukova_move_textView.text = total.vestaMoveToZhukova.toString()
        year_vesta_kulturi_move_textView.text = total.vestaMoveToKulturi.toString()
        year_vesta_sedova_move_textView.text = total.vestaMoveToSedova.toString()
        year_vesta_himikov_move_textView.text = total.vestaMoveToHimikov.toString()
        year_total_move_textView.text = "${total.movesWithSalary}(${total.totalMove})"
        year_logan_task_textView.text = total.loganTask.toString()
        year_logan_zhukova_task_textView.text = total.loganTaskToZhukova.toString()
        year_logan_kulturi_task_textView.text = total.loganTaskToKulturi.toString()
        year_logan_sedova_task_textView.text = total.loganTaskToSedova.toString()
        year_logan_himikov_task_textView.text = total.loganTaskToHimikov.toString()
        year_logan_else_task_textView.text = total.loganTaskElse.toString()
        year_vesta_task_textView.text = total.vestaTask.toString()
        year_vesta_zhukova_task_textView.text = total.vestaTaskToZhukova.toString()
        year_vesta_kulturi_task_textView.text = total.vestaTaskToKulturi.toString()
        year_vesta_sedova_task_textView.text = total.vestaTaskToSedova.toString()
        year_vesta_himikov_task_textView.text = total.vestaTaskToHimikov.toString()
        year_vesta_else_task_textView.text = total.vestaTaskElse.toString()
        year_total_task_textView.text = "${total.tasksWithSalary}(${total.totalTask})"
        year_total_money_textView.text = total.totalMoney.toString()
        year_total_cash_textView.text = total.totalCash.toString()
        year_total_card_textView.text = total.totalCard.toString()
        year_total_delivery_value_logan_textView.text = total.loganDeliveryValue.toString()
        year_logan_money_textView.text = total.loganMoney.toString()
        year_logan_cash_textView.text = total.loganCash.toString()
        year_logan_card_textView.text = total.loganCard.toString()
        year_total_delivery_value_vesta_textView.text = total.vestaDeliveryValue.toString()
        year_vesta_money_textView.text = total.vestaMoney.toString()
        year_vesta_cash_textView.text = total.vestaCash.toString()
        year_vesta_card_textView.text = total.vestaCard.toString()
        year_salary_textView.text = total.salary.toString()
        year_tea_textView.text = total.expenses.toString()
        year_largus_count_textView.text = total.largusShifts.toString()
        year_sandero_count_textView.text = total.sanderoShifts.toString()
        year_xray_count_textView.text = total.xrayShifts.toString()
    }

}
