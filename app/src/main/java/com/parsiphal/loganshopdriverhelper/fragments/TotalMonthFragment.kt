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
import com.parsiphal.loganshopdriverhelper.databinding.FragmentTotalMonthBinding
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import com.parsiphal.loganshopdriverhelper.prefs
import kotlinx.coroutines.*
import moxy.MvpAppCompatFragment
import java.lang.Exception

class TotalMonthFragment : MvpAppCompatFragment() {

    private var totals: List<Total> = ArrayList()
    private lateinit var total: Total
    private var newTotal = true
    private var xRayDB = false
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
    private var qualityPay = 0
    private var penalty = 0
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
    private var largusNewShifts = 0
    private var vestaSWShifts = 0
    private var loganMoveFromZhukova = 0
    private var loganMoveFromKulturi = 0
    private var loganMoveFromSedova = 0
    private var loganMoveFromHimikov = 0
    private var loganMoveFromPlanernaya = 0
    private var loganMoveFromVeteranov = 0
    private var loganMoveToZhukova = 0
    private var loganMoveToKulturi = 0
    private var loganMoveToSedova = 0
    private var loganMoveToHimikov = 0
    private var loganMoveToPlanernya = 0
    private var loganMoveToVeteranov = 0
    private var vestaMoveFromZhukova = 0
    private var vestaMoveFromKulturi = 0
    private var vestaMoveFromSedova = 0
    private var vestaMoveFromHimikov = 0
    private var vestaMoveFromPlanernaya = 0
    private var vestaMoveFromVeteranov = 0
    private var vestaMoveToZhukova = 0
    private var vestaMoveToKulturi = 0
    private var vestaMoveToSedova = 0
    private var vestaMoveToHimikov = 0
    private var vestaMoveToPlanernaya = 0
    private var vestaMoveToVeteranov = 0
    private var loganTaskFromZhukova = 0
    private var loganTaskFromKulturi = 0
    private var loganTaskFromSedova = 0
    private var loganTaskFromHimikov = 0
    private var loganTaskFromPlanenaya = 0
    private var loganTaskFromVeteranov = 0
    private var loganTaskToZhukova = 0
    private var loganTaskToKulturi = 0
    private var loganTaskToSedova = 0
    private var loganTaskToHimikov = 0
    private var loganTaskToPlanernaya = 0
    private var loganTaskToVeteranov = 0
    private var loganTaskElse = 0
    private var vestaTaskFromZhukova = 0
    private var vestaTaskFromKulturi = 0
    private var vestaTaskFromSedova = 0
    private var vestaTaskFromHimikov = 0
    private var vestaTaskFromPlanernaya = 0
    private var vestaTaskFromVeteranov = 0
    private var vestaTaskToZhukova = 0
    private var vestaTaskToKulturi = 0
    private var vestaTaskToSedova = 0
    private var vestaTaskToHimikov = 0
    private var vestaTaskToPlanernaya = 0
    private var vestaTaskToVeteranov = 0
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
    private var largusNewExpenseFuel = 0
    private var largusNewExpenseWash = 0
    private var largusNewExpenseOther = 0
    private var largusNewExpenseTotal = 0
    private var vestaSWExpenseFuel = 0
    private var vestaSWExpenseWash = 0
    private var vestaSWExpenseOther = 0
    private var vestaSWExpenseTotal = 0
    private var _binding: FragmentTotalMonthBinding? = null
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
        _binding = FragmentTotalMonthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (newTotal) {
            val time = System.currentTimeMillis() / 1000L
            if (time > 1614556800) {
                hideXRay()
            }
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
                binding.monthProgress.visibility = View.GONE
                binding.monthData.visibility = View.VISIBLE
            }
            binding.monthShare.visibility = View.GONE
        } else {
            if (total.xRayDB == 1) {
                hideXRay()
            }
            val search =
                "${total.date[3]}${total.date[4]}${total.date[5]}${total.date[6]}${total.date[7]}${total.date[8]}${total.date[9]}"
            val data = GlobalScope.async {
                getData(search)
                calculateExpences()
            }
            MainScope().launch {
                data.await()
                placeData()
                placeExpenses()
                binding.monthProgress.visibility = View.GONE
                binding.monthData.visibility = View.VISIBLE
            }
            binding.monthWrite.visibility = View.GONE
        }
        binding.monthWrite.setOnClickListener {
            saveData()
        }
        binding.monthShare.setOnClickListener {
            shareDate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun hideXRay() {
        xRayDB = true
        binding.monthXrayCount.visibility = View.GONE
        binding.monthXrayTotalExpenses.visibility = View.GONE
        binding.monthXrayExpensesFuel.visibility = View.GONE
        binding.monthXrayExpensesWash.visibility = View.GONE
        binding.monthXrayExpensesOther.visibility = View.GONE
    }

    private fun getData(search: String) {
        totals = DB.getDao().getTotalsByMonth("%$search%")
    }

    private fun setData() {
        binding.monthMonth.text = "${prefs.date!![3]}${prefs.date!![4]}"
        binding.monthYear.text = "${prefs.date!![6]}${prefs.date!![7]}${prefs.date!![8]}${prefs.date!![9]}"
        binding.monthTotalShiftsValueTextView.text = totalShifts.toString()
        binding.monthTotalMoneyTextView.text = totalMoney.toString()
        binding.monthTotalCashTextView.text = totalCash.toString()
        binding.monthTotalCardTextView.text = totalCard.toString()
        binding.monthTotalDeliveryValueLoganTextView.text = deliveryValueLogan.toString()
        binding.monthLoganMoneyTextView.text = loganMoney.toString()
        binding.monthLoganCashTextView.text = loganCash.toString()
        binding.monthLoganCardTextView.text = loganCard.toString()
        binding.monthTotalDeliveryValueVestaTextView.text = deliveryValueVesta.toString()
        binding.monthVestaMoneyTextView.text = vestaMoney.toString()
        binding.monthVestaCashTextView.text = vestaCash.toString()
        binding.monthVestaCardTextView.text = vestaCard.toString()
        binding.monthPrepayTextView.text = prepay.toString()
        binding.monthHolidayPayTextView.text = holiday.toString()
        binding.monthLargusCountTextView.text = largusShifts.toString()
        binding.monthSanderoCountTextView.text = sanderoShifts.toString()
        binding.monthXrayCountTextView.text = xrayShifts.toString()
        binding.monthLargusNewCountTextView.text = largusNewShifts.toString()
        binding.monthVestaSWCountTextView.text = vestaSWShifts.toString()
        binding.monthTotalDeliveryValueTextView.text = totalDeliveries.toString()
        binding.monthLoganMoveTextView.text = loganMove.toString()
        binding.monthLoganZhukovaMoveTextView.text = loganMoveToZhukova.toString()
        binding.monthLoganKulturiMoveTextView.text = loganMoveToKulturi.toString()
        binding.monthLoganSedovaMoveTextView.text = loganMoveToSedova.toString()
        binding.monthLoganHimikovMoveTextView.text = loganMoveToHimikov.toString()
        binding.monthLoganPlanernayaMoveTextView.text = loganMoveToPlanernya.toString()
        binding.monthLoganVeteranovMoveTextView.text = loganMoveToVeteranov.toString()
        binding.monthVestaMoveTextView.text = vestaMove.toString()
        binding.monthVestaZhukovaMoveTextView.text = vestaMoveToZhukova.toString()
        binding.monthVestaKulturiMoveTextView.text = vestaMoveToKulturi.toString()
        binding.monthVestaSedovaMoveTextView.text = vestaMoveToSedova.toString()
        binding.monthVestaHimikovMoveTextView.text = vestaMoveToHimikov.toString()
        binding.monthVestaPlanernayaMoveTextView.text = vestaMoveToPlanernaya.toString()
        binding.monthVestaVeteranovMoveTextView.text = vestaMoveToVeteranov.toString()
        binding.monthTotalMoveTextView.text = "${totalMoveWithSalary}(${totalMove})"
        binding.monthLoganTaskTextView.text = loganTask.toString()
        binding.monthLoganZhukovaTaskTextView.text = loganTaskToZhukova.toString()
        binding.monthLoganKulturiTaskTextView.text = loganTaskToKulturi.toString()
        binding.monthLoganSedovaTaskTextView.text = loganTaskToSedova.toString()
        binding.monthLoganHimikovTaskTextView.text = loganTaskToHimikov.toString()
        binding.monthLoganPlanernayaTaskTextView.text = loganTaskToPlanernaya.toString()
        binding.monthLoganVeteranovTaskTextView.text = loganTaskToVeteranov.toString()
        binding.monthLoganElseTaskTextView.text = loganTaskElse.toString()
        binding.monthVestaTaskTextView.text = vestaTask.toString()
        binding.monthVestaZhukovaTaskTextView.text = vestaTaskToZhukova.toString()
        binding.monthVestaKulturiTaskTextView.text = vestaTaskToKulturi.toString()
        binding.monthVestaSedovaTaskTextView.text = vestaTaskToSedova.toString()
        binding.monthVestaHimikovTaskTextView.text = vestaTaskToHimikov.toString()
        binding.monthVestaPlanernayaTaskTextView.text = vestaTaskToPlanernaya.toString()
        binding.monthVestaVeteranovTaskTextView.text = vestaTaskToVeteranov.toString()
        binding.monthVestaElseTaskTextView.text = vestaTaskElse.toString()
        binding.monthTotalTaskTextView.text = "${totalTaskWithSalary}(${totalTask})"
        binding.monthSalaryTextView.text = salary.toString()
        binding.monthTeaTextView.text = teaMoney.toString()
        binding.monthExtraPayTextView.text = extraPay.toString()
        binding.monthQualityPayTextView.text = qualityPay.toString()
        binding.monthPenaltyTextView.text = penalty.toString()

        try {
            binding.monthMidSalaryTextView.text = if (totalShifts != 0) {
                "${salary / totalShifts}"
            } else "0"
            binding.monthToRecieveTextView.text =
                "${salary - binding.monthPrepayTextView.text.toString().toInt()}"
        } catch (e: Exception) {
            e.printStackTrace()
            Snackbar.make(view!!, getString(R.string.error), Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun calculateSum() {
        totalShifts = totals.size
        for (position in totals) {
            when (position.carIndex) {
                Cars.Largus.i -> largusShifts++
                Cars.Sandero.i -> sanderoShifts++
                Cars.XRay.i -> xrayShifts++
                Cars.LargusNew.i -> largusNewShifts++
                Cars.VestaSW.i -> vestaSWShifts++
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
            qualityPay += position.qualityPay
            penalty += position.penalty
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
            loganMoveFromPlanernaya += position.loganMoveFromPlanernaya
            loganMoveFromVeteranov += position.loganMoveFromVeteranov
            loganMoveToZhukova += position.loganMoveToZhukova
            loganMoveToKulturi += position.loganMoveToKulturi
            loganMoveToSedova += position.loganMoveToSedova
            loganMoveToHimikov += position.loganMoveToHimikov
            loganMoveToPlanernya += position.loganMoveToPlanernaya
            loganMoveToVeteranov += position.loganMoveToVeteranov
            vestaMoveFromZhukova += position.vestaMoveFromZhukova
            vestaMoveFromKulturi += position.vestaMoveFromKulturi
            vestaMoveFromSedova += position.vestaMoveFromSedova
            vestaMoveFromHimikov += position.vestaMoveFromHimikov
            vestaMoveFromPlanernaya += position.vestaMoveFromPlanernaya
            vestaMoveFromVeteranov += position.vestaMoveFromVeteranov
            vestaMoveToZhukova += position.vestaMoveToZhukova
            vestaMoveToKulturi += position.vestaMoveToKulturi
            vestaMoveToSedova += position.vestaMoveToSedova
            vestaMoveToHimikov += position.vestaMoveToHimikov
            vestaMoveToPlanernaya += position.vestaMoveToPlanernaya
            vestaMoveToVeteranov += position.vestaMoveToVeteranov
            loganTaskFromZhukova += position.loganTaskFromZhukova
            loganTaskFromKulturi += position.loganTaskFromKulturi
            loganTaskFromSedova += position.loganTaskFromSedova
            loganTaskFromHimikov += position.loganTaskFromHimikov
            loganTaskFromPlanenaya += position.loganTaskFromPlanernaya
            loganTaskFromVeteranov += position.loganTaskFromVeteranov
            loganTaskToZhukova += position.loganTaskToZhukova
            loganTaskToKulturi += position.loganTaskToKulturi
            loganTaskToSedova += position.loganTaskToSedova
            loganTaskToHimikov += position.loganTaskToHimikov
            loganTaskToPlanernaya += position.loganTaskToPlanernaya
            loganTaskToVeteranov += position.loganTaskToVeteranov
            loganTaskElse += position.loganTaskElse
            vestaTaskFromZhukova += position.vestaTaskFromZhukova
            vestaTaskFromKulturi += position.vestaTaskFromKulturi
            vestaTaskFromSedova += position.vestaTaskFromSedova
            vestaTaskFromHimikov += position.vestaTaskFromHimikov
            vestaTaskFromPlanernaya += position.vestaTaskFromPlanernaya
            vestaTaskFromVeteranov += position.vestaTaskFromVeteranov
            vestaTaskToZhukova += position.vestaTaskToZhukova
            vestaTaskToKulturi += position.vestaTaskToKulturi
            vestaTaskToSedova += position.vestaTaskToSedova
            vestaTaskToHimikov += position.vestaTaskToHimikov
            vestaTaskToPlanernaya += position.vestaTaskToPlanernaya
            vestaTaskToVeteranov += position.vestaTaskToVeteranov
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
                Cars.LargusNew.i -> {
                    largusNewExpenseFuel += position.expensesFuel
                    largusNewExpenseWash += position.expensesWash
                    largusNewExpenseOther += position.expensesOther
                }
                Cars.VestaSW.i -> {
                    vestaSWExpenseFuel += position.expensesFuel
                    vestaSWExpenseWash += position.expensesWash
                    vestaSWExpenseOther += position.expensesOther
                }
            }
        }
        largusExpenseTotal = largusExpenseFuel + largusExpenseWash + largusExpenseOther
        sanderoExpenseTotal = sanderoExpenseFuel + sanderoExpenseWash + sanderoExpenseOther
        xRayExpenseTotal = xRayExpenseFuel + xRayExpenseWash + xRayExpenseOther
        largusNewExpenseTotal = largusNewExpenseFuel + largusNewExpenseWash + largusNewExpenseOther
        vestaSWExpenseTotal = vestaSWExpenseFuel + vestaSWExpenseWash + vestaSWExpenseOther
    }

    private fun placeExpenses() {
        binding.monthLargusTotalExpensesTextView.text = largusExpenseTotal.toString()
        binding.monthLargusExpensesFuelTextView.text = largusExpenseFuel.toString()
        binding.monthLargusExpensesWashTextView.text = largusExpenseWash.toString()
        binding.monthLargusExpensesOtherTextView.text = largusExpenseOther.toString()
        binding.monthSanderoTotalExpensesTextView.text = sanderoExpenseTotal.toString()
        binding.monthSanderoExpensesFuelTextView.text = sanderoExpenseFuel.toString()
        binding.monthSanderoExpensesWashTextView.text = sanderoExpenseWash.toString()
        binding.monthSanderoExpensesOtherTextView.text = sanderoExpenseOther.toString()
        binding.monthXrayTotalExpensesTextView.text = xRayExpenseTotal.toString()
        binding.monthXrayExpensesFuelTextView.text = xRayExpenseFuel.toString()
        binding.monthXrayExpensesWashTextView.text = xRayExpenseWash.toString()
        binding.monthXrayExpensesOtherTextView.text = xRayExpenseOther.toString()
        binding.monthLargusNewTotalExpensesTextView.text = largusNewExpenseTotal.toString()
        binding.monthLargusNewExpensesFuelTextView.text = largusNewExpenseFuel.toString()
        binding.monthLargusNewExpensesWashTextView.text = largusNewExpenseWash.toString()
        binding.monthLargusNewExpensesOtherTextView.text = largusNewExpenseOther.toString()
        binding.monthVestaSWTotalExpensesTextView.text = vestaSWExpenseTotal.toString()
        binding.monthVestaSWExpensesFuelTextView.text = vestaSWExpenseFuel.toString()
        binding.monthVestaSWExpensesWashTextView.text = vestaSWExpenseWash.toString()
        binding.monthVestaSWExpensesOtherTextView.text = vestaSWExpenseOther.toString()
    }

    private fun saveData() = GlobalScope.launch {
        try {
            total.dayOrMonth = 1
            total.date = prefs.date!!
            total.totalShifts = binding.monthTotalShiftsValueTextView.text.toString().toInt()
            total.totalDeliveries = totalDeliveries
            total.movesWithSalary = totalMoveWithSalary
            total.loganMove = loganMove
            total.vestaMove = vestaMove
            total.totalMove = totalMove
            total.tasksWithSalary = totalTaskWithSalary
            total.loganTask = loganTask
            total.vestaTask = vestaTask
            total.totalTask = totalTask
            total.totalMoney = binding.monthTotalMoneyTextView.text.toString().toInt()
            total.totalCash = binding.monthTotalCashTextView.text.toString().toInt()
            total.totalCard = binding.monthTotalCardTextView.text.toString().toInt()
            total.loganDeliveryValue =
                binding.monthTotalDeliveryValueLoganTextView.text.toString().toInt()
            total.loganMoney = binding.monthLoganMoneyTextView.text.toString().toInt()
            total.loganCash = binding.monthLoganCashTextView.text.toString().toInt()
            total.loganCard = binding.monthLoganCardTextView.text.toString().toInt()
            total.vestaDeliveryValue =
                binding.monthTotalDeliveryValueVestaTextView.text.toString().toInt()
            total.vestaMoney = binding.monthVestaMoneyTextView.text.toString().toInt()
            total.vestaCash = binding.monthVestaCashTextView.text.toString().toInt()
            total.vestaCard = binding.monthVestaCardTextView.text.toString().toInt()
            total.salary = binding.monthSalaryTextView.text.toString().toInt()
            total.expenses = binding.monthTeaTextView.text.toString().toInt()
            total.extraPay = binding.monthExtraPayTextView.text.toString().toInt()
            total.qualityPay = binding.monthQualityPayTextView.text.toString().toInt()
            total.penalty = binding.monthPenaltyTextView.text.toString().toInt()
            total.deltaODO = deltaODO
            total.prepay = binding.monthPrepayTextView.text.toString().toInt()
            total.holidayPay = binding.monthHolidayPayTextView.text.toString().toInt()
            total.largusShifts = binding.monthLargusCountTextView.text.toString().toInt()
            total.sanderoShifts = binding.monthSanderoCountTextView.text.toString().toInt()
            total.xrayShifts = binding.monthXrayCountTextView.text.toString().toInt()
            total.largusNewShifts = binding.monthLargusNewCountTextView.text.toString().toInt()
            total.vestaSWShifts = binding.monthVestaSWCountTextView.text.toString().toInt()

            total.loganMoveFromZhukova = loganMoveFromZhukova
            total.loganMoveFromKulturi = loganMoveFromKulturi
            total.loganMoveFromSedova = loganMoveFromSedova
            total.loganMoveFromHimikov = loganMoveFromHimikov
            total.loganMoveFromPlanernaya = loganMoveFromPlanernaya
            total.loganMoveFromVeteranov = loganMoveFromVeteranov

            total.loganMoveToZhukova = loganMoveToZhukova
            total.loganMoveToKulturi = loganMoveToKulturi
            total.loganMoveToSedova = loganMoveToSedova
            total.loganMoveToHimikov = loganMoveToHimikov
            total.loganMoveToPlanernaya = loganMoveToPlanernya
            total.loganMoveToVeteranov = loganMoveToVeteranov

            total.vestaMoveFromZhukova = vestaMoveFromZhukova
            total.vestaMoveFromKulturi = vestaMoveFromKulturi
            total.vestaMoveFromSedova = vestaMoveFromSedova
            total.vestaMoveFromHimikov = vestaMoveFromHimikov
            total.vestaMoveFromPlanernaya = vestaMoveFromPlanernaya
            total.vestaMoveFromVeteranov = vestaMoveFromVeteranov

            total.vestaMoveToZhukova = vestaMoveToZhukova
            total.vestaMoveToKulturi = vestaMoveToKulturi
            total.vestaMoveToSedova = vestaMoveToSedova
            total.vestaMoveToHimikov = vestaMoveToHimikov
            total.vestaMoveToPlanernaya = vestaMoveToPlanernaya
            total.vestaMoveToVeteranov = vestaMoveToVeteranov

            total.loganTaskFromZhukova = loganTaskFromZhukova
            total.loganTaskFromKulturi = loganTaskFromKulturi
            total.loganTaskFromSedova = loganTaskFromSedova
            total.loganTaskFromHimikov = loganTaskFromHimikov
            total.loganTaskFromPlanernaya = loganTaskFromPlanenaya
            total.loganTaskFromVeteranov = loganTaskFromVeteranov

            total.loganTaskToZhukova = loganTaskToZhukova
            total.loganTaskToKulturi = loganTaskToKulturi
            total.loganTaskToSedova = loganTaskToSedova
            total.loganTaskToHimikov = loganTaskToHimikov
            total.loganTaskToPlanernaya = loganTaskToPlanernaya
            total.loganTaskToVeteranov = loganTaskToVeteranov

            total.loganTaskElse = loganTaskElse

            total.vestaTaskFromZhukova = vestaTaskFromZhukova
            total.vestaTaskFromKulturi = vestaTaskFromKulturi
            total.vestaTaskFromSedova = vestaTaskFromSedova
            total.vestaTaskFromHimikov = vestaTaskFromHimikov
            total.vestaTaskFromPlanernaya = vestaTaskFromPlanernaya
            total.vestaTaskFromVeteranov = vestaTaskFromVeteranov

            total.vestaTaskToZhukova = vestaTaskToZhukova
            total.vestaTaskToKulturi = vestaTaskToKulturi
            total.vestaTaskToSedova = vestaTaskToSedova
            total.vestaTaskToHimikov = vestaTaskToHimikov
            total.vestaTaskToPlanernaya = vestaTaskToPlanernaya
            total.vestaTaskToVeteranov = vestaTaskToVeteranov

            total.vestaTaskElse = vestaTaskElse

            if (xRayDB) {
                total.xRayDB = 1
            }

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
        binding.monthMonth.text = "${total.date[3]}${total.date[4]}"
        binding.monthYear.text = "${total.date[6]}${total.date[7]}${total.date[8]}${total.date[9]}"
        binding.monthTotalShiftsValueTextView.text = total.totalShifts.toString()
        binding.monthTotalDeliveryValueTextView.text = total.totalDeliveries.toString()

        binding.monthLoganMoveTextView.text = total.loganMove.toString()
        binding.monthLoganZhukovaMoveTextView.text = total.loganMoveToZhukova.toString()
        binding.monthLoganKulturiMoveTextView.text = total.loganMoveToKulturi.toString()
        binding.monthLoganSedovaMoveTextView.text = total.loganMoveToSedova.toString()
        binding.monthLoganHimikovMoveTextView.text = total.loganMoveToHimikov.toString()
        binding.monthLoganPlanernayaMoveTextView.text = total.loganMoveToPlanernaya.toString()
        binding.monthLoganVeteranovMoveTextView.text = total.loganMoveToVeteranov.toString()

        binding.monthVestaMoveTextView.text = total.vestaMove.toString()
        binding.monthVestaZhukovaMoveTextView.text = total.vestaMoveToZhukova.toString()
        binding.monthVestaKulturiMoveTextView.text = total.vestaMoveToKulturi.toString()
        binding.monthVestaSedovaMoveTextView.text = total.vestaMoveToSedova.toString()
        binding.monthVestaHimikovMoveTextView.text = total.vestaMoveToHimikov.toString()
        binding.monthVestaPlanernayaMoveTextView.text = total.vestaMoveToPlanernaya.toString()
        binding.monthVestaVeteranovMoveTextView.text = total.vestaMoveToVeteranov.toString()

        binding.monthTotalMoveTextView.text = "${total.movesWithSalary}(${total.totalMove})"

        binding.monthLoganTaskTextView.text = total.loganTask.toString()
        binding.monthLoganZhukovaTaskTextView.text = total.loganTaskToZhukova.toString()
        binding.monthLoganKulturiTaskTextView.text = total.loganTaskToKulturi.toString()
        binding.monthLoganSedovaTaskTextView.text = total.loganTaskToSedova.toString()
        binding.monthLoganHimikovTaskTextView.text = total.loganTaskToHimikov.toString()
        binding.monthLoganPlanernayaTaskTextView.text = total.loganTaskToPlanernaya.toString()
        binding.monthLoganVeteranovTaskTextView.text = total.loganTaskToVeteranov.toString()

        binding.monthLoganElseTaskTextView.text = total.loganTaskElse.toString()

        binding.monthVestaTaskTextView.text = total.vestaTask.toString()
        binding.monthVestaZhukovaTaskTextView.text = total.vestaTaskToZhukova.toString()
        binding.monthVestaKulturiTaskTextView.text = total.vestaTaskToKulturi.toString()
        binding.monthVestaSedovaTaskTextView.text = total.vestaTaskToSedova.toString()
        binding.monthVestaHimikovTaskTextView.text = total.vestaTaskToHimikov.toString()
        binding.monthVestaPlanernayaTaskTextView.text = total.vestaTaskToPlanernaya.toString()
        binding.monthVestaVeteranovTaskTextView.text = total.vestaTaskToVeteranov.toString()

        binding.monthVestaElseTaskTextView.text = total.vestaTaskElse.toString()

        binding.monthTotalTaskTextView.text = "${total.tasksWithSalary}(${total.totalTask})"
        binding.monthTotalMoneyTextView.text = total.totalMoney.toString()
        binding.monthTotalCashTextView.text = total.totalCash.toString()
        binding.monthTotalCardTextView.text = total.totalCard.toString()
        binding.monthTotalDeliveryValueLoganTextView.text = total.loganDeliveryValue.toString()
        binding.monthLoganMoneyTextView.text = total.loganMoney.toString()
        binding.monthLoganCashTextView.text = total.loganCash.toString()
        binding.monthLoganCardTextView.text = total.loganCard.toString()
        binding.monthTotalDeliveryValueVestaTextView.text = total.vestaDeliveryValue.toString()
        binding.monthVestaMoneyTextView.text = total.vestaMoney.toString()
        binding.monthVestaCashTextView.text = total.vestaCash.toString()
        binding.monthVestaCardTextView.text = total.vestaCard.toString()
        binding.monthSalaryTextView.text = total.salary.toString()
        binding.monthMidSalaryTextView.text = if (total.totalShifts != 0) {
            "${total.salary / total.totalShifts}"
        } else "0"
        binding.monthTeaTextView.text = total.expenses.toString()
        binding.monthPrepayTextView.text = total.prepay.toString()
        binding.monthHolidayPayTextView.text = total.holidayPay.toString()
        binding.monthExtraPayTextView.text = total.extraPay.toString()
        binding.monthQualityPayTextView.text = total.qualityPay.toString()
        binding.monthPenaltyTextView.text = total.penalty.toString()
        binding.monthToRecieveTextView.text = "${total.salary - total.prepay}"
        binding.monthLargusCountTextView.text = total.largusShifts.toString()
        binding.monthSanderoCountTextView.text = total.sanderoShifts.toString()
        binding.monthXrayCountTextView.text = total.xrayShifts.toString()
        binding.monthLargusNewCountTextView.text = total.largusNewShifts.toString()
        binding.monthVestaSWCountTextView.text = total.vestaSWShifts.toString()
    }

    private fun shareDate() = GlobalScope.launch {
        var textToSend = "${binding.monthMonth.text} / ${binding.monthYear.text}\n" +
                "${prefs.family}\n" +
                "${resources.getString(R.string.shiftsValue)} ${binding.monthTotalShiftsValueTextView.text}\n" +
                "${resources.getString(R.string.car_largus)}: ${binding.monthLargusCountTextView.text}\n" +
                "${resources.getString(R.string.car_sandero)}: ${binding.monthSanderoCountTextView.text}\n"
        if (!xRayDB) {
            textToSend += "${resources.getString(R.string.car_x_ray)}: ${binding.monthXrayCountTextView.text}\n"
        }
        textToSend += "${resources.getString(R.string.car_largusNew)}: ${binding.monthLargusNewCountTextView.text}\n" +
                "${resources.getString(R.string.car_vestaSW)}: ${binding.monthVestaSWCountTextView.text}\n" +
                "\n" +
                "${resources.getString(R.string.deliveryValue)}: ${binding.monthTotalDeliveryValueTextView.text}\n" +
                "${resources.getString(R.string.totalMoney)}: ${binding.monthTotalMoneyTextView.text}\n" +
                "${resources.getString(R.string.cash)}: ${binding.monthTotalCashTextView.text}\n" +
                "${resources.getString(R.string.card)}: ${binding.monthTotalCardTextView.text}\n" +
                "\n" +
                "${resources.getString(R.string.logan_divider)}\n" +
                "${resources.getString(R.string.deliveryValue)}: ${binding.monthTotalDeliveryValueLoganTextView.text}\n" +
                "${resources.getString(R.string.money)}: ${binding.monthLoganMoneyTextView.text}\n" +
                "${resources.getString(R.string.cash)}: ${binding.monthLoganCashTextView.text}\n" +
                "${resources.getString(R.string.card)}: ${binding.monthLoganCardTextView.text}\n" +
                "\n" +
                "${resources.getString(R.string.vesta_divider)}\n" +
                "${resources.getString(R.string.deliveryValue)}: ${binding.monthTotalDeliveryValueVestaTextView.text}\n" +
                "${resources.getString(R.string.money)}: ${binding.monthVestaMoneyTextView.text}\n" +
                "${resources.getString(R.string.cash)}: ${binding.monthVestaCashTextView.text}\n" +
                "${resources.getString(R.string.card)}: ${binding.monthVestaCardTextView.text}\n" +
                "\n" +
                "${resources.getString(R.string.total_moves)}\n" +
                "   ${resources.getString(R.string.logan_divider)}: ${binding.monthLoganMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_veteranov)}: ${binding.monthLoganVeteranovMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_zhukova)}: ${binding.monthLoganZhukovaMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_kulturi)}: ${binding.monthLoganKulturiMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_planernaya)}: ${binding.monthLoganPlanernayaMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_sedova)}: ${binding.monthLoganSedovaMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_himikov)}: ${binding.monthLoganHimikovMoveTextView.text}\n" +
                "   ${resources.getString(R.string.vesta_divider)}: ${binding.monthVestaMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_veteranov)}: ${binding.monthVestaVeteranovMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_zhukova)}: ${binding.monthVestaZhukovaMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_kulturi)}: ${binding.monthVestaKulturiMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_planernaya)}: ${binding.monthVestaPlanernayaMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_sedova)}: ${binding.monthVestaSedovaMoveTextView.text}\n" +
//                "${resources.getString(R.string.shop_himikov)}: ${binding.monthVestaHimikovMoveTextView.text}\n" +
                "${resources.getString(R.string.total_total)} ${binding.monthTotalMoveTextView.text}\n" +
                "\n" +
                "${resources.getString(R.string.total_tasks)}\n" +
                "   ${resources.getString(R.string.logan_divider)}: ${binding.monthLoganTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_veteranov)}: ${binding.monthLoganVeteranovTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_zhukova)}: ${binding.monthLoganZhukovaTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_kulturi)} :${binding.monthLoganKulturiTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_planernaya)}: ${binding.monthLoganPlanernayaTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_sedova)}: ${binding.monthLoganSedovaTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_himikov)}: ${binding.monthLoganHimikovTaskTextView.text}\n" +
                "${resources.getString(R.string.switch_else)}: ${binding.monthLoganElseTaskTextView.text}\n" +
                "   ${resources.getString(R.string.vesta_divider)}: ${binding.monthVestaTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_veteranov)}: ${binding.monthVestaVeteranovTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_zhukova)}: ${binding.monthVestaZhukovaTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_kulturi)}: ${binding.monthVestaKulturiTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_planernaya)}: ${binding.monthVestaPlanernayaTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_sedova)}: ${binding.monthVestaSedovaTaskTextView.text}\n" +
//                "${resources.getString(R.string.shop_himikov)}: ${binding.monthVestaHimikovTaskTextView.text}\n" +
                "${resources.getString(R.string.switch_else)}: ${binding.monthVestaElseTaskTextView.text}\n" +
                "${resources.getString(R.string.total_total)} ${binding.monthTotalTaskTextView.text}\n" +
                "\n" +
                "${resources.getString(R.string.expenses)}\n" +
                "   ${resources.getString(R.string.car_largus)}: ${binding.monthLargusTotalExpensesTextView.text}\n" +
                "${resources.getString(R.string.fuel)} ${binding.monthLargusExpensesFuelTextView.text}\n" +
                "${resources.getString(R.string.wash)}: ${binding.monthLargusExpensesWashTextView.text}\n" +
                "${resources.getString(R.string.other)}: ${binding.monthLargusExpensesOtherTextView.text}\n" +
                "   ${resources.getString(R.string.car_sandero)}: ${binding.monthSanderoTotalExpensesTextView.text}\n" +
                "${resources.getString(R.string.fuel)} ${binding.monthSanderoExpensesFuelTextView.text}\n" +
                "${resources.getString(R.string.wash)}: ${binding.monthSanderoExpensesWashTextView.text}\n" +
                "${resources.getString(R.string.other)}: ${binding.monthSanderoExpensesOtherTextView.text}\n"
        if (!xRayDB) {
            textToSend += "   ${resources.getString(R.string.car_x_ray)}: ${binding.monthXrayTotalExpensesTextView.text}\n" +
                    "${resources.getString(R.string.fuel)} ${binding.monthXrayExpensesFuelTextView.text}\n" +
                    "${resources.getString(R.string.wash)}: ${binding.monthXrayExpensesWashTextView.text}\n" +
                    "${resources.getString(R.string.other)}: ${binding.monthXrayExpensesOtherTextView.text}\n"
        }
        textToSend += "   ${resources.getString(R.string.car_largusNew)}: ${binding.monthLargusNewTotalExpensesTextView.text}\n" +
                "${resources.getString(R.string.fuel)} ${binding.monthLargusNewExpensesFuelTextView.text}\n" +
                "${resources.getString(R.string.wash)}: ${binding.monthLargusNewExpensesWashTextView.text}\n" +
                "${resources.getString(R.string.other)}: ${binding.monthLargusNewExpensesOtherTextView.text}\n" +
                "   ${resources.getString(R.string.car_vestaSW)}: ${binding.monthVestaSWTotalExpensesTextView.text}\n" +
                "${resources.getString(R.string.fuel)} ${binding.monthVestaSWExpensesFuelTextView.text}\n" +
                "${resources.getString(R.string.wash)}: ${binding.monthVestaSWExpensesWashTextView.text}\n" +
                "${resources.getString(R.string.other)}: ${binding.monthVestaSWExpensesOtherTextView.text}\n" +
                "\n" +
                "${resources.getString(R.string.salary)} ${binding.monthSalaryTextView.text}\n" +
                "${resources.getString(R.string.prepay)}: ${binding.monthPrepayTextView.text}\n" +
                "${resources.getString(R.string.holiday_pay)}: ${binding.monthHolidayPayTextView.text}\n" +
                "${resources.getString(R.string.qualityPay)}: ${binding.monthQualityPayTextView.text}\n" +
                "${resources.getString(R.string.penalty)}: ${binding.monthPenaltyTextView.text}\n" +
                "${resources.getString(R.string.money_to_recieve)} ${binding.monthToRecieveTextView.text}"
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, textToSend)
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, resources.getString(R.string.share)))
    }
}
