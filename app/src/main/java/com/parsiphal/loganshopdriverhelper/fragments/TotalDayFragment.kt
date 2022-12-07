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
import com.parsiphal.loganshopdriverhelper.databinding.FragmentTotalDayBinding
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import com.parsiphal.loganshopdriverhelper.prefs
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
    private var loganMoveFromVeteranov = 0
    private var loganMoveToZhukova = 0
    private var loganMoveToKulturi = 0
    private var loganMoveToSedova = 0
    private var loganMoveToHimikov = 0
    private var loganMoveToPlanernaya = 0
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
    private var _binding: FragmentTotalDayBinding? = null
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
        _binding = FragmentTotalDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getOrPlace(newTotal)
        binding.dayWrite.setOnClickListener {
            saveData()
        }
        binding.dayShare.setOnClickListener {
            shareData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getOrPlace(newTotal: Boolean) {
        val data = GlobalScope.async {
            if (newTotal) {
                getData()
                binding.dayShare.visibility = View.GONE
            } else {
                placeData()
                binding.dayWrite.visibility = View.GONE
            }
        }
        MainScope().launch {
            data.await()
            binding.dayProgress.visibility = View.GONE
            binding.dayData.visibility = View.VISIBLE
        }
    }

    private fun getData() {
        items = DB.getDao().getDeliveriesByDate(prefs.date!!)
        calculateSum()
        MainScope().launch { setData() }
    }

    private fun setData() {
        val car = "${prefs.region} - ${prefs.car}"
        binding.dayCarTextView.text = car
        binding.dayDateTextView.text = prefs.date
        binding.dayMorningOdoTextView.text = prefs.morningODO.toString()
        binding.dayEveningOdoTextView.text = prefs.eveningODO.toString()
        binding.dayMorningFuelTextView.text = prefs.morningFuel?.toString()
        binding.dayEveningFuelTextView.text = prefs.eveningFuel?.toString()
        binding.dayTotalMoneyTextView.text = totalMoney.toString()
        binding.dayTotalMoneyCashTextView.text = totalCash.toString()
        binding.dayTotalMoneyCardTextView.text = totalCard.toString()
        binding.dayLoganDeliveryValueTextView.text = deliveryValueLogan.toString()
        binding.dayLoganMoneyTextView.text = loganMoney.toString()
        binding.dayLoganCashTextView.text = loganCash.toString()
        binding.dayLoganCardTextView.text = loganCard.toString()
        binding.dayVestaDeliveryValueTextView.text = deliveryValueVesta.toString()
        binding.dayVestaMoneyTextView.text = vestaMoney.toString()
        binding.dayVestaCashTextView.text = vestaCash.toString()
        binding.dayVestaCardTextView.text = vestaCard.toString()
        binding.dayTeaTextVew.text = teaMoney.toString()
        binding.dayTotalDeliveriesTextVew.text = totalDeliveries.toString()
        binding.dayLoganMoveTextView.text = (loganMoveWithSalary + loganMoveWithOutSalary).toString()
        binding.dayLoganZhukovaMoveTextView.text = loganMoveToZhukova.toString()
        binding.dayLoganKulturiMoveTextView.text = loganMoveToKulturi.toString()
        binding.dayLoganSedovaMoveTextView.text = loganMoveToSedova.toString()
        binding.dayLoganHimikovMoveTextView.text = loganMoveToHimikov.toString()
        binding.dayLoganPlanernayaMoveTextView.text = loganMoveToPlanernaya.toString()
        binding.dayLoganVeteranovMoveTextView.text = loganMoveToVeteranov.toString()
        binding.dayVestaMoveTextView.text = (vestaMoveWithSalary + vestaMoveWithOutSalary).toString()
        binding.dayVestaZhukovaMoveTextView.text = vestaMoveToZhukova.toString()
        binding.dayVestaKulturiMoveTextView.text = vestaMoveToKulturi.toString()
        binding.dayVestaSedovaMoveTextView.text = vestaMoveToSedova.toString()
        binding.dayVestaHimikovMoveTextView.text = vestaMoveToHimikov.toString()
        binding.dayVestaPlanernayaMoveTextView.text = vestaMoveToPlanernaya.toString()
        binding.dayVestaVeteranovMoveTextView.text = vestaMoveToVeteranov.toString()
        binding.dayTotalMoveTextView.text = "${totalMoveToPay}(${totalMove})"
        binding.dayLoganTaskTextView.text = (loganTaskWithSalary + loganTaskWithOutSalary).toString()
        binding.dayLoganZhukovaTaskTextView.text = loganTaskToZhukova.toString()
        binding.dayLoganKulturiTaskTextView.text = loganTaskToKulturi.toString()
        binding.dayLoganSedovaTaskTextView.text = loganTaskToSedova.toString()
        binding.dayLoganHimikovTaskTextView.text = loganTaskToHimikov.toString()
        binding.dayLoganPlanernayaTaskTextView.text = loganTaskToPlanernaya.toString()
        binding.dayLoganVeteranovTaskTextView.text = loganTaskToVeteranov.toString()
        binding.dayLoganElseTaskTextView.text = loganTaskElse.toString()
        binding.dayVestaTaskTextView.text = (vestaTaskWithSalary + vestaTaskWithOutSalary).toString()
        binding.dayVestaZhukovaTaskTextView.text = vestaTaskToZhukova.toString()
        binding.dayVestaKulturiTaskTextView.text = vestaTaskToKulturi.toString()
        binding.dayVestaSedovaTaskTextView.text = vestaTaskToSedova.toString()
        binding.dayVestaHimikovTaskTextView.text = vestaTaskToHimikov.toString()
        binding.dayVestaPlanernayaTaskTextView.text = vestaTaskToPlanernaya.toString()
        binding.dayVestaVeteranovTaskTextView.text = vestaTaskToVeteranov.toString()
        binding.dayVestaElseTaskTextView.text = vestaTaskElse.toString()
        binding.dayTotalTaskTextView.text = "${totalTaskToPay}(${totalTask})"
        binding.dayExpensesTextView.text = (expenseFuel + expenseWash + expenseOther).toString()
        binding.dayExpensesFuelTextView.text = expenseFuel.toString()
        binding.dayExpensesWashTextView.text = expenseWash.toString()
        binding.dayExpensesOtherTextView.text = expenseOther.toString()
        binding.daySalaryTextView.text = salary.toString()
        binding.dayPrepayTextVew.text = prepay.toString()
        binding.dayHolidayTextVew.text = holiday.toString()
        binding.dayExtraPayTextView.text = extraPay.toString()
        binding.dayQualityPayTextView.text = qualityPay.toString()
        binding.dayPenaltyTextView.text = penalty.toString()
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
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Logan.i && position.moveFrom == Shops.Veteranov.i) {
                loganMoveFromVeteranov++
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
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Logan.i && position.moveTo == Shops.Veteranov.i) {
                loganMoveToVeteranov++
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
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Vesta.i && position.moveFrom == Shops.Veteranov.i) {
                vestaMoveFromVeteranov++
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
            if (position.workType == WorkType.Move.i && position.deliveryType == DeliveryType.Vesta.i && position.moveTo == Shops.Veteranov.i) {
                vestaMoveToVeteranov++
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
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Logan.i && position.moveFrom == Shops.Veteranov.i) {
                loganTaskFromVeteranov++
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
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Logan.i && position.moveTo == Shops.Veteranov.i) {
                loganTaskToVeteranov++
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
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Vesta.i && position.moveFrom == Shops.Veteranov.i) {
                vestaTaskFromVeteranov++
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
            if (position.workType == WorkType.Task.i && position.deliveryType == DeliveryType.Vesta.i && position.moveTo == Shops.Veteranov.i) {
                vestaTaskToVeteranov++
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
            (Salary.Employee.i + totalDeliveries * Salary.Delivery.i + totalMoveToPay * Salary.Move.i + totalTaskToPay * Salary.Task.i - penalty)
        } else {
            Salary.Newbie.i
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
            total.morningFuel = prefs.morningFuel!!
            total.eveningFuel = prefs.eveningFuel!!
            total.totalMoney = binding.dayTotalMoneyTextView.text.toString().toInt()
            total.totalCash = binding.dayTotalMoneyCashTextView.text.toString().toInt()
            total.totalCard = binding.dayTotalMoneyCardTextView.text.toString().toInt()
            total.loganDeliveryValue = binding.dayLoganDeliveryValueTextView.text.toString().toInt()
            total.loganMoney = binding.dayLoganMoneyTextView.text.toString().toInt()
            total.loganCash = binding.dayLoganCashTextView.text.toString().toInt()
            total.loganCard = binding.dayLoganCardTextView.text.toString().toInt()
            total.vestaDeliveryValue = binding.dayVestaDeliveryValueTextView.text.toString().toInt()
            total.vestaMoney = binding.dayVestaMoneyTextView.text.toString().toInt()
            total.vestaCash = binding.dayVestaCashTextView.text.toString().toInt()
            total.vestaCard = binding.dayVestaCardTextView.text.toString().toInt()
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
            total.salary = binding.daySalaryTextView.text.toString().toInt()
            total.prepay = prepay
            total.holidayPay = holiday
            total.extraPay = extraPay
            total.qualityPay = qualityPay
            total.penalty = penalty
            total.expenses = binding.dayTeaTextVew.text.toString().toInt()
            total.deltaODO = deltaODO()
            total.carIndex = prefs.carPosition!!

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
            total.loganMoveToPlanernaya = loganMoveToPlanernaya
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
        binding.dayCarTextView.text = total.carModel
        binding.dayDateTextView.text = total.date
        binding.dayMorningOdoTextView.text = total.morningODO.toString()
        binding.dayEveningOdoTextView.text = total.eveningODO.toString()
        binding.dayMorningFuelTextView.text = total.morningFuel.toString()
        binding.dayEveningFuelTextView.text = total.eveningFuel.toString()
        binding.dayTotalMoneyTextView.text = total.totalMoney.toString()
        binding.dayTotalMoneyCashTextView.text = total.totalCash.toString()
        binding.dayTotalMoneyCardTextView.text = total.totalCard.toString()
        binding.dayLoganDeliveryValueTextView.text = total.loganDeliveryValue.toString()
        binding.dayLoganMoneyTextView.text = total.loganMoney.toString()
        binding.dayLoganCashTextView.text = total.loganCash.toString()
        binding.dayLoganCardTextView.text = total.loganCard.toString()
        binding.dayVestaDeliveryValueTextView.text = total.vestaDeliveryValue.toString()
        binding.dayVestaMoneyTextView.text = total.vestaMoney.toString()
        binding.dayVestaCashTextView.text = total.vestaCash.toString()
        binding.dayVestaCardTextView.text = total.vestaCard.toString()
        binding.dayExpensesTextView.text =
            (total.expensesFuel + total.expensesWash + total.expensesOther).toString()
        binding.dayExpensesFuelTextView.text = total.expensesFuel.toString()
        binding.dayExpensesWashTextView.text = total.expensesWash.toString()
        binding.dayExpensesOtherTextView.text = total.expensesOther.toString()
        binding.dayTotalDeliveriesTextVew.text = total.totalDeliveries.toString()

        binding.dayLoganMoveTextView.text = total.loganMove.toString()

        binding.dayLoganZhukovaMoveTextView.text = total.loganMoveToZhukova.toString()
        binding.dayLoganKulturiMoveTextView.text = total.loganMoveToKulturi.toString()
        binding.dayLoganSedovaMoveTextView.text = total.loganMoveToSedova.toString()
        binding.dayLoganHimikovMoveTextView.text = total.loganMoveToHimikov.toString()
        binding.dayLoganPlanernayaMoveTextView.text = total.loganMoveToPlanernaya.toString()
        binding.dayLoganVeteranovMoveTextView.text = total.loganMoveToVeteranov.toString()

        binding.dayVestaMoveTextView.text = total.vestaMove.toString()

        binding.dayVestaZhukovaMoveTextView.text = total.vestaMoveToZhukova.toString()
        binding.dayVestaKulturiMoveTextView.text = total.vestaMoveToKulturi.toString()
        binding.dayVestaSedovaMoveTextView.text = total.vestaMoveToSedova.toString()
        binding.dayVestaHimikovMoveTextView.text = total.vestaMoveToHimikov.toString()
        binding.dayVestaPlanernayaMoveTextView.text = total.vestaMoveToPlanernaya.toString()
        binding.dayVestaVeteranovMoveTextView.text = total.vestaMoveToVeteranov.toString()

        binding.dayTotalMoveTextView.text = "${total.movesWithSalary}(${total.totalMove})"
        binding.dayLoganTaskTextView.text = total.loganTask.toString()

        binding.dayLoganZhukovaTaskTextView.text = total.loganTaskToZhukova.toString()
        binding.dayLoganKulturiTaskTextView.text = total.loganTaskToKulturi.toString()
        binding.dayLoganSedovaTaskTextView.text = total.loganTaskToSedova.toString()
        binding.dayLoganHimikovTaskTextView.text = total.loganTaskToHimikov.toString()
        binding.dayLoganPlanernayaTaskTextView.text = total.loganTaskToPlanernaya.toString()
        binding.dayLoganVeteranovTaskTextView.text = total.loganTaskToVeteranov.toString()
        binding.dayLoganElseTaskTextView.text = total.loganTaskElse.toString()

        binding.dayVestaTaskTextView.text = total.vestaTask.toString()

        binding.dayVestaZhukovaTaskTextView.text = total.vestaTaskToZhukova.toString()
        binding.dayVestaKulturiTaskTextView.text = total.vestaTaskToKulturi.toString()
        binding.dayVestaSedovaTaskTextView.text = total.vestaTaskToSedova.toString()
        binding.dayVestaHimikovTaskTextView.text = total.vestaTaskToHimikov.toString()
        binding.dayVestaPlanernayaTaskTextView.text = total.vestaTaskToPlanernaya.toString()
        binding.dayVestaVeteranovTaskTextView.text = total.vestaTaskToVeteranov.toString()
        binding.dayVestaElseTaskTextView.text = total.vestaTaskElse.toString()

        binding.dayTotalTaskTextView.text = "${total.tasksWithSalary}(${total.totalTask})"
        binding.dayTeaTextVew.text = total.expenses.toString()
        binding.daySalaryTextView.text = total.salary.toString()
        binding.dayPrepayTextVew.text = total.prepay.toString()
        binding.dayHolidayTextVew.text = total.holidayPay.toString()
        binding.dayExtraPayTextView.text = total.extraPay.toString()
        binding.dayQualityPayTextView.text = total.qualityPay.toString()
        binding.dayPenaltyTextView.text = total.penalty.toString()
    }

    private fun shareData() = GlobalScope.launch {
        var textToSend = "${binding.dayCarTextView.text} (${binding.dayDateTextView.text})\n" +
                "${resources.getString(R.string.odo_morning)} ${binding.dayMorningOdoTextView.text}\n" +
                "${resources.getString(R.string.odo_evening)} ${binding.dayEveningOdoTextView.text}\n" +
                "${resources.getString(R.string.fuel_morning)} ${binding.dayMorningFuelTextView.text} ${
                    resources.getString(
                        R.string.fuel_dividers
                    )
                }\n" +
                "${resources.getString(R.string.fuel_evening)} ${binding.dayEveningFuelTextView.text} ${
                    resources.getString(
                        R.string.fuel_dividers
                    )
                }\n" +
                "\n" +
                "${resources.getString(R.string.totalMoney)}/${resources.getString(R.string.total_deliveries)}: " +
                "${binding.dayTotalMoneyTextView.text}/${binding.dayTotalDeliveriesTextVew.text}\n" +
                "   ${resources.getString(R.string.logan_divider)}\n" +
                "${resources.getString(R.string.money)}/${resources.getString(R.string.deliveryValue)}: " +
                "${binding.dayLoganMoneyTextView.text}/${binding.dayLoganDeliveryValueTextView.text}\n" +
                "${resources.getString(R.string.cash)}: ${binding.dayLoganCashTextView.text}\n" +
                "${resources.getString(R.string.card)}: ${binding.dayLoganCardTextView.text}\n" +
                "   ${resources.getString(R.string.vesta_divider)}\n" +
                "${resources.getString(R.string.money)}/${resources.getString(R.string.deliveryValue)}: " +
                "${binding.dayVestaMoneyTextView.text}/${binding.dayVestaDeliveryValueTextView.text}\n" +
                "${resources.getString(R.string.cash)}: ${binding.dayVestaCashTextView.text}\n" +
                "${resources.getString(R.string.card)}: ${binding.dayVestaCardTextView.text}\n" +
                "\n" +
                "${resources.getString(R.string.total_moves)}\n" +
                "   ${resources.getString(R.string.logan_divider)}: ${binding.dayLoganMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_veteranov)}: ${binding.dayLoganVeteranovMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_zhukova)}: ${binding.dayLoganZhukovaMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_kulturi)}: ${binding.dayLoganKulturiMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_planernaya)}: ${binding.dayLoganPlanernayaMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_sedova)}: ${binding.dayLoganSedovaMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_himikov)}: ${binding.dayLoganHimikovMoveTextView.text}\n" +
                "   ${resources.getString(R.string.vesta_divider)}: ${binding.dayVestaMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_veteranov)}: ${binding.dayVestaVeteranovMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_zhukova)}: ${binding.dayVestaZhukovaMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_kulturi)}: ${binding.dayVestaKulturiMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_planernaya)}: ${binding.dayVestaPlanernayaMoveTextView.text}\n" +
                "${resources.getString(R.string.shop_sedova)}: ${binding.dayVestaSedovaMoveTextView.text}\n" +
//                "${resources.getString(R.string.shop_himikov)}: ${binding.dayVestaHimikovMoveTextView.text}\n" +
                "${resources.getString(R.string.total_total)} ${binding.dayTotalMoveTextView.text}\n" +
                "\n" +
                "${resources.getString(R.string.total_tasks)}\n" +
                "   ${resources.getString(R.string.logan_divider)}: ${binding.dayLoganTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_veteranov)}: ${binding.dayLoganVeteranovTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_zhukova)}: ${binding.dayLoganZhukovaTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_kulturi)}: ${binding.dayLoganKulturiTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_planernaya)}: ${binding.dayLoganPlanernayaTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_sedova)}: ${binding.dayLoganSedovaTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_himikov)}: ${binding.dayLoganHimikovTaskTextView.text}\n" +
                "${resources.getString(R.string.switch_else)}: ${binding.dayLoganElseTaskTextView.text}\n" +
                "   ${resources.getString(R.string.vesta_divider)}: ${binding.dayVestaTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_veteranov)}: ${binding.dayVestaVeteranovTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_zhukova)}: ${binding.dayVestaZhukovaTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_kulturi)}: ${binding.dayVestaKulturiTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_planernaya)}: ${binding.dayVestaPlanernayaTaskTextView.text}\n" +
                "${resources.getString(R.string.shop_sedova)}: ${binding.dayVestaSedovaTaskTextView.text}\n" +
//                "${resources.getString(R.string.shop_himikov)}: ${binding.dayVestaHimikovTaskTextView.text}\n" +
                "${resources.getString(R.string.switch_else)}: ${binding.dayVestaElseTaskTextView.text}\n" +
                "${resources.getString(R.string.total_total)} ${binding.dayTotalTaskTextView.text}\n" +
                "\n" +
                "${resources.getString(R.string.expenses)}\n" +
                "${resources.getString(R.string.total_total)} ${binding.dayExpensesTextView.text}\n" +
                "${resources.getString(R.string.fuel)}: ${binding.dayExpensesFuelTextView.text}\n" +
                "${resources.getString(R.string.wash)}: ${binding.dayExpensesWashTextView.text}\n" +
                "${resources.getString(R.string.other)}: ${binding.dayExpensesOtherTextView.text}\n" +
                "\n" +
                "${resources.getString(R.string.salary)} ${prefs.family} ${binding.daySalaryTextView.text}"
        if (binding.dayPrepayTextVew.text.toString().toInt() != 0) {
            textToSend += "\n" +
                    "${resources.getString(R.string.prepay)}: ${binding.dayPrepayTextVew.text}"
        }
        if (binding.dayHolidayTextVew.text.toString().toInt() != 0) {
            textToSend += "\n" +
                    "${resources.getString(R.string.holiday_pay)}: ${binding.dayHolidayTextVew.text}"
        }
        if (binding.dayQualityPayTextView.text.toString().toInt() != 0) {
            textToSend += "\n" +
                    "${resources.getText(R.string.qualityPay)}: ${binding.dayQualityPayTextView.text}"
        }
        if (binding.dayPenaltyTextView.text.toString().toInt() != 0) {
            textToSend += "\n" +
                    "${resources.getText(R.string.penalty)}: ${binding.dayPenaltyTextView.text}"
        }
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, textToSend)
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, resources.getString(R.string.share)))
    }
}
