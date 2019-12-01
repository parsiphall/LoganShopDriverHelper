package com.parsiphal.loganshopdriverhelper

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.parsiphal.loganshopdriverhelper.fragments.DeliveryFragment
import com.parsiphal.loganshopdriverhelper.fragments.ShiftFragment
import com.parsiphal.loganshopdriverhelper.fragments.TotalFragment
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.MvpAppCompatFragment

class MainActivity : MvpAppCompatActivity(), MainView {

    private lateinit var mInterstitialAd: InterstitialAd

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

    override fun showAd() {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        }
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
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    1
                )
            }
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    1
                )
            }
        }
        MobileAds.initialize(this) {}
        prepareAd()
        version_TextView.text = "v${BuildConfig.VERSION_NAME}\nb${BuildConfig.VERSION_CODE}"
        fragmentPlace(DeliveryFragment())
    }

    override fun prepareAd() {
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-1794452166316673/5993649829"
//        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }
}
