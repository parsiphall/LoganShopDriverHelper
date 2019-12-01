package com.parsiphal.loganshopdriverhelper.interfaces

import android.os.Bundle
import moxy.MvpAppCompatFragment

interface MainView: BaseView {
    fun fragmentPlace(fragment: MvpAppCompatFragment)
    fun fragmentPlaceWithArgs(fragment: MvpAppCompatFragment, args: Bundle)
    fun showAd()
    fun prepareAd()
}