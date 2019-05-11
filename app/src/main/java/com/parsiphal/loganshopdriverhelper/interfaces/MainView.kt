package com.parsiphal.loganshopdriverhelper.interfaces

import com.arellomobile.mvp.MvpAppCompatFragment

interface MainView: BaseView {
    fun fragmentPlace(fragment: MvpAppCompatFragment)
}