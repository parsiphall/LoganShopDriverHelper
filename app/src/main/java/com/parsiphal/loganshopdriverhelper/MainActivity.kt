package com.parsiphal.loganshopdriverhelper

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.parsiphal.loganshopdriverhelper.fragments.DeliveryFragment
import com.parsiphal.loganshopdriverhelper.fragments.ShiftFragment
import com.parsiphal.loganshopdriverhelper.fragments.TotalFragment

class MainActivity : AppCompatActivity() {


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                fragmentPlace(ShiftFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                fragmentPlace(DeliveryFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                fragmentPlace(TotalFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navView.selectedItemId = R.id.navigation_dashboard
        fragmentPlace(DeliveryFragment())
    }

    private fun fragmentPlace(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
