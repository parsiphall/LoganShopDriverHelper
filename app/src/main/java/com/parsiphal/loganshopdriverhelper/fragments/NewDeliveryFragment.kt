package com.parsiphal.loganshopdriverhelper.fragments

import android.content.Context
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.parsiphal.loganshopdriverhelper.DB

import com.parsiphal.loganshopdriverhelper.R
import com.parsiphal.loganshopdriverhelper.data.Delivery
import com.parsiphal.loganshopdriverhelper.data.WorkType
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import com.parsiphal.loganshopdriverhelper.prefs
import kotlinx.android.synthetic.main.fragment_new_delivery.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import moxy.MvpAppCompatFragment

@ExperimentalCoroutinesApi
class NewDeliveryFragment : MvpAppCompatFragment() {

    private lateinit var delivery: Delivery
    private lateinit var callBackActivity: MainView
    private var newDelivery = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBackActivity = context as MainView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            delivery = bundle.getSerializable("ITEM") as Delivery
            newDelivery = false
        } else {
            delivery = Delivery()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_delivery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!newDelivery) {
            when (delivery.workType) {
                WorkType.Delivery.i -> {
                    layoutDelivery()
                    newDelivery_cost_cash_editText.setText((delivery.expense + delivery.cost).toString())
                }
                WorkType.Move.i -> {
                    layoutMove()
                }
                WorkType.Task.i -> {
                    layoutTask()
                }
                WorkType.Expense.i -> {
                    layoutExpence()
                }
                WorkType.Other.i -> {
                    layoutOther()
                }
                WorkType.Salary.i -> {
                    layoutSalary()
                }
            }
            newDelivery_work_type_spinner.setSelection(delivery.workType)
            newDelivery_delivery_type_spinner.setSelection(delivery.deliveryType)
            newDelivery_pay_type_spinner.setSelection(delivery.payType)
            newDelivery_address.setText(delivery.address)
            newDelivery_cost_editText.setText(delivery.cost.toString())
            newDelivery_comment.setText(delivery.commentSimple)
            newDelivery_expense_spinner.setSelection(delivery.expenseType)
            newDelivery_isSalary_CheckBox.isChecked = delivery.ifSalary != 1
            if (delivery.moveFrom != 4) {
                newDelivery_move_from_spinner.setSelection(delivery.moveFrom)
            }
            if (delivery.moveTo != 4) {
                newDelivery_move_to_spinner.setSelection(delivery.moveTo)
            }
        }
        newDelivery_work_type_spinner.onItemSelectedListener =
            (object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        WorkType.Delivery.i -> {
                            layoutDelivery()
                        }
                        WorkType.Move.i -> {
                            layoutMove()
                        }
                        WorkType.Task.i -> {
                            layoutTask()
                        }
                        WorkType.Expense.i -> {
                            layoutExpence()
                        }
                        WorkType.Other.i -> {
                            layoutOther()
                        }
                        WorkType.Salary.i -> {
                            layoutSalary()
                        }
                    }
                }
            })
        newDelivery_write.setOnClickListener {
            try {
                when (newDelivery_work_type_spinner.selectedItemPosition) {
                    WorkType.Delivery.i -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = newDelivery_work_type_spinner.selectedItemPosition
                        delivery.deliveryType =
                            newDelivery_delivery_type_spinner.selectedItemPosition
                        delivery.payType = newDelivery_pay_type_spinner.selectedItemPosition
                        delivery.address = newDelivery_address.text.toString()
                        delivery.cost = newDelivery_cost_editText.text.toString().toInt()
                        if (newDelivery_cost_cash_editText.text.toString() == "") {
                            delivery.expense = 0
                        } else {
                            delivery.expense =
                                (newDelivery_cost_cash_editText.text.toString().toInt() - newDelivery_cost_editText.text.toString().toInt())
                        }
                        delivery.commentSimple = newDelivery_comment.text.toString()
                    }
                    WorkType.Move.i -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = newDelivery_work_type_spinner.selectedItemPosition
                        delivery.deliveryType =
                            newDelivery_delivery_type_spinner.selectedItemPosition
                        delivery.moveFrom = newDelivery_move_from_spinner.selectedItemPosition
                        delivery.moveTo = newDelivery_move_to_spinner.selectedItemPosition
                        delivery.commentSimple = newDelivery_comment.text.toString()
                        if (newDelivery_isSalary_CheckBox.isChecked) {
                            delivery.ifSalary = 0
                            if (newDelivery_comment.text.toString() == "") {
                                delivery.comment = "${resources.getString(R.string.no_salary)}\n" +
                                        "${resources.getString(R.string.move_from)} " +
                                        "${newDelivery_move_from_spinner.selectedItem} " +
                                        "${resources.getString(R.string.move_to)} " +
                                        "${newDelivery_move_to_spinner.selectedItem}"
                            } else {
                                delivery.comment = "${resources.getString(R.string.no_salary)}\n" +
                                        "${resources.getString(R.string.move_from)} " +
                                        "${newDelivery_move_from_spinner.selectedItem} " +
                                        "${resources.getString(R.string.move_to)} " +
                                        "${newDelivery_move_to_spinner.selectedItem}\n" +
                                        "${newDelivery_comment.text}"
                            }
                        } else {
                            delivery.ifSalary = 1
                            if (newDelivery_comment.text.toString() == "") {
                                delivery.comment = "${resources.getString(R.string.move_from)} " +
                                        "${newDelivery_move_from_spinner.selectedItem} " +
                                        "${resources.getString(R.string.move_to)} " +
                                        "${newDelivery_move_to_spinner.selectedItem}"
                            } else {
                                delivery.comment = "${resources.getString(R.string.move_from)} " +
                                        "${newDelivery_move_from_spinner.selectedItem} " +
                                        "${resources.getString(R.string.move_to)} " +
                                        "${newDelivery_move_to_spinner.selectedItem}\n" +
                                        "${newDelivery_comment.text}"
                            }
                        }
                    }
                    WorkType.Task.i -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = newDelivery_work_type_spinner.selectedItemPosition
                        delivery.deliveryType =
                            newDelivery_delivery_type_spinner.selectedItemPosition
                        if (newDelivery_move_task_switch.isChecked) {
                            delivery.moveFrom = 4
                            delivery.moveTo = 4
                        } else {
                            delivery.moveFrom = newDelivery_move_from_spinner.selectedItemPosition
                            delivery.moveTo = newDelivery_move_to_spinner.selectedItemPosition
                        }
                        delivery.commentSimple = newDelivery_comment.text.toString()
                        if (newDelivery_isSalary_CheckBox.isChecked && !newDelivery_move_task_switch.isChecked) {
                            delivery.ifSalary = 0
                            if (newDelivery_comment.text.toString() == "") {
                                delivery.comment = "${resources.getString(R.string.no_salary)}\n" +
                                        "${resources.getString(R.string.move_from)} " +
                                        "${newDelivery_move_from_spinner.selectedItem} " +
                                        "${resources.getString(R.string.move_to)} " +
                                        "${newDelivery_move_to_spinner.selectedItem}"
                            } else {
                                delivery.comment = "${resources.getString(R.string.no_salary)}\n" +
                                        "${resources.getString(R.string.move_from)} " +
                                        "${newDelivery_move_from_spinner.selectedItem} " +
                                        "${resources.getString(R.string.move_to)} " +
                                        "${newDelivery_move_to_spinner.selectedItem}\n" +
                                        "${newDelivery_comment.text}"
                            }
                        } else if (!newDelivery_isSalary_CheckBox.isChecked && !newDelivery_move_task_switch.isChecked) {
                            delivery.ifSalary = 1
                            if (newDelivery_comment.text.toString() == "") {
                                delivery.comment = "${resources.getString(R.string.move_from)} " +
                                        "${newDelivery_move_from_spinner.selectedItem} " +
                                        "${resources.getString(R.string.move_to)} " +
                                        "${newDelivery_move_to_spinner.selectedItem}"
                            } else {
                                delivery.comment = "${resources.getString(R.string.move_from)} " +
                                        "${newDelivery_move_from_spinner.selectedItem} " +
                                        "${resources.getString(R.string.move_to)} " +
                                        "${newDelivery_move_to_spinner.selectedItem}\n" +
                                        "${newDelivery_comment.text}"
                            }
                        } else if (newDelivery_isSalary_CheckBox.isChecked && newDelivery_move_task_switch.isChecked) {
                            delivery.ifSalary = 0
                            if (newDelivery_comment.text.toString() == "") {
                                delivery.comment = "${resources.getString(R.string.no_salary)}\n" +
                                        resources.getString(R.string.switch_else)
                            } else {
                                delivery.comment = "${resources.getString(R.string.no_salary)}\n" +
                                        "${resources.getString(R.string.switch_else)}\n" +
                                        "${newDelivery_comment.text}"
                            }
                        } else if (!newDelivery_isSalary_CheckBox.isChecked && newDelivery_move_task_switch.isChecked) {
                            delivery.ifSalary = 1
                            if (newDelivery_comment.text.toString() == "") {
                                delivery.comment = resources.getString(R.string.switch_else)
                            } else {
                                delivery.comment =
                                    "${resources.getString(R.string.switch_else)}\n" +
                                            "${newDelivery_comment.text}"
                            }
                        }
                    }
                    WorkType.Expense.i -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = newDelivery_work_type_spinner.selectedItemPosition
                        delivery.comment = newDelivery_comment.text.toString()
                        delivery.cost = newDelivery_cost_editText.text.toString().toInt()
                        delivery.expenseType = newDelivery_expense_spinner.selectedItemPosition
                        delivery.commentSimple = newDelivery_comment.text.toString()
                        if (newDelivery_comment.text.toString() == "") {
                            delivery.comment = newDelivery_expense_spinner.selectedItem.toString()
                        } else {
                            delivery.comment = "${newDelivery_expense_spinner.selectedItem}\n" +
                                    "${newDelivery_comment.text}"
                        }
                    }
                    WorkType.Other.i -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = newDelivery_work_type_spinner.selectedItemPosition
                        delivery.commentSimple = newDelivery_comment.text.toString()
                    }
                    WorkType.Salary.i -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = newDelivery_work_type_spinner.selectedItemPosition
                        delivery.deliveryType = newDelivery_salary_type_spinner.selectedItemPosition
                        delivery.commentSimple = newDelivery_comment.text.toString()
                        delivery.comment = "${newDelivery_salary_type_spinner.selectedItem}\n" +
                                "${newDelivery_comment.text}"
                        delivery.cost = newDelivery_cost_editText.text.toString().toInt()
                    }
                }
                saveToBase()
                callBackActivity.fragmentPlace(DeliveryFragment())
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
        newDelivery_move_task_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                newDelivery_move_task_switch.text = resources.getString(R.string.switch_else)
                newDelivery_move.visibility = View.GONE
            } else {
                newDelivery_move_task_switch.text =
                    resources.getString(R.string.switch_between_shops)
                newDelivery_move.visibility = View.VISIBLE
            }
        }
    }

    private fun layoutSalary() {
        newDelivery_delivery_type.visibility = View.GONE
        newDelivery_salary_type.visibility = View.VISIBLE
        newDelivery_pay_type.visibility = View.GONE
        newDelivery_expense.visibility = View.GONE
        newDelivery_move_task.visibility = View.GONE
        newDelivery_move_task_switch.isChecked = false
        newDelivery_address.visibility = View.GONE
        newDelivery_cost.visibility = View.VISIBLE
        newDelivery_cost_cash.visibility = View.GONE
        newDelivery_move.visibility = View.GONE
        newDelivery_isSalary.visibility = View.GONE
    }

    private fun layoutOther() {
        newDelivery_delivery_type.visibility = View.GONE
        newDelivery_salary_type.visibility = View.GONE
        newDelivery_pay_type.visibility = View.GONE
        newDelivery_expense.visibility = View.GONE
        newDelivery_move_task.visibility = View.GONE
        newDelivery_move_task_switch.isChecked = false
        newDelivery_address.visibility = View.GONE
        newDelivery_cost.visibility = View.GONE
        newDelivery_cost_cash.visibility = View.GONE
        newDelivery_move.visibility = View.GONE
        newDelivery_isSalary.visibility = View.GONE
    }

    private fun layoutExpence() {
        newDelivery_delivery_type.visibility = View.GONE
        newDelivery_salary_type.visibility = View.GONE
        newDelivery_pay_type.visibility = View.GONE
        newDelivery_expense.visibility = View.VISIBLE
        newDelivery_move_task.visibility = View.GONE
        newDelivery_move_task_switch.isChecked = false
        newDelivery_address.visibility = View.GONE
        newDelivery_cost.visibility = View.VISIBLE
        newDelivery_cost_cash.visibility = View.GONE
        newDelivery_move.visibility = View.GONE
        newDelivery_isSalary.visibility = View.GONE
    }

    private fun layoutTask() {
        newDelivery_delivery_type.visibility = View.VISIBLE
        newDelivery_salary_type.visibility = View.GONE
        newDelivery_pay_type.visibility = View.GONE
        newDelivery_expense.visibility = View.GONE
        newDelivery_move_task.visibility = View.VISIBLE
        newDelivery_move_task_switch.isChecked = false
        newDelivery_address.visibility = View.GONE
        newDelivery_cost.visibility = View.GONE
        newDelivery_cost_cash.visibility = View.GONE
        newDelivery_move.visibility = View.VISIBLE
        newDelivery_isSalary.visibility = View.VISIBLE
    }

    private fun layoutMove() {
        newDelivery_delivery_type.visibility = View.VISIBLE
        newDelivery_salary_type.visibility = View.GONE
        newDelivery_pay_type.visibility = View.GONE
        newDelivery_expense.visibility = View.GONE
        newDelivery_move_task.visibility = View.GONE
        newDelivery_move_task_switch.isChecked = false
        newDelivery_address.visibility = View.GONE
        newDelivery_cost.visibility = View.GONE
        newDelivery_cost_cash.visibility = View.GONE
        newDelivery_move.visibility = View.VISIBLE
        newDelivery_isSalary.visibility = View.VISIBLE
    }

    private fun layoutDelivery() {
        newDelivery_delivery_type.visibility = View.VISIBLE
        newDelivery_salary_type.visibility = View.GONE
        newDelivery_pay_type.visibility = View.VISIBLE
        newDelivery_expense.visibility = View.GONE
        newDelivery_move_task.visibility = View.GONE
        newDelivery_move_task_switch.isChecked = false
        newDelivery_address.visibility = View.VISIBLE
        newDelivery_cost.visibility = View.VISIBLE
        newDelivery_cost_cash.visibility = View.VISIBLE
        newDelivery_move.visibility = View.GONE
        newDelivery_isSalary.visibility = View.GONE
    }

    private fun saveToBase() = GlobalScope.launch {
        if (newDelivery) {
            DB.getDao().addDelivery(delivery)
        } else {
            DB.getDao().updateDelivery(delivery)
        }

    }
}
