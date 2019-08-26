package com.parsiphal.loganshopdriverhelper

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentTransaction
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.parsiphal.loganshopdriverhelper.fragments.DeliveryFragment
import com.parsiphal.loganshopdriverhelper.fragments.MaintananceFragment
import com.parsiphal.loganshopdriverhelper.fragments.ShiftFragment
import com.parsiphal.loganshopdriverhelper.fragments.TotalFragment
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity(), MainView {


    override fun fragmentPlace(fragment: MvpAppCompatFragment) {
        supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun fragmentPlaceWithArgs(fragment: MvpAppCompatFragment, args: Bundle) {
        fragment.arguments = args
        supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private val onNavigationItemMainSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_shift -> {
                    fragmentPlace(ShiftFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_delivery -> {
                    fragmentPlace(DeliveryFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_total -> {
                    fragmentPlace(TotalFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nav_view.setOnNavigationItemSelectedListener(onNavigationItemMainSelectedListener)
        nav_view.selectedItemId = R.id.navigation_delivery
//        if (prefs.launchCount == 0) {
//            fragmentPlace(MaintananceFragment())
//            prefs.launchCount = 1
//        } else {
//            fragmentPlace(DeliveryFragment())
//        }
         if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                    1
                )
            }
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    1
                )
            }
        }
        fragmentPlace(DeliveryFragment())
    }
}
