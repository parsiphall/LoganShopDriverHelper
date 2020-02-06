package com.parsiphal.loganshopdriverhelper

import android.app.Application
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.Room
import androidx.room.migration.Migration
import com.parsiphal.loganshopdriverhelper.data.DataBase
import com.parsiphal.loganshopdriverhelper.data.Preferences

const val DB_NAME = "helper_DB"
const val DB_SHM = "helper_DB-shm"
const val DB_WAL = "helper_DB-wal"

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

        val mig8to9 = object : Migration(8, 9) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Total ADD COLUMN holidayPay INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN carIndex INTEGER DEFAULT -1 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN largusShifts INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN sanderoShifts INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN xrayShifts INTEGER DEFAULT 0 NOT NULL")
            }
        }

        val mig9to10 = object : Migration(9, 10) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Delivery ADD COLUMN moveFrom INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Delivery ADD COLUMN moveTo INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganMoveFromZhukova INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganMoveFromKulturi INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganMoveFromSedova INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganMoveFromHimikov INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganMoveToZhukova INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganMoveToKulturi INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganMoveToSedova INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganMoveToHimikov INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaMoveFromZhukova INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaMoveFromKulturi INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaMoveFromSedova INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaMoveFromHimikov INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaMoveToZhukova INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaMoveToKulturi INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaMoveToSedova INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaMoveToHimikov INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganTaskFromZhukova INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganTaskFromKulturi INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganTaskFromSedova INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganTaskFromHimikov INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganTaskToZhukova INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganTaskToKulturi INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganTaskToSedova INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganTaskToHimikov INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganTaskElse INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaTaskFromZhukova INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaTaskFromKulturi INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaTaskFromSedova INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaTaskFromHimikov INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaTaskToZhukova INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaTaskToKulturi INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaTaskToSedova INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaTaskToHimikov INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaTaskElse INTEGER DEFAULT 0 NOT NULL")
            }
        }

        val mig10to11 = object : Migration(10, 11) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Delivery ADD COLUMN commentSimple TEXT DEFAULT '' NOT NULL")
            }
        }

        val mig11to12 = object : Migration(11,12){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Total ADD COLUMN extraPay INTEGER DEFAULT 0 NOT NULL")
            }
        }

        val mig12to13 = object : Migration(12,13){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Total ADD COLUMN penalty INTEGER DEFAULT 0 NOT NULL")
            }
        }

        val mig13to14 = object : Migration(13,14){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Total ADD COLUMN loganMoveFromPlanernaya INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganMoveToPlanernaya INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaMoveFromPlanernaya INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaMoveToPlanernaya INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganTaskFromPlanernaya INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN loganTaskToPlanernaya INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaTaskFromPlanernaya INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE Total ADD COLUMN vestaTaskToPlanernaya INTEGER DEFAULT 0 NOT NULL")
            }
        }

        prefs = Preferences(applicationContext)
        mDataBase = Room
            .databaseBuilder(applicationContext, DataBase::class.java, DB_NAME)
            .addMigrations(
                mig1to2,
                mig2to3,
                mig3to4,
                mig4to5,
                mig5to6,
                mig6to7,
                mig7to8,
                mig8to9,
                mig9to10,
                mig10to11,
                mig11to12,
                mig12to13,
                mig13to14
            )
            .build()
    }
}