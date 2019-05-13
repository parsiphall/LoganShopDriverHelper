package com.parsiphal.loganshopdriverhelper

import android.app.Application
import android.arch.persistence.room.Room
import com.parsiphal.loganshopdriverhelper.data.DataBase
import com.parsiphal.loganshopdriverhelper.data.Preferences

const val DB_NAME = "helper_DB"

val prefs: Preferences by lazy {
    MainApp.prefs!!
}

val DB: DataBase by lazy {
    MainApp.mDataBase!!
}

class MainApp : Application() {

    companion object {
        var prefs: Preferences? = null
        var mDataBase: DataBase? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Preferences(applicationContext)
        mDataBase = Room
            .databaseBuilder(applicationContext, DataBase::class.java, DB_NAME)
            .build()
    }
}

//TODO Export