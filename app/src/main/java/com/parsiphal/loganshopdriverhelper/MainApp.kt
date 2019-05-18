package com.parsiphal.loganshopdriverhelper

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.migration.Migration
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

        val mig1to2 = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Total ADD COLUMN expensesString TEXT DEFAULT '' NOT NULL")
            }
        }

        prefs = Preferences(applicationContext)
        mDataBase = Room
            .databaseBuilder(applicationContext, DataBase::class.java, DB_NAME)
            .addMigrations(mig1to2)
            .build()
    }
}

//TODO Export