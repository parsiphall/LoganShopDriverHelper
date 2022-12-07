package com.parsiphal.loganshopdriverhelper

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.parsiphal.loganshopdriverhelper.databinding.ActivityMainBinding
import com.parsiphal.loganshopdriverhelper.fragments.*
import com.parsiphal.loganshopdriverhelper.interfaces.MainView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import moxy.MvpAppCompatActivity
import moxy.MvpAppCompatFragment

@ExperimentalCoroutinesApi
class MainActivity : MvpAppCompatActivity(), MainView {

    private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var binding: ActivityMainBinding

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
//        if (mInterstitialAd.isLoaded) {
//            mInterstitialAd.show()
//        }
    }

    private val onNavigationItemMainSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_shift -> {
                    if (prefs.shiftSystem == 0) {
                        fragmentPlace(ShiftFragment())
                    } else {
                        fragmentPlace(ShiftFragmentNew())
                    }
                    binding.settingsFab.visibility = View.VISIBLE
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_delivery -> {
                    fragmentPlace(DeliveryFragment())
                    binding.settingsFab.visibility = View.GONE
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_total -> {
                    fragmentPlace(TotalFragment())
                    binding.settingsFab.visibility = View.GONE
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.navView.setOnNavigationItemSelectedListener(onNavigationItemMainSelectedListener)
        binding.navView.selectedItemId = R.id.navigation_delivery
        binding.settingsFab.setOnClickListener {
            fragmentPlace(MaintananceFragment())
            binding.settingsFab.visibility = View.GONE
        }
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
//        MobileAds.initialize(this) {}
//        prepareAd()
        binding.versionTextView.text = "v${BuildConfig.VERSION_NAME}\nb${BuildConfig.VERSION_CODE}"
        fragmentPlace(DeliveryFragment())
    }

    override fun prepareAd() {
//        mInterstitialAd = InterstitialAd(this)
//        mInterstitialAd.adUnitId = "ca-app-pub-1794452166316673/5993649829"
////        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
//        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }
}
