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
import com.parsiphal.loganshopdriverhelper.databinding.FragmentNewDeliveryAddBinding
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import com.parsiphal.loganshopdriverhelper.prefs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import moxy.MvpAppCompatFragment


@ExperimentalCoroutinesApi
class NewDeliveryAddFragment : MvpAppCompatFragment() {

    private lateinit var delivery: Delivery
    private lateinit var callBackActivity: MainView
    private var isBetweenShops = true
    private var _binding: FragmentNewDeliveryAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delivery = Delivery()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewDeliveryAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBackActivity = context as MainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        delivery.deliveryDate = prefs.date!!
        binding.newDeliveryAddWorkType.visibility = View.VISIBLE
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveToBase() = GlobalScope.launch {
        DB.getDao().addDelivery(delivery)
    }

    private fun workTypeListeners() {
        binding.newDeliveryAddWorkTypeDeliveryButton.setOnClickListener {
            delivery.workType = WorkType.Delivery.i
            binding.newDeliveryAddWorkType.visibility = View.GONE
            binding.newDeliveryAddDeliveryType.visibility = View.VISIBLE
        }
        binding.newDeliveryAddWorkTypeMoveButton.setOnClickListener {
            delivery.workType = WorkType.Move.i
            binding.newDeliveryAddWorkType.visibility = View.GONE
            binding.newDeliveryAddDeliveryType.visibility = View.VISIBLE
        }
        binding.newDeliveryAddWorkTypeTaskButton.setOnClickListener {
            delivery.workType = WorkType.Task.i
            binding.newDeliveryAddWorkType.visibility = View.GONE
            binding.newDeliveryAddDeliveryType.visibility = View.VISIBLE
        }
        binding.newDeliveryAddWorkTypeExpenseButton.setOnClickListener {
            delivery.workType = WorkType.Expense.i
            binding.newDeliveryAddWorkType.visibility = View.GONE
            binding.newDeliveryAddExpense.visibility = View.VISIBLE
        }
        binding.newDeliveryAddWorkTypePayButton.setOnClickListener {
            delivery.workType = WorkType.Salary.i
            binding.newDeliveryAddWorkType.visibility = View.GONE
            binding.newDeliveryAddPay.visibility = View.VISIBLE
        }
        binding.newDeliveryAddWorkTypeOtherButton.setOnClickListener {
            delivery.workType = WorkType.Other.i
            binding.newDeliveryAddWorkType.visibility = View.GONE
            binding.newDeliveryAddOther.visibility = View.VISIBLE
        }
    }

    private fun deliveryTypeListeners() {
        binding.newDeliveryAddDeliveryTypeLogan.setOnClickListener {
            delivery.deliveryType = 0
            binding.newDeliveryAddDeliveryType.visibility = View.GONE
            when (delivery.workType) {
                WorkType.Delivery.i -> {
                    binding.newDeliveryAddPayType.visibility = View.VISIBLE
                }
                WorkType.Move.i -> {
                    binding.newDeliveryAddMoveFrom.visibility = View.VISIBLE
                }
                else -> {
                    binding.newDeliveryAddTaskType.visibility = View.VISIBLE
                }
            }
        }
        binding.newDeliveryAddDeliveryTypeVesta.setOnClickListener {
            delivery.deliveryType = 1
            binding.newDeliveryAddDeliveryType.visibility = View.GONE
            when (delivery.workType) {
                WorkType.Delivery.i -> {
                    binding.newDeliveryAddPayType.visibility = View.VISIBLE
                }
                WorkType.Move.i -> {
                    binding.newDeliveryAddMoveFrom.visibility = View.VISIBLE
                    binding.newDeliveryAddMoveFromHimikov.visibility = View.GONE
                    binding.newDeliveryAddMoveToHimikov.visibility = View.GONE
                }
                else -> {
                    binding.newDeliveryAddTaskType.visibility = View.VISIBLE
                    binding.newDeliveryAddMoveFromHimikov.visibility = View.GONE
                    binding.newDeliveryAddMoveToHimikov.visibility = View.GONE
                }
            }
        }
    }

    private fun payTypeListeners() {
        binding.newDeliveryAddPayTypeCash.setOnClickListener {
            delivery.payType = PayType.Cash.i
            binding.newDeliveryAddPayType.visibility = View.GONE
            binding.newDeliveryAddAddress.visibility = View.VISIBLE
        }
        binding.newDeliveryAddPayTypeCard.setOnClickListener {
            delivery.payType = PayType.Card.i
            binding.newDeliveryAddPayType.visibility = View.GONE
            binding.newDeliveryAddAddress.visibility = View.VISIBLE
        }
    }

    private fun payingListeners() {
        binding.newDeliveryAddPayingNext.setOnClickListener {
            try {
                delivery.cost = binding.newDeliveryAddPayingCost.text.toString().toInt()
                if (binding.newDeliveryAddPayingMoneyTake.text.toString() == "") {
                    delivery.expense = 0
                } else {
                    delivery.expense =
                        (binding.newDeliveryAddPayingMoneyTake.text.toString()
                            .toInt() - binding.newDeliveryAddPayingCost.text.toString().toInt())
                }
                save()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view!!, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun addressListeners() {
        binding.newDeliveryAddAddressSave.setOnClickListener {
            if (binding.newDeliveryAddAddressAddress.text.toString() != "") {
                delivery.address = binding.newDeliveryAddAddressAddress.text.toString()
                delivery.comment = binding.newDeliveryAddAddressComment.text.toString()
                delivery.commentSimple = binding.newDeliveryAddAddressComment.text.toString()
                binding.newDeliveryAddAddress.visibility = View.GONE
                binding.newDeliveryAddPaying.visibility = View.VISIBLE
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
        binding.newDeliveryAddMoveFromZhukova.setOnClickListener {
            delivery.moveFrom = Shops.Zhukova.i
            binding.newDeliveryAddMoveFrom.visibility = View.GONE
            binding.newDeliveryAddMoveTo.visibility = View.VISIBLE
        }
        binding.newDeliveryAddMoveFromKulturi.setOnClickListener {
            delivery.moveFrom = Shops.Kulturi.i
            binding.newDeliveryAddMoveFrom.visibility = View.GONE
            binding.newDeliveryAddMoveTo.visibility = View.VISIBLE
        }
        binding.newDeliveryAddMoveFromSedova.setOnClickListener {
            delivery.moveFrom = Shops.Sedova.i
            binding.newDeliveryAddMoveFrom.visibility = View.GONE
            binding.newDeliveryAddMoveTo.visibility = View.VISIBLE
        }
        binding.newDeliveryAddMoveFromHimikov.setOnClickListener {
            delivery.moveFrom = Shops.Himikov.i
            binding.newDeliveryAddMoveFrom.visibility = View.GONE
            binding.newDeliveryAddMoveTo.visibility = View.VISIBLE
        }
        binding.newDeliveryAddMoveFromPlanernaya.setOnClickListener {
            delivery.moveFrom = Shops.Planernaya.i
            binding.newDeliveryAddMoveFrom.visibility = View.GONE
            binding.newDeliveryAddMoveTo.visibility = View.VISIBLE
        }
        binding.newDeliveryAddMoveFromVeteranov.setOnClickListener {
            delivery.moveFrom = Shops.Veteranov.i
            binding.newDeliveryAddMoveFrom.visibility = View.GONE
            binding.newDeliveryAddMoveTo.visibility = View.VISIBLE
        }
    }

    private fun moveToListeners() {
        binding.newDeliveryAddMoveToZhukova.setOnClickListener {
            delivery.moveTo = Shops.Zhukova.i
            binding.newDeliveryAddMoveTo.visibility = View.GONE
            binding.newDeliveryAddCommentAndPay.visibility = View.VISIBLE
        }
        binding.newDeliveryAddMoveToKulturi.setOnClickListener {
            delivery.moveTo = Shops.Kulturi.i
            binding.newDeliveryAddMoveTo.visibility = View.GONE
            binding.newDeliveryAddCommentAndPay.visibility = View.VISIBLE
        }
        binding.newDeliveryAddMoveToSedova.setOnClickListener {
            delivery.moveTo = Shops.Sedova.i
            binding.newDeliveryAddMoveTo.visibility = View.GONE
            binding.newDeliveryAddCommentAndPay.visibility = View.VISIBLE
        }
        binding.newDeliveryAddMoveToHimikov.setOnClickListener {
            delivery.moveTo = Shops.Himikov.i
            binding.newDeliveryAddMoveTo.visibility = View.GONE
            binding.newDeliveryAddCommentAndPay.visibility = View.VISIBLE
        }
        binding.newDeliveryAddMoveToPlanernaya.setOnClickListener {
            delivery.moveTo = Shops.Planernaya.i
            binding.newDeliveryAddMoveTo.visibility = View.GONE
            binding.newDeliveryAddCommentAndPay.visibility = View.VISIBLE
        }
        binding.newDeliveryAddMoveToVeteranov.setOnClickListener {
            delivery.moveTo = Shops.Veteranov.i
            binding.newDeliveryAddMoveTo.visibility = View.GONE
            binding.newDeliveryAddCommentAndPay.visibility = View.VISIBLE
        }
    }

    private fun commentAndPayListeners() {
        binding.newDeliveryAddCommentAndPayYes.setOnClickListener {
            delivery.ifSalary = 1
            delivery.commentSimple = binding.newDeliveryAddCommentAndPayComment.text.toString()
            if (isBetweenShops) {
                if (binding.newDeliveryAddCommentAndPayComment.text.toString() == "") {
                    delivery.comment = "${resources.getString(R.string.move_from)} " +
                            "${resources.getStringArray(R.array.shops)[delivery.moveFrom]} " +
                            "${resources.getString(R.string.move_to)} " +
                            "${resources.getStringArray(R.array.shops)[delivery.moveTo]}"
                } else {
                    delivery.comment = "${resources.getString(R.string.move_from)} " +
                            "${resources.getStringArray(R.array.shops)[delivery.moveFrom]} " +
                            "${resources.getString(R.string.move_to)} " +
                            "${resources.getStringArray(R.array.shops)[delivery.moveTo]}\n" +
                            "${binding.newDeliveryAddCommentAndPayComment.text}"
                }
            } else {
                if (binding.newDeliveryAddCommentAndPayComment.text.toString() == "") {
                    delivery.comment = resources.getString(R.string.switch_else)
                } else {
                    delivery.comment = "${resources.getString(R.string.switch_else)} \n" +
                            "${binding.newDeliveryAddCommentAndPayComment.text}"
                }
            }
            save()
        }
        binding.newDeliveryAddCommentAndPayNo.setOnClickListener {
            delivery.ifSalary = 0
            delivery.commentSimple = binding.newDeliveryAddCommentAndPayComment.text.toString()
            if (isBetweenShops) {
                if (binding.newDeliveryAddCommentAndPayComment.text.toString() == "") {
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
                            "${binding.newDeliveryAddCommentAndPayComment.text}"
                }
            } else {
                if (binding.newDeliveryAddCommentAndPayComment.text.toString() == "") {
                    delivery.comment = "${resources.getString(R.string.no_salary)}\n" +
                            "${resources.getString(R.string.switch_else)}"
                } else {
                    delivery.comment = "${resources.getString(R.string.no_salary)}\n" +
                            "${resources.getString(R.string.switch_else)} \n" +
                            "${binding.newDeliveryAddCommentAndPayComment.text}"
                }
            }
            save()
        }
    }

    private fun taskTypeListeners() {
        binding.newDeliveryAddMoveTaskTypeYes.setOnClickListener {
            isBetweenShops = true
            binding.newDeliveryAddTaskType.visibility = View.GONE
            binding.newDeliveryAddMoveFrom.visibility = View.VISIBLE
        }
        binding.newDeliveryAddMoveTaskTypeNo.setOnClickListener {
            isBetweenShops = false
            delivery.moveFrom = Shops.Other.i
            delivery.moveTo = Shops.Other.i
            binding.newDeliveryAddTaskType.visibility = View.GONE
            binding.newDeliveryAddCommentAndPay.visibility = View.VISIBLE
        }
    }

    private fun expenseListeners() {
        binding.newDeliveryAddExpenseFuel.setOnClickListener {
            try {
                delivery.commentSimple = binding.newDeliveryAddExpenseComment.text.toString()
                delivery.cost = binding.newDeliveryAddExpenseCost.text.toString().toInt()
                delivery.expenseType = Expenses.Fuel.i
                if (binding.newDeliveryAddExpenseComment.text.toString() == "") {
                    delivery.comment = resources.getStringArray(R.array.expenses)[0]
                } else {
                    delivery.comment = "${resources.getStringArray(R.array.expenses)[0]} \n" +
                            "${binding.newDeliveryAddExpenseComment.text}"
                }
                save()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view!!, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
        binding.newDeliveryAddExpenseWash.setOnClickListener {
            try {
                delivery.commentSimple = binding.newDeliveryAddExpenseComment.text.toString()
                delivery.cost = binding.newDeliveryAddExpenseCost.text.toString().toInt()
                delivery.expenseType = Expenses.Wash.i
                if (binding.newDeliveryAddExpenseComment.text.toString() == "") {
                    delivery.comment = resources.getStringArray(R.array.expenses)[1]
                } else {
                    delivery.comment = "${resources.getStringArray(R.array.expenses)[1]} \n" +
                            "${binding.newDeliveryAddExpenseComment.text}"
                }
                save()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view!!, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
        binding.newDeliveryAddExpenseOther.setOnClickListener {
            try {
                delivery.commentSimple = binding.newDeliveryAddExpenseComment.text.toString()
                delivery.cost = binding.newDeliveryAddExpenseCost.text.toString().toInt()
                delivery.expenseType = Expenses.Other.i
                if (binding.newDeliveryAddExpenseComment.text.toString() == "") {
                    delivery.comment = resources.getStringArray(R.array.expenses)[2]
                } else {
                    delivery.comment = "${resources.getStringArray(R.array.expenses)[2]} \n" +
                            "${binding.newDeliveryAddExpenseComment.text}"
                }
                save()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view!!, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun payListeners() {
        binding.newDeliveryAddPayPrePay.setOnClickListener {
            try {
                delivery.deliveryType = SalaryType.Prepay.i
                delivery.cost = binding.newDeliveryAddPayCost.text.toString().toInt()
                delivery.comment = binding.newDeliveryAddPayComment.text.toString()
                delivery.commentSimple = binding.newDeliveryAddPayComment.text.toString()
                save()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view!!, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
        binding.newDeliveryAddPayHolidayPay.setOnClickListener {
            try {
                delivery.deliveryType = SalaryType.Holiday.i
                delivery.cost = binding.newDeliveryAddPayCost.text.toString().toInt()
                delivery.comment = binding.newDeliveryAddPayComment.text.toString()
                delivery.commentSimple = binding.newDeliveryAddPayComment.text.toString()
                save()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view!!, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
        binding.newDeliveryAddPayExtraPay.setOnClickListener {
            try {
                delivery.deliveryType = SalaryType.Extra.i
                delivery.cost = binding.newDeliveryAddPayCost.text.toString().toInt()
                delivery.comment = binding.newDeliveryAddPayComment.text.toString()
                delivery.commentSimple = binding.newDeliveryAddPayComment.text.toString()
                save()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view!!, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
        binding.newDeliveryAddPayPenalty.setOnClickListener {
            try {
                delivery.deliveryType = SalaryType.Penalty.i
                delivery.cost = binding.newDeliveryAddPayCost.text.toString().toInt()
                delivery.comment = binding.newDeliveryAddPayComment.text.toString()
                delivery.commentSimple = binding.newDeliveryAddPayComment.text.toString()
                save()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view!!, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
        binding.newDeliveryAddPayQualityPay.setOnClickListener {
            try {
                delivery.deliveryType = SalaryType.Quality.i
                delivery.cost = binding.newDeliveryAddPayCost.text.toString().toInt()
                delivery.comment = binding.newDeliveryAddPayComment.text.toString()
                delivery.commentSimple = binding.newDeliveryAddPayComment.text.toString()
                save()
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(view!!, getString(R.string.wrongData), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun otherListeners() {
        binding.newDeliveryAddOtherSave.setOnClickListener {
            delivery.comment = binding.newDeliveryAddOtherComment.text.toString()
            delivery.commentSimple = binding.newDeliveryAddOtherComment.text.toString()
            save()
        }
    }
}
