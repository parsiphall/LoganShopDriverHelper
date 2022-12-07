package com.parsiphal.loganshopdriverhelper.fragments

import android.content.Context
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parsiphal.loganshopdriverhelper.DB
import com.parsiphal.loganshopdriverhelper.R
import com.parsiphal.loganshopdriverhelper.data.Cars
import com.parsiphal.loganshopdriverhelper.data.Total
import com.parsiphal.loganshopdriverhelper.databinding.FragmentTotalYearBinding
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import com.parsiphal.loganshopdriverhelper.prefs
import kotlinx.coroutines.*
import moxy.MvpAppCompatFragment


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
    private var _binding: FragmentTotalYearBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentTotalYearBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (newTotal) {
//            val search = "${prefs.date!![6]}${prefs.date!![7]}${prefs.date!![8]}${prefs.date!![9]}"
            val data = GlobalScope.async {
                getData()
                calculateSum()
                calculateExpences()
            }
            MainScope().launch {
                data.await()
                setData()
                placeExpenses()
                delay(1000)
                saveData()
                binding.yearProgress.visibility = View.GONE
                binding.yearData.visibility = View.VISIBLE
            }
        } else {
//            val search = "${total.date[3]}${total.date[4]}"
            val data = GlobalScope.async {
                getData()
                calculateExpences()
            }
            MainScope().launch {
                data.await()
                placeData()
                placeExpenses()
                binding.yearProgress.visibility = View.GONE
                binding.yearData.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getData() {
        totalsMonth = DB.getDao().getAllMonthlyTotals()
        totalsDays = DB.getDao().getAllDailyTotals()
    }

    private fun setData() {
        binding.yearTextView.text =
            "${prefs.date!![6]}${prefs.date!![7]}${prefs.date!![8]}${prefs.date!![9]}"
        binding.yearTotalShiftsValueTextView.text = totalShifts.toString()
        binding.yearTotalMoneyTextView.text = totalMoney.toString()
        binding.yearTotalCashTextView.text = totalCash.toString()
        binding.yearTotalCardTextView.text = totalCard.toString()
        binding.yearTotalDeliveryValueLoganTextView.text = deliveryValueLogan.toString()
        binding.yearLoganMoneyTextView.text = loganMoney.toString()
        binding.yearLoganCashTextView.text = loganCash.toString()
        binding.yearLoganCardTextView.text = loganCard.toString()
        binding.yearTotalDeliveryValueVestaTextView.text = deliveryValueVesta.toString()
        binding.yearVestaMoneyTextView.text = vestaMoney.toString()
        binding.yearVestaCashTextView.text = vestaCash.toString()
        binding.yearVestaCardTextView.text = vestaCard.toString()
        binding.yearLargusCountTextView.text = largusShifts.toString()
        binding.yearSanderoCountTextView.text = sanderoShifts.toString()
        binding.yearXrayCountTextView.text = xrayShifts.toString()
        binding.yearTotalDeliveryValueTextView.text = totalDeliveries.toString()
        binding.yearLoganMoveTextView.text = loganMove.toString()
        binding.yearLoganZhukovaMoveTextView.text = loganMoveToZhukova.toString()
        binding.yearLoganKulturiMoveTextView.text = loganMoveToKulturi.toString()
        binding.yearLoganSedovaMoveTextView.text = loganMoveToSedova.toString()
        binding.yearLoganHimikovMoveTextView.text = loganMoveToHimikov.toString()
        binding.yearVestaMoveTextView.text = vestaMove.toString()
        binding.yearVestaZhukovaMoveTextView.text = vestaMoveToZhukova.toString()
        binding.yearVestaKulturiMoveTextView.text = vestaMoveToKulturi.toString()
        binding.yearVestaSedovaMoveTextView.text = vestaMoveToSedova.toString()
        binding.yearVestaHimikovMoveTextView.text = vestaMoveToHimikov.toString()
        binding.yearTotalMoveTextView.text = "${totalMoveWithSalary}(${totalMove})"
        binding.yearLoganTaskTextView.text = loganTask.toString()
        binding.yearLoganZhukovaTaskTextView.text = loganTaskToZhukova.toString()
        binding.yearLoganKulturiTaskTextView.text = loganTaskToKulturi.toString()
        binding.yearLoganSedovaTaskTextView.text = loganTaskToSedova.toString()
        binding.yearLoganHimikovTaskTextView.text = loganTaskToHimikov.toString()
        binding.yearLoganElseTaskTextView.text = loganTaskElse.toString()
        binding.yearVestaTaskTextView.text = vestaTask.toString()
        binding.yearVestaZhukovaTaskTextView.text = vestaTaskToZhukova.toString()
        binding.yearVestaKulturiTaskTextView.text = vestaTaskToKulturi.toString()
        binding.yearVestaSedovaTaskTextView.text = vestaTaskToSedova.toString()
        binding.yearVestaHimikovTaskTextView.text = vestaTaskToHimikov.toString()
        binding.yearVestaElseTaskTextView.text = vestaTaskElse.toString()
        binding.yearTotalTaskTextView.text = "${totalTaskWithSalary}(${totalTask})"
        binding.yearSalaryTextView.text = salary.toString()
        binding.yearTeaTextView.text = teaMoney.toString()
        binding.yearLargusTotalExpensesTextView.text = largusExpenseTotal.toString()
        binding.yearLargusExpensesFuelTextView.text = largusExpenseFuel.toString()
        binding.yearLargusExpensesWashTextView.text = largusExpenseWash.toString()
        binding.yearLargusExpensesOtherTextView.text = largusExpenseOther.toString()
        binding.yearSanderoTotalExpensesTextView.text = sanderoExpenseTotal.toString()
        binding.yearSanderoExpensesFuelTextView.text = sanderoExpenseFuel.toString()
        binding.yearSanderoExpensesWashTextView.text = sanderoExpenseWash.toString()
        binding.yearSanderoExpensesOtherTextView.text = sanderoExpenseOther.toString()
        binding.yearXrayTotalExpensesTextView.text = xRayExpenseTotal.toString()
        binding.yearXrayExpensesFuelTextView.text = xRayExpenseFuel.toString()
        binding.yearXrayExpensesWashTextView.text = xRayExpenseWash.toString()
        binding.yearXrayExpensesOtherTextView.text = xRayExpenseOther.toString()
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
        binding.yearLargusTotalExpensesTextView.text = largusExpenseTotal.toString()
        binding.yearLargusExpensesFuelTextView.text = largusExpenseFuel.toString()
        binding.yearLargusExpensesWashTextView.text = largusExpenseWash.toString()
        binding.yearLargusExpensesOtherTextView.text = largusExpenseOther.toString()
        binding.yearSanderoTotalExpensesTextView.text = sanderoExpenseTotal.toString()
        binding.yearSanderoExpensesFuelTextView.text = sanderoExpenseFuel.toString()
        binding.yearSanderoExpensesWashTextView.text = sanderoExpenseWash.toString()
        binding.yearSanderoExpensesOtherTextView.text = sanderoExpenseOther.toString()
        binding.yearXrayTotalExpensesTextView.text = xRayExpenseTotal.toString()
        binding.yearXrayExpensesFuelTextView.text = xRayExpenseFuel.toString()
        binding.yearXrayExpensesWashTextView.text = xRayExpenseWash.toString()
        binding.yearXrayExpensesOtherTextView.text = xRayExpenseOther.toString()
    }

    private fun saveData() = GlobalScope.launch {
        try {
            total.dayOrMonth = 2
            total.date = prefs.date!!
            total.totalShifts = binding.yearTotalShiftsValueTextView.text.toString().toInt()
            total.totalDeliveries = totalDeliveries
            total.movesWithSalary = totalMoveWithSalary
            total.loganMove = loganMove
            total.vestaMove = vestaMove
            total.totalMove = totalMove
            total.tasksWithSalary = totalTaskWithSalary
            total.loganTask = loganTask
            total.vestaTask = vestaTask
            total.totalTask = totalTask
            total.totalMoney = binding.yearTotalMoneyTextView.text.toString().toInt()
            total.totalCash = binding.yearTotalCashTextView.text.toString().toInt()
            total.totalCard = binding.yearTotalCardTextView.text.toString().toInt()
            total.loganDeliveryValue =
                binding.yearTotalDeliveryValueLoganTextView.text.toString().toInt()
            total.loganMoney = binding.yearLoganMoneyTextView.text.toString().toInt()
            total.loganCash = binding.yearLoganCashTextView.text.toString().toInt()
            total.loganCard = binding.yearLoganCardTextView.text.toString().toInt()
            total.vestaDeliveryValue =
                binding.yearTotalDeliveryValueVestaTextView.text.toString().toInt()
            total.vestaMoney = binding.yearVestaMoneyTextView.text.toString().toInt()
            total.vestaCash = binding.yearVestaCashTextView.text.toString().toInt()
            total.vestaCard = binding.yearVestaCardTextView.text.toString().toInt()
            total.salary = binding.yearSalaryTextView.text.toString().toInt()
            total.expenses = binding.yearTeaTextView.text.toString().toInt()
            total.deltaODO = deltaODO
            total.largusShifts = binding.yearLargusCountTextView.text.toString().toInt()
            total.sanderoShifts = binding.yearSanderoCountTextView.text.toString().toInt()
            total.xrayShifts = binding.yearXrayCountTextView.text.toString().toInt()
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
        binding.yearTextView.text = "${total.date[6]}${total.date[7]}${total.date[8]}${total.date[9]}"
        binding.yearTotalShiftsValueTextView.text = total.totalShifts.toString()
        binding.yearTotalDeliveryValueTextView.text = total.totalDeliveries.toString()
        binding.yearLoganMoveTextView.text = total.loganMove.toString()
        binding.yearLoganZhukovaMoveTextView.text = total.loganMoveToZhukova.toString()
        binding.yearLoganKulturiMoveTextView.text = total.loganMoveToKulturi.toString()
        binding.yearLoganSedovaMoveTextView.text = total.loganMoveToSedova.toString()
        binding.yearLoganHimikovMoveTextView.text = total.loganMoveToHimikov.toString()
        binding.yearVestaMoveTextView.text = total.vestaMove.toString()
        binding.yearVestaZhukovaMoveTextView.text = total.vestaMoveToZhukova.toString()
        binding.yearVestaKulturiMoveTextView.text = total.vestaMoveToKulturi.toString()
        binding.yearVestaSedovaMoveTextView.text = total.vestaMoveToSedova.toString()
        binding.yearVestaHimikovMoveTextView.text = total.vestaMoveToHimikov.toString()
        binding.yearTotalMoveTextView.text = "${total.movesWithSalary}(${total.totalMove})"
        binding.yearLoganTaskTextView.text = total.loganTask.toString()
        binding.yearLoganZhukovaTaskTextView.text = total.loganTaskToZhukova.toString()
        binding.yearLoganKulturiTaskTextView.text = total.loganTaskToKulturi.toString()
        binding.yearLoganSedovaTaskTextView.text = total.loganTaskToSedova.toString()
        binding.yearLoganHimikovTaskTextView.text = total.loganTaskToHimikov.toString()
        binding.yearLoganElseTaskTextView.text = total.loganTaskElse.toString()
        binding.yearVestaTaskTextView.text = total.vestaTask.toString()
        binding.yearVestaZhukovaTaskTextView.text = total.vestaTaskToZhukova.toString()
        binding.yearVestaKulturiTaskTextView.text = total.vestaTaskToKulturi.toString()
        binding.yearVestaSedovaTaskTextView.text = total.vestaTaskToSedova.toString()
        binding.yearVestaHimikovTaskTextView.text = total.vestaTaskToHimikov.toString()
        binding.yearVestaElseTaskTextView.text = total.vestaTaskElse.toString()
        binding.yearTotalTaskTextView.text = "${total.tasksWithSalary}(${total.totalTask})"
        binding.yearTotalMoneyTextView.text = total.totalMoney.toString()
        binding.yearTotalCashTextView.text = total.totalCash.toString()
        binding.yearTotalCardTextView.text = total.totalCard.toString()
        binding.yearTotalDeliveryValueLoganTextView.text = total.loganDeliveryValue.toString()
        binding.yearLoganMoneyTextView.text = total.loganMoney.toString()
        binding.yearLoganCashTextView.text = total.loganCash.toString()
        binding.yearLoganCardTextView.text = total.loganCard.toString()
        binding.yearTotalDeliveryValueVestaTextView.text = total.vestaDeliveryValue.toString()
        binding.yearVestaMoneyTextView.text = total.vestaMoney.toString()
        binding.yearVestaCashTextView.text = total.vestaCash.toString()
        binding.yearVestaCardTextView.text = total.vestaCard.toString()
        binding.yearSalaryTextView.text = total.salary.toString()
        binding.yearTeaTextView.text = total.expenses.toString()
        binding.yearLargusCountTextView.text = total.largusShifts.toString()
        binding.yearSanderoCountTextView.text = total.sanderoShifts.toString()
        binding.yearXrayCountTextView.text = total.xrayShifts.toString()
    }

}
