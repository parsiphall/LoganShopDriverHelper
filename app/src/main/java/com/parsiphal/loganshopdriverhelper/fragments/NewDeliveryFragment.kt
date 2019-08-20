package com.parsiphal.loganshopdriverhelper.fragments

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.parsiphal.loganshopdriverhelper.DB

import com.parsiphal.loganshopdriverhelper.R
import com.parsiphal.loganshopdriverhelper.data.Delivery
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import com.parsiphal.loganshopdriverhelper.prefs
import kotlinx.android.synthetic.main.fragment_new_delivery.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class NewDeliveryFragment : MvpAppCompatFragment() {

    private lateinit var delivery: Delivery
    private lateinit var callBackActivity: MainView

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callBackActivity = context as MainView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delivery = Delivery()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_delivery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newDelivery_work_type_spinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        newDelivery_delivery_type.visibility = View.VISIBLE
                        newDelivery_salary_type.visibility = View.GONE
                        newDelivery_pay_type.visibility = View.VISIBLE
                        newDelivery_expense.visibility = View.GONE
                        newDelivery_address.visibility = View.VISIBLE
                        newDelivery_cost.visibility = View.VISIBLE
                        newDelivery_cost_cash.visibility = View.VISIBLE
                        newDelivery_move.visibility = View.GONE
                        newDelivery_isSalary.visibility = View.GONE
                    }
                    1 -> {
                        newDelivery_delivery_type.visibility = View.VISIBLE
                        newDelivery_salary_type.visibility = View.GONE
                        newDelivery_pay_type.visibility = View.GONE
                        newDelivery_expense.visibility = View.GONE
                        newDelivery_address.visibility = View.GONE
                        newDelivery_cost.visibility = View.GONE
                        newDelivery_cost_cash.visibility = View.GONE
                        newDelivery_move.visibility = View.VISIBLE
                        newDelivery_isSalary.visibility = View.VISIBLE
                    }
                    2 -> {
                        newDelivery_delivery_type.visibility = View.VISIBLE
                        newDelivery_salary_type.visibility = View.GONE
                        newDelivery_pay_type.visibility = View.GONE
                        newDelivery_expense.visibility = View.GONE
                        newDelivery_address.visibility = View.GONE
                        newDelivery_cost.visibility = View.GONE
                        newDelivery_cost_cash.visibility = View.GONE
                        newDelivery_move.visibility = View.GONE
                        newDelivery_isSalary.visibility = View.VISIBLE
                    }
                    3 -> {
                        newDelivery_delivery_type.visibility = View.GONE
                        newDelivery_salary_type.visibility = View.GONE
                        newDelivery_pay_type.visibility = View.GONE
                        newDelivery_expense.visibility = View.VISIBLE
                        newDelivery_address.visibility = View.GONE
                        newDelivery_cost.visibility = View.VISIBLE
                        newDelivery_cost_cash.visibility = View.GONE
                        newDelivery_move.visibility = View.GONE
                        newDelivery_isSalary.visibility = View.GONE
                    }
                    4 -> {
                        newDelivery_delivery_type.visibility = View.GONE
                        newDelivery_salary_type.visibility = View.GONE
                        newDelivery_pay_type.visibility = View.GONE
                        newDelivery_expense.visibility = View.GONE
                        newDelivery_address.visibility = View.GONE
                        newDelivery_cost.visibility = View.GONE
                        newDelivery_cost_cash.visibility = View.GONE
                        newDelivery_move.visibility = View.GONE
                        newDelivery_isSalary.visibility = View.GONE
                    }
                    5 -> {
                        newDelivery_delivery_type.visibility = View.GONE
                        newDelivery_salary_type.visibility = View.VISIBLE
                        newDelivery_pay_type.visibility = View.GONE
                        newDelivery_expense.visibility = View.GONE
                        newDelivery_address.visibility = View.GONE
                        newDelivery_cost.visibility = View.VISIBLE
                        newDelivery_cost_cash.visibility = View.GONE
                        newDelivery_move.visibility = View.GONE
                        newDelivery_isSalary.visibility = View.GONE
                    }
                }
            }
        })
        newDelivery_write.setOnClickListener {
            try {
                when (newDelivery_work_type_spinner.selectedItemPosition) {
                    0 -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = newDelivery_work_type_spinner.selectedItemPosition
                        delivery.deliveryType = newDelivery_delivery_type_spinner.selectedItemPosition
                        delivery.payType = newDelivery_pay_type_spinner.selectedItemPosition
                        delivery.address = newDelivery_address.text.toString()
                        delivery.cost = newDelivery_cost_editText.text.toString().toInt()
                        if (newDelivery_cost_cash_editText.text.toString() == "") {
                            delivery.expense = 0
                        } else {
                            delivery.expense =
                                (newDelivery_cost_cash_editText.text.toString().toInt() - newDelivery_cost_editText.text.toString().toInt())
                        }
                        delivery.comment = newDelivery_comment.text.toString()
                    }
                    1 -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = newDelivery_work_type_spinner.selectedItemPosition
                        delivery.deliveryType = newDelivery_delivery_type_spinner.selectedItemPosition
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
                    2 -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = newDelivery_work_type_spinner.selectedItemPosition
                        delivery.deliveryType = newDelivery_delivery_type_spinner.selectedItemPosition
                        if (newDelivery_isSalary_CheckBox.isChecked) {
                            delivery.ifSalary = 0
                            delivery.comment = "${resources.getString(R.string.no_salary)}\n" +
                                    "${newDelivery_comment.text}"
                        } else {
                            delivery.ifSalary = 1
                            delivery.comment = newDelivery_comment.text.toString()
                        }
                    }
                    3 -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = newDelivery_work_type_spinner.selectedItemPosition
                        delivery.comment = newDelivery_comment.text.toString()
                        delivery.cost = newDelivery_cost_editText.text.toString().toInt()
                        delivery.expenseType = newDelivery_expense_spinner.selectedItemPosition
                        if (newDelivery_comment.text.toString() == "") {
                            delivery.comment = newDelivery_expense_spinner.selectedItem.toString()
                        } else {
                            delivery.comment = "${newDelivery_expense_spinner.selectedItem}\n" +
                                    "${newDelivery_comment.text}"
                        }
                    }
                    4 -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = newDelivery_work_type_spinner.selectedItemPosition
                        delivery.comment = newDelivery_comment.text.toString()
                    }
                    5 -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = newDelivery_work_type_spinner.selectedItemPosition
                        delivery.deliveryType = newDelivery_salary_type_spinner.selectedItemPosition
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
    }

    private fun saveToBase() = GlobalScope.launch {
        DB.getDao().addDelivery(delivery)
    }
}
