package com.parsiphal.loganshopdriverhelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.parsiphal.loganshopdriverhelper.data.Total
import com.parsiphal.loganshopdriverhelper.databinding.ActivityEmptyBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

const val DEADLINE = 1671364628
const val DEADLINE_TEST = 1588104600

class EmptyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmptyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmptyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val time = System.currentTimeMillis() / 1000L
        if (!prefs.xRayDB) {
            try {
                refactorDB()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        justStart()
        //startWithIf(time)
    }

    private fun refactorDB() {
        prefs.xRayDB = true
        var totals: List<Total> = ArrayList()
        val search = "02/2021"
        val data = GlobalScope.async {
            totals = DB.getDao().getTotalsByMonth("%$search%")
        }
        MainScope().launch {
            data.await()
            for (position in totals) {
                if (position.carIndex == 2) {
                    position.carIndex = -2
                    GlobalScope.launch {
                        DB.getDao().updateTotal(position)
                    }
                }
            }
        }
    }

    private fun justStart() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun startWithIf(time: Long) {
        if (time < DEADLINE) {
            justStart()
        } else {
            binding.somethingWrong.visibility = View.VISIBLE
        }
    }
}
