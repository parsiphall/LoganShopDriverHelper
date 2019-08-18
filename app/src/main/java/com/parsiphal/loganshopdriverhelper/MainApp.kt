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

        val mig1to2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Total ADD COLUMN expensesString TEXT DEFAULT '' NOT NULL")
            }
        }

        val mig2to3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Total ADD COLUMN deltaODO INTEGER DEFAULT 0 NOT NULL")
            }

        }

        val mig3to4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Total ADD COLUMN totalMove INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN totalTask INTEGER DEFAULT 0 NOT NULL")
            }
        }

        val mig4to5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Total ADD COLUMN loganMove INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganTask INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaMove INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaTask INTEGER DEFAULT 0 NOT NULL")
            }
        }

        val mig5to6 = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Delivery ADD COLUMN expenseType INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN expensesFuel INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN expensesWash INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN expensesOther INTEGER DEFAULT 0 NOT NULL")
            }
        }

        val mig6to7 = object : Migration(6, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Delivery ADD COLUMN ifSalary INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN movesWithSalary INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN tasksWithSalary INTEGER DEFAULT 0 NOT NULL")
            }
        }

        val mig7to8 = object : Migration(7, 8) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Total ADD COLUMN prepay INTEGER DEFAULT 0 NOT NULL")
            }
        }

        prefs = Preferences(applicationContext)
        mDataBase = Room
            .databaseBuilder(applicationContext, DataBase::class.java, DB_NAME)
            .addMigrations(mig1to2, mig2to3, mig3to4, mig4to5, mig5to6, mig6to7, mig7to8)
            .build()
    }
}

//TODO Export

//TODO Перевести уровни топлива на spinner
//TODO Сохранение топлива и пробега по авто
//TODO Переделать карточку на фрагменте доставок. Сделать универсальную.