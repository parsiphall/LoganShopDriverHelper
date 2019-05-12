package com.parsiphal.loganshopdriverhelper.interfaces

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatFragment

interface MainView: BaseView {
    fun fragmentPlace(fragment: MvpAppCompatFragment)
    fun fragmentPlaceWithArgs(fragment: MvpAppCompatFragment, args: Bundle)
}