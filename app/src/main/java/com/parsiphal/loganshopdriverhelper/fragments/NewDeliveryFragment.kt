package com.parsiphal.loganshopdriverhelper.fragments

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
import com.parsiphal.loganshopdriverhelper.prefs
import kotlinx.android.synthetic.main.fragment_new_delivery.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewDeliveryFragment : MvpAppCompatFragment() {

    private lateinit var delivery: Delivery

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
                        newDelivery_pay_type.visibility = View.VISIBLE
                        newDelivery_address.visibility = View.VISIBLE
                        newDelivery_cost.visibility = View.VISIBLE
                    }
                    1, 3 -> {
                        newDelivery_delivery_type.visibility = View.GONE
                        newDelivery_pay_type.visibility = View.GONE
                        newDelivery_address.visibility = View.GONE
                        newDelivery_cost.visibility = View.GONE
                    }
                    2 -> {
                        newDelivery_delivery_type.visibility = View.GONE
                        newDelivery_pay_type.visibility = View.GONE
                        newDelivery_address.visibility = View.GONE
                        newDelivery_cost.visibility = View.VISIBLE
                    }
                }
            }
        })
        newDelivery_write.setOnClickListener {
            try {
                when {
                    newDelivery_work_type_spinner.selectedItemPosition == 0 -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = newDelivery_work_type_spinner.selectedItemPosition
                        delivery.deliveryType = newDelivery_delivery_type_spinner.selectedItemPosition
                        delivery.payType = newDelivery_pay_type_spinner.selectedItemPosition
                        delivery.address = newDelivery_address.text.toString()
                        delivery.cost = newDelivery_cost_editText.text.toString().toInt()
                        delivery.comment = newDelivery_comment.text.toString()
                    }
                    newDelivery_work_type_spinner.selectedItemPosition == 2 -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = newDelivery_work_type_spinner.selectedItemPosition
                        delivery.comment = newDelivery_comment.text.toString()
                        delivery.cost = newDelivery_cost_editText.text.toString().toInt()
                    }
                    else -> {
                        delivery.deliveryDate = prefs.date!!
                        delivery.workType = newDelivery_work_type_spinner.selectedItemPosition
                        delivery.comment = newDelivery_comment.text.toString()
                    }
                }
                saveToBase()
                activity?.onBackPressed()
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
