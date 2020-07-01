package com.parsiphal.loganshopdriverhelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_empty.*

const val DEADLINE = 1596240000
const val DEADLINE_TEST = 1588104600

class EmptyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)
        val time = System.currentTimeMillis() / 1000L
        if (time < DEADLINE) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            somethingWrong.visibility = View.VISIBLE
        }
    }
}
