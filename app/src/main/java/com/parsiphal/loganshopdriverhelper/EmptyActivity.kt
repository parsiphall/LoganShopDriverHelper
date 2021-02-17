package com.parsiphal.loganshopdriverhelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.parsiphal.loganshopdriverhelper.data.Total
import kotlinx.android.synthetic.main.activity_empty.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

const val DEADLINE = 1640984399
const val DEADLINE_TEST = 1588104600

class EmptyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)
        val time = System.currentTimeMillis() / 1000L
        if (!prefs.xRayDB) {
            try {
                refactorDB()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (time < DEADLINE) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            somethingWrong.visibility = View.VISIBLE
        }
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
}
