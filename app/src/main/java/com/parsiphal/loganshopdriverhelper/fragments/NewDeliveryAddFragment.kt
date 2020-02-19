package com.parsiphal.loganshopdriverhelper.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.parsiphal.loganshopdriverhelper.DB
import com.parsiphal.loganshopdriverhelper.R
import com.parsiphal.loganshopdriverhelper.data.*
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import com.parsiphal.loganshopdriverhelper.prefs
import kotlinx.android.synthetic.main.fragment_new_delivery_add.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import moxy.MvpAppCompatFragment


@ExperimentalCoroutinesApi
class NewDeliveryAddFragment : MvpAppCompatFragment() {

    private lateinit var delivery: Delivery
    private lateinit var callBackActivity: MainView
    private var isBetweenShops = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delivery = Delivery()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_delivery_add, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBackActivity = context as MainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        delivery.deliveryDate = prefs.date!!
        newDeliveryAdd_workType.visibility = View.VISIBLE
        workTypeListeners()
        deliveryTypeListeners()
        payTypeListeners()
        payingListeners()
        addressListeners()
        moveFromListeners()
        moveToListeners()
        commentAndPayListeners()
        taskTypeListeners()
        expenseListeners()
        payListeners()
        otherListeners()
    }

    private fun saveToBase() = GlobalScope.launch {
        DB.getDao().addDelivery(delivery)
    }

    private fun workTypeListeners() {
        newDeliveryAdd_workType_deliveryButton.setOnClickListener {
            delivery.workType = WorkType.Delivery.i
            newDeliveryAdd_workType.visibility = View.GONE
            newDeliveryAdd_deliveryType.visibility = View.VISIBLE
        }
        newDeliveryAdd_workType_moveButton.setOnClickListener {
            delivery.workType = WorkType.Move.i
            newDeliveryAdd_workType.visibility = View.GONE
            newDeliveryAdd_deliveryType.visibility = View.VISIBLE
        }
        newDeliveryAdd_workType_taskButton.setOnClickListener {
            delivery.workType = WorkType.Task.i
            newDeliveryAdd_workType.visibility = View.GONE
            newDeliveryAdd_deliveryType.visibility = View.VISIBLE
        }
        newDeliveryAdd_workType_expenseButton.setOnClickListener {
            delivery.workType = WorkType.Expense.i
            newDeliveryAdd_workType.visibility = View.GONE
            newDeliveryAdd_expense.visibility = View.VISIBLE
        }
        newDeliveryAdd_workType_payButton.setOnClickListener {
            delivery.workType = WorkType.Salary.i
            newDeliveryAdd_workType.visibility = View.GONE
            newDeliveryAdd_pay.visibility = View.VISIBLE
        }
        newDeliveryAdd_workType_otherButton.setOnClickListener {
            delivery.workType = WorkType.Other.i
            newDeliveryAdd_workType.visibility = View.GONE
            newDeliveryAdd_other.visibility = View.VISIBLE
        }
    }

    private fun deliveryTypeListeners() {
        newDeliveryAdd_deliveryType_logan.setOnClickListener {
            delivery.deliveryType = 0
            newDeliveryAdd_deliveryType.visibility = View.GONE
            when (delivery.workType) {
                WorkType.Delivery.i -> {
                    newDeliveryAdd_payType.visibility = View.VISIBLE
                }
                WorkType.Move.i -> {
                    newDeliveryAdd_moveFrom.visibility = View.VISIBLE
                }
                else -> {
                    newDeliveryAdd_taskType.visibility = View.VISIBLE
                }
            }
        }
        newDeliveryAdd_deliveryType_vesta.setOnClickListener {
            delivery.deliveryType = 1
            newDeliveryAdd_deliveryType.visibility = View.GONE
            when (delivery.workType) {
                WorkType.Delivery.i -> {
                    newDeliveryAdd_payType.visibility = View.VISIBLE
                }
                WorkType.Move.i -> {
                    newDeliveryAdd_moveFrom.visibility = View.VISIBLE
                    newDeliveryAdd_moveFrom_Himikov.visibility = View.GONE
                    newDeliveryAdd_moveTo_Himikov.visibility = View.GONE
                }
                else -> {
                    newDeliveryAdd_taskType.visibility = View.VISIBLE
                    newDeliveryAdd_moveFrom_Himikov.visibility = View.GONE
                    newDeliveryAdd_moveTo_Himikov.visibility = View.GONE
                }
            }
        }
    }

    private fun payTypeListeners() {
        newDeliveryAdd_payType_cash.setOnClickListener {
            delivery.payType = PayType.Cash.i
            newDeliveryAdd_payType.visibility = View.GONE
            newDeliveryAdd_address.visibility = View.VISIBLE
        }
        newDeliveryAdd_payType_card.setOnClickListener {
            delivery.payType = PayType.Card.i
            newDeliveryAdd_payType.visibility = View.GONE
            newDeliveryAdd_address.visibility = View.VISIBLE
        }
    }

    private fun payingListeners() {
        newDeliveryAdd_paying_next.setOnClickListener {
            try {
                delivery.cost = newDeliveryAdd_paying_cost.text.toString().toInt()
                if (newDeliveryAdd_paying_moneyTake.text.toString() == "") {
                    delivery.expense = 0
                } else {
                    delivery.expense =
                        (newDeliveryAdd_paying_moneyTake.text.toString().toInt() - newDeliveryAdd_paying_cost.text.toString().toInt())
                }
                save()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view!!, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun addressListeners() {
        newDeliveryAdd_address_save.setOnClickListener {
            if (newDeliveryAdd_address_address.text.toString() != "") {
                delivery.address = newDeliveryAdd_address_address.text.toString()
                delivery.comment = newDeliveryAdd_address_comment.text.toString()
                delivery.commentSimple = newDeliveryAdd_address_comment.text.toString()
                newDeliveryAdd_address.visibility = View.GONE
                newDeliveryAdd_paying.visibility = View.VISIBLE
            } else {
                Snackbar.make(view!!, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun save() {
        saveToBase()
        callBackActivity.fragmentPlace(DeliveryFragment())
    }

    private fun moveFromListeners() {
        newDeliveryAdd_moveFrom_Zhukova.setOnClickListener {
            delivery.moveFrom = Shops.Zhukova.i
            newDeliveryAdd_moveFrom.visibility = View.GONE
            newDeliveryAdd_moveTo.visibility = View.VISIBLE
        }
        newDeliveryAdd_moveFrom_Kulturi.setOnClickListener {
            delivery.moveFrom = Shops.Kulturi.i
            newDeliveryAdd_moveFrom.visibility = View.GONE
            newDeliveryAdd_moveTo.visibility = View.VISIBLE
        }
        newDeliveryAdd_moveFrom_Sedova.setOnClickListener {
            delivery.moveFrom = Shops.Sedova.i
            newDeliveryAdd_moveFrom.visibility = View.GONE
            newDeliveryAdd_moveTo.visibility = View.VISIBLE
        }
        newDeliveryAdd_moveFrom_Himikov.setOnClickListener {
            delivery.moveFrom = Shops.Himikov.i
            newDeliveryAdd_moveFrom.visibility = View.GONE
            newDeliveryAdd_moveTo.visibility = View.VISIBLE
        }
        newDeliveryAdd_moveFrom_Planernaya.setOnClickListener {
            delivery.moveFrom = Shops.Planernaya.i
            newDeliveryAdd_moveFrom.visibility = View.GONE
            newDeliveryAdd_moveTo.visibility = View.VISIBLE
        }
    }

    private fun moveToListeners() {
        newDeliveryAdd_moveTo_Zhukova.setOnClickListener {
            delivery.moveTo = Shops.Zhukova.i
            newDeliveryAdd_moveTo.visibility = View.GONE
            newDeliveryAdd_comment_and_pay.visibility = View.VISIBLE
        }
        newDeliveryAdd_moveTo_Kulturi.setOnClickListener {
            delivery.moveTo = Shops.Kulturi.i
            newDeliveryAdd_moveTo.visibility = View.GONE
            newDeliveryAdd_comment_and_pay.visibility = View.VISIBLE
        }
        newDeliveryAdd_moveTo_Sedova.setOnClickListener {
            delivery.moveTo = Shops.Sedova.i
            newDeliveryAdd_moveTo.visibility = View.GONE
            newDeliveryAdd_comment_and_pay.visibility = View.VISIBLE
        }
        newDeliveryAdd_moveTo_Himikov.setOnClickListener {
            delivery.moveTo = Shops.Himikov.i
            newDeliveryAdd_moveTo.visibility = View.GONE
            newDeliveryAdd_comment_and_pay.visibility = View.VISIBLE
        }
        newDeliveryAdd_moveTo_Planernaya.setOnClickListener {
            delivery.moveTo = Shops.Planernaya.i
            newDeliveryAdd_moveTo.visibility = View.GONE
            newDeliveryAdd_comment_and_pay.visibility = View.VISIBLE
        }
    }

    private fun commentAndPayListeners() {
        newDeliveryAdd_comment_and_pay_yes.setOnClickListener {
            delivery.ifSalary = 1
            delivery.commentSimple = newDeliveryAdd_comment_and_pay_comment.text.toString()
            if (isBetweenShops) {
                if (newDeliveryAdd_comment_and_pay_comment.text.toString() == "") {
                    delivery.comment = "${resources.getString(R.string.move_from)} " +
                            "${resources.getStringArray(R.array.shops)[delivery.moveFrom]} " +
                            "${resources.getString(R.string.move_to)} " +
                            "${resources.getStringArray(R.array.shops)[delivery.moveTo]}"
                } else {
                    delivery.comment = "${resources.getString(R.string.move_from)} " +
                            "${resources.getStringArray(R.array.shops)[delivery.moveFrom]} " +
                            "${resources.getString(R.string.move_to)} " +
                            "${resources.getStringArray(R.array.shops)[delivery.moveTo]}\n" +
                            "${newDeliveryAdd_comment_and_pay_comment.text}"
                }
            } else {
                if (newDeliveryAdd_comment_and_pay_comment.text.toString() == "") {
                    delivery.comment = resources.getString(R.string.switch_else)
                } else {
                    delivery.comment = "${resources.getString(R.string.switch_else)} \n" +
                            "${newDeliveryAdd_comment_and_pay_comment.text}"
                }
            }
            save()
        }
        newDeliveryAdd_comment_and_pay_no.setOnClickListener {
            delivery.ifSalary = 0
            delivery.commentSimple = newDeliveryAdd_comment_and_pay_comment.text.toString()
            if (isBetweenShops) {
                if (newDeliveryAdd_comment_and_pay_comment.text.toString() == "") {
                    delivery.comment = "${resources.getString(R.string.no_salary)}\n" +
                            "${resources.getString(R.string.move_from)} " +
                            "${resources.getStringArray(R.array.shops)[delivery.moveFrom]} " +
                            "${resources.getString(R.string.move_to)} " +
                            "${resources.getStringArray(R.array.shops)[delivery.moveTo]}"
                } else {
                    delivery.comment = "${resources.getString(R.string.no_salary)}\n" +
                            "${resources.getString(R.string.move_from)} " +
                            "${resources.getStringArray(R.array.shops)[delivery.moveFrom]} " +
                            "${resources.getString(R.string.move_to)} " +
                            "${resources.getStringArray(R.array.shops)[delivery.moveTo]}\n" +
                            "${newDeliveryAdd_comment_and_pay_comment.text}"
                }
            } else {
                if (newDeliveryAdd_comment_and_pay_comment.text.toString() == "") {
                    delivery.comment = "${resources.getString(R.string.no_salary)}\n" +
                            "${resources.getString(R.string.switch_else)}"
                } else {
                    delivery.comment = "${resources.getString(R.string.no_salary)}\n" +
                            "${resources.getString(R.string.switch_else)} \n" +
                            "${newDeliveryAdd_comment_and_pay_comment.text}"
                }
            }
            save()
        }
    }

    private fun taskTypeListeners() {
        newDeliveryAdd_moveTaskType_yes.setOnClickListener {
            isBetweenShops = true
            newDeliveryAdd_taskType.visibility = View.GONE
            newDeliveryAdd_moveFrom.visibility = View.VISIBLE
        }
        newDeliveryAdd_moveTaskType_no.setOnClickListener {
            isBetweenShops = false
            delivery.moveFrom = Shops.Other.i
            delivery.moveTo = Shops.Other.i
            newDeliveryAdd_taskType.visibility = View.GONE
            newDeliveryAdd_comment_and_pay.visibility = View.VISIBLE
        }
    }

    private fun expenseListeners() {
        newDeliveryAdd_expense_fuel.setOnClickListener {
            try {
                delivery.commentSimple = newDeliveryAdd_expense_comment.text.toString()
                delivery.cost = newDeliveryAdd_expense_cost.text.toString().toInt()
                delivery.expenseType = Expenses.Fuel.i
                if (newDeliveryAdd_expense_comment.text.toString() == "") {
                    delivery.comment = resources.getStringArray(R.array.expenses)[0]
                } else {
                    delivery.comment = "${resources.getStringArray(R.array.expenses)[0]} \n" +
                            "${newDeliveryAdd_expense_comment.text}"
                }
                save()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view!!, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
        newDeliveryAdd_expense_wash.setOnClickListener {
            try {
                delivery.commentSimple = newDeliveryAdd_expense_comment.text.toString()
                delivery.cost = newDeliveryAdd_expense_cost.text.toString().toInt()
                delivery.expenseType = Expenses.Wash.i
                if (newDeliveryAdd_expense_comment.text.toString() == "") {
                    delivery.comment = resources.getStringArray(R.array.expenses)[1]
                } else {
                    delivery.comment = "${resources.getStringArray(R.array.expenses)[1]} \n" +
                            "${newDeliveryAdd_expense_comment.text}"
                }
                save()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view!!, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
        newDeliveryAdd_expense_other.setOnClickListener {
            try {
                delivery.commentSimple = newDeliveryAdd_expense_comment.text.toString()
                delivery.cost = newDeliveryAdd_expense_cost.text.toString().toInt()
                delivery.expenseType = Expenses.Other.i
                if (newDeliveryAdd_expense_comment.text.toString() == "") {
                    delivery.comment = resources.getStringArray(R.array.expenses)[2]
                } else {
                    delivery.comment = "${resources.getStringArray(R.array.expenses)[2]} \n" +
                            "${newDeliveryAdd_expense_comment.text}"
                }
                save()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view!!, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun payListeners() {
        newDeliveryAdd_pay_prePay.setOnClickListener {
            try {
                delivery.deliveryType = SalaryType.Prepay.i
                delivery.cost = newDeliveryAdd_pay_cost.text.toString().toInt()
                delivery.comment = newDeliveryAdd_pay_comment.text.toString()
                delivery.commentSimple = newDeliveryAdd_pay_comment.text.toString()
                save()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view!!, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
        newDeliveryAdd_pay_holidayPay.setOnClickListener {
            try {
                delivery.deliveryType = SalaryType.Holiday.i
                delivery.cost = newDeliveryAdd_pay_cost.text.toString().toInt()
                delivery.comment = newDeliveryAdd_pay_comment.text.toString()
                delivery.commentSimple = newDeliveryAdd_pay_comment.text.toString()
                save()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view!!, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
        newDeliveryAdd_pay_extraPay.setOnClickListener {
            try {
                delivery.deliveryType = SalaryType.Extra.i
                delivery.cost = newDeliveryAdd_pay_cost.text.toString().toInt()
                delivery.comment = newDeliveryAdd_pay_comment.text.toString()
                delivery.commentSimple = newDeliveryAdd_pay_comment.text.toString()
                save()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view!!, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
        newDeliveryAdd_pay_penalty.setOnClickListener {
            try {
                delivery.deliveryType = SalaryType.Penalty.i
                delivery.cost = newDeliveryAdd_pay_cost.text.toString().toInt()
                delivery.comment = newDeliveryAdd_pay_comment.text.toString()
                delivery.commentSimple = newDeliveryAdd_pay_comment.text.toString()
                save()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view!!, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
        newDeliveryAdd_pay_qualityPay.setOnClickListener {
            try {
                delivery.deliveryType = SalaryType.Quality.i
                delivery.cost = newDeliveryAdd_pay_cost.text.toString().toInt()
                delivery.comment = newDeliveryAdd_pay_comment.text.toString()
                delivery.commentSimple = newDeliveryAdd_pay_comment.text.toString()
                save()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view!!, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun otherListeners() {
        newDeliveryAdd_other_save.setOnClickListener {
            delivery.comment = newDeliveryAdd_other_comment.text.toString()
            delivery.commentSimple = newDeliveryAdd_other_comment.text.toString()
            save()
        }
    }
}
