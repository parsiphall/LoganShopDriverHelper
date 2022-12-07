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
import com.parsiphal.loganshopdriverhelper.databinding.FragmentNewDeliveryBinding
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import com.parsiphal.loganshopdriverhelper.prefs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import moxy.MvpAppCompatFragment

@ExperimentalCoroutinesApi
class NewDeliveryFragment : MvpAppCompatFragment() {

    private lateinit var delivery: Delivery
    private lateinit var callBackActivity: MainView
    private var newDelivery = true
    private var _binding: FragmentNewDeliveryBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentNewDeliveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!newDelivery) {
            when (delivery.workType) {
                WorkType.Delivery.i -> {
                    layoutDelivery()
                    binding.newDeliveryCostCashEditText.setText((delivery.expense + delivery.cost).toString())
                }
                WorkType.Move.i -> {
                    layoutMove()
                }
                WorkType.Task.i -> {
                    layoutTask()
                }
                WorkType.Expense.i -> {
                    layoutExpense()
                }
                WorkType.Other.i -> {
                    layoutOther()
                }
                WorkType.Salary.i -> {
                    layoutSalary()
                }
            }
            binding.newDeliveryWorkTypeSpinner.setSelection(delivery.workType)
            binding.newDeliveryDeliveryTypeSpinner.setSelection(delivery.deliveryType)
            binding.newDeliveryPayTypeSpinner.setSelection(delivery.payType)
            binding.newDeliverySalaryTypeSpinner.setSelection(delivery.deliveryType)
            binding.newDeliveryAddress.setText(delivery.address)
            binding.newDeliveryCostEditText.setText(delivery.cost.toString())
            binding.newDeliveryComment.setText(delivery.commentSimple)
            binding.newDeliveryExpenseSpinner.setSelection(delivery.expenseType)
            binding.newDeliveryIsSalaryCheckBox.isChecked = delivery.ifSalary != 1
            if (delivery.moveFrom != -1) {
                binding.newDeliveryMoveFromSpinner.setSelection(delivery.moveFrom)
            }
            if (delivery.moveTo != -1) {
                binding.newDeliveryMoveToSpinner.setSelection(delivery.moveTo)
            }
        }
        binding.newDeliveryWorkTypeSpinner.onItemSelectedListener =
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
                            layoutExpense()
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
        binding.newDeliveryWrite.setOnClickListener {
            try {
                when (binding.newDeliveryWorkTypeSpinner.selectedItemPosition) {
                    WorkType.Delivery.i -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = binding.newDeliveryWorkTypeSpinner.selectedItemPosition
                        delivery.deliveryType =
                            binding.newDeliveryDeliveryTypeSpinner.selectedItemPosition
                        delivery.payType = binding.newDeliveryPayTypeSpinner.selectedItemPosition
                        delivery.address = binding.newDeliveryAddress.text.toString()
                        delivery.cost = binding.newDeliveryCostEditText.text.toString().toInt()
                        if (binding.newDeliveryCostCashEditText.text.toString() == "") {
                            delivery.expense = 0
                        } else {
                            delivery.expense =
                                (binding.newDeliveryCostCashEditText.text.toString().toInt() - binding.newDeliveryCostEditText.text.toString().toInt())
                        }
                        delivery.commentSimple = binding.newDeliveryComment.text.toString()
                        delivery.comment = binding.newDeliveryComment.text.toString()
                    }
                    WorkType.Move.i -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = binding.newDeliveryWorkTypeSpinner.selectedItemPosition
                        delivery.deliveryType =
                            binding.newDeliveryDeliveryTypeSpinner.selectedItemPosition
                        delivery.moveFrom = binding.newDeliveryMoveFromSpinner.selectedItemPosition
                        delivery.moveTo = binding.newDeliveryMoveToSpinner.selectedItemPosition
                        delivery.commentSimple = binding.newDeliveryComment.text.toString()
                        if (binding.newDeliveryIsSalaryCheckBox.isChecked) {
                            delivery.ifSalary = 0
                            if (binding.newDeliveryComment.text.toString() == "") {
                                delivery.comment = "${resources.getString(R.string.no_salary)}\n" +
                                        "${resources.getString(R.string.move_from)} " +
                                        "${binding.newDeliveryMoveFromSpinner.selectedItem} " +
                                        "${resources.getString(R.string.move_to)} " +
                                        "${binding.newDeliveryMoveToSpinner.selectedItem}"
                            } else {
                                delivery.comment = "${resources.getString(R.string.no_salary)}\n" +
                                        "${resources.getString(R.string.move_from)} " +
                                        "${binding.newDeliveryMoveFromSpinner.selectedItem} " +
                                        "${resources.getString(R.string.move_to)} " +
                                        "${binding.newDeliveryMoveToSpinner.selectedItem}\n" +
                                        "${binding.newDeliveryComment.text}"
                            }
                        } else {
                            delivery.ifSalary = 1
                            if (binding.newDeliveryComment.text.toString() == "") {
                                delivery.comment = "${resources.getString(R.string.move_from)} " +
                                        "${binding.newDeliveryMoveFromSpinner.selectedItem} " +
                                        "${resources.getString(R.string.move_to)} " +
                                        "${binding.newDeliveryMoveToSpinner.selectedItem}"
                            } else {
                                delivery.comment = "${resources.getString(R.string.move_from)} " +
                                        "${binding.newDeliveryMoveFromSpinner.selectedItem} " +
                                        "${resources.getString(R.string.move_to)} " +
                                        "${binding.newDeliveryMoveToSpinner.selectedItem}\n" +
                                        "${binding.newDeliveryComment.text}"
                            }
                        }
                    }
                    WorkType.Task.i -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = binding.newDeliveryWorkTypeSpinner.selectedItemPosition
                        delivery.deliveryType =
                            binding.newDeliveryDeliveryTypeSpinner.selectedItemPosition
                        if (binding.newDeliveryMoveTaskSwitch.isChecked) {
                            delivery.moveFrom = -1
                            delivery.moveTo = -1
                        } else {
                            delivery.moveFrom = binding.newDeliveryMoveFromSpinner.selectedItemPosition
                            delivery.moveTo = binding.newDeliveryMoveToSpinner.selectedItemPosition
                        }
                        delivery.commentSimple = binding.newDeliveryComment.text.toString()
                        if (binding.newDeliveryIsSalaryCheckBox.isChecked && !binding.newDeliveryMoveTaskSwitch.isChecked) {
                            delivery.ifSalary = 0
                            if (binding.newDeliveryComment.text.toString() == "") {
                                delivery.comment = "${resources.getString(R.string.no_salary)}\n" +
                                        "${resources.getString(R.string.move_from)} " +
                                        "${binding.newDeliveryMoveFromSpinner.selectedItem} " +
                                        "${resources.getString(R.string.move_to)} " +
                                        "${binding.newDeliveryMoveToSpinner.selectedItem}"
                            } else {
                                delivery.comment = "${resources.getString(R.string.no_salary)}\n" +
                                        "${resources.getString(R.string.move_from)} " +
                                        "${binding.newDeliveryMoveFromSpinner.selectedItem} " +
                                        "${resources.getString(R.string.move_to)} " +
                                        "${binding.newDeliveryMoveToSpinner.selectedItem}\n" +
                                        "${binding.newDeliveryComment.text}"
                            }
                        } else if (!binding.newDeliveryIsSalaryCheckBox.isChecked && !binding.newDeliveryMoveTaskSwitch.isChecked) {
                            delivery.ifSalary = 1
                            if (binding.newDeliveryComment.text.toString() == "") {
                                delivery.comment = "${resources.getString(R.string.move_from)} " +
                                        "${binding.newDeliveryMoveFromSpinner.selectedItem} " +
                                        "${resources.getString(R.string.move_to)} " +
                                        "${binding.newDeliveryMoveToSpinner.selectedItem}"
                            } else {
                                delivery.comment = "${resources.getString(R.string.move_from)} " +
                                        "${binding.newDeliveryMoveFromSpinner.selectedItem} " +
                                        "${resources.getString(R.string.move_to)} " +
                                        "${binding.newDeliveryMoveToSpinner.selectedItem}\n" +
                                        "${binding.newDeliveryComment.text}"
                            }
                        } else if (binding.newDeliveryIsSalaryCheckBox.isChecked && binding.newDeliveryMoveTaskSwitch.isChecked) {
                            delivery.ifSalary = 0
                            if (binding.newDeliveryComment.text.toString() == "") {
                                delivery.comment = "${resources.getString(R.string.no_salary)}\n" +
                                        resources.getString(R.string.switch_else)
                            } else {
                                delivery.comment = "${resources.getString(R.string.no_salary)}\n" +
                                        "${resources.getString(R.string.switch_else)}\n" +
                                        "${binding.newDeliveryComment.text}"
                            }
                        } else if (!binding.newDeliveryIsSalaryCheckBox.isChecked && binding.newDeliveryMoveTaskSwitch.isChecked) {
                            delivery.ifSalary = 1
                            if (binding.newDeliveryComment.text.toString() == "") {
                                delivery.comment = resources.getString(R.string.switch_else)
                            } else {
                                delivery.comment =
                                    "${resources.getString(R.string.switch_else)}\n" +
                                            "${binding.newDeliveryComment.text}"
                            }
                        }
                    }
                    WorkType.Expense.i -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = binding.newDeliveryWorkTypeSpinner.selectedItemPosition
                        delivery.comment = binding.newDeliveryComment.text.toString()
                        delivery.cost = binding.newDeliveryCostEditText.text.toString().toInt()
                        delivery.expenseType = binding.newDeliveryExpenseSpinner.selectedItemPosition
                        delivery.commentSimple = binding.newDeliveryComment.text.toString()
                        if (binding.newDeliveryComment.text.toString() == "") {
                            delivery.comment = binding.newDeliveryExpenseSpinner.selectedItem.toString()
                        } else {
                            delivery.comment = "${binding.newDeliveryExpenseSpinner.selectedItem}\n" +
                                    "${binding.newDeliveryComment.text}"
                        }
                    }
                    WorkType.Other.i -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = binding.newDeliveryWorkTypeSpinner.selectedItemPosition
                        delivery.commentSimple = binding.newDeliveryComment.text.toString()
                    }
                    WorkType.Salary.i -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = binding.newDeliveryWorkTypeSpinner.selectedItemPosition
                        delivery.deliveryType = binding.newDeliverySalaryTypeSpinner.selectedItemPosition
                        delivery.commentSimple = binding.newDeliveryComment.text.toString()
                        delivery.comment = binding.newDeliveryComment.text.toString()
                        delivery.cost = binding.newDeliveryCostEditText.text.toString().toInt()
                    }
                }
                saveToBase()
                callBackActivity.fragmentPlace(DeliveryFragment())
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
        binding.newDeliveryMoveTaskSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.newDeliveryMoveTaskSwitch.text = resources.getString(R.string.switch_else)
                binding.newDeliveryMove.visibility = View.GONE
            } else {
                binding.newDeliveryMoveTaskSwitch.text =
                    resources.getString(R.string.switch_between_shops)
                binding.newDeliveryMove.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun layoutSalary() {
        binding.newDeliveryDeliveryType.visibility = View.GONE
        binding.newDeliverySalaryType.visibility = View.VISIBLE
        binding.newDeliveryPayType.visibility = View.GONE
        binding.newDeliveryExpense.visibility = View.GONE
        binding.newDeliveryMoveTask.visibility = View.GONE
        binding.newDeliveryMoveTaskSwitch.isChecked = false
        binding.newDeliveryAddress.visibility = View.GONE
        binding.newDeliveryCost.visibility = View.VISIBLE
        binding.newDeliveryCostCash.visibility = View.GONE
        binding.newDeliveryMove.visibility = View.GONE
        binding.newDeliveryIsSalary.visibility = View.GONE
    }

    private fun layoutOther() {
        binding.newDeliveryDeliveryType.visibility = View.GONE
        binding.newDeliverySalaryType.visibility = View.GONE
        binding.newDeliveryPayType.visibility = View.GONE
        binding.newDeliveryExpense.visibility = View.GONE
        binding.newDeliveryMoveTask.visibility = View.GONE
        binding.newDeliveryMoveTaskSwitch.isChecked = false
        binding.newDeliveryAddress.visibility = View.GONE
        binding.newDeliveryCost.visibility = View.GONE
        binding.newDeliveryCostCash.visibility = View.GONE
        binding.newDeliveryMove.visibility = View.GONE
        binding.newDeliveryIsSalary.visibility = View.GONE
    }

    private fun layoutExpense() {
        binding.newDeliveryDeliveryType.visibility = View.GONE
        binding.newDeliverySalaryType.visibility = View.GONE
        binding.newDeliveryPayType.visibility = View.GONE
        binding.newDeliveryExpense.visibility = View.VISIBLE
        binding.newDeliveryMoveTask.visibility = View.GONE
        binding.newDeliveryMoveTaskSwitch.isChecked = false
        binding.newDeliveryAddress.visibility = View.GONE
        binding.newDeliveryCost.visibility = View.VISIBLE
        binding.newDeliveryCostCash.visibility = View.GONE
        binding.newDeliveryMove.visibility = View.GONE
        binding.newDeliveryIsSalary.visibility = View.GONE
    }

    private fun layoutTask() {
        binding.newDeliveryDeliveryType.visibility = View.VISIBLE
        binding.newDeliverySalaryType.visibility = View.GONE
        binding.newDeliveryPayType.visibility = View.GONE
        binding.newDeliveryExpense.visibility = View.GONE
        binding.newDeliveryMoveTask.visibility = View.VISIBLE
        binding.newDeliveryMoveTaskSwitch.isChecked = false
        binding.newDeliveryAddress.visibility = View.GONE
        binding.newDeliveryCost.visibility = View.GONE
        binding.newDeliveryCostCash.visibility = View.GONE
        binding.newDeliveryMove.visibility = View.VISIBLE
        binding.newDeliveryIsSalary.visibility = View.VISIBLE
    }

    private fun layoutMove() {
        binding.newDeliveryDeliveryType.visibility = View.VISIBLE
        binding.newDeliverySalaryType.visibility = View.GONE
        binding.newDeliveryPayType.visibility = View.GONE
        binding.newDeliveryExpense.visibility = View.GONE
        binding.newDeliveryMoveTask.visibility = View.GONE
        binding.newDeliveryMoveTaskSwitch.isChecked = false
        binding.newDeliveryAddress.visibility = View.GONE
        binding.newDeliveryCost.visibility = View.GONE
        binding.newDeliveryCostCash.visibility = View.GONE
        binding.newDeliveryMove.visibility = View.VISIBLE
        binding.newDeliveryIsSalary.visibility = View.VISIBLE
    }

    private fun layoutDelivery() {
        binding.newDeliveryDeliveryType.visibility = View.VISIBLE
        binding.newDeliverySalaryType.visibility = View.GONE
        binding.newDeliveryPayType.visibility = View.VISIBLE
        binding.newDeliveryExpense.visibility = View.GONE
        binding.newDeliveryMoveTask.visibility = View.GONE
        binding.newDeliveryMoveTaskSwitch.isChecked = false
        binding.newDeliveryAddress.visibility = View.VISIBLE
        binding.newDeliveryCost.visibility = View.VISIBLE
        binding.newDeliveryCostCash.visibility = View.VISIBLE
        binding.newDeliveryMove.visibility = View.GONE
        binding.newDeliveryIsSalary.visibility = View.GONE
    }

    private fun saveToBase() = GlobalScope.launch {
        if (newDelivery) {
            DB.getDao().addDelivery(delivery)
        } else {
            DB.getDao().updateDelivery(delivery)
        }

    }
}
