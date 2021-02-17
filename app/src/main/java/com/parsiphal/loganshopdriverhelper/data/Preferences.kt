package com.parsiphal.loganshopdriverhelper.data

import android.content.Context

const val PREFS_FILENAME = "com.parsiphal.loganshopdriverhelper.prefs"
const val FAMILY = "family"
const val BONUS = "bonus"
const val DATE = "date"
const val REGION = "region"
const val REGION_POSITION = "region_position"
const val CAR = "car"
const val CAR_POSITION = "car_position"
const val MORNING_ODO = "morning_odo"
const val MORNING_FUEL = "morning_fuel"
const val EVENING_ODO = "evening_odo"
const val EVENING_FUEL = "evening_fuel"
const val LAST_ODO_LARGUS = "last_odo_largus"
const val LAST_ODO_SANDERO = "last_odo_sandero"
const val LAST_ODO_XRAY = "last_odo_xray"
const val LAST_ODO_LARGUS_NEW = "last_odo_largus_new"
const val LAST_FUEL_LARGUS = "last_fuel_largus"
const val LAST_FUEL_SANDERO = "last_fuel_sandero"
const val LAST_FUEL_XRAY = "last_fuel_xray"
const val LAST_FUEL_LARGUS_NEW = "last_fuel_largus_new"
const val ADD_SYSTEM = "add_system"
const val STATUS = "status"
const val DELIVERY_VIEW = "delivery_view"
const val SHIFT_SYSTEM = "shift_system"
const val XRAY_DB = "xray_db"


class Preferences(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    var family: String?
        get() = prefs.getString(FAMILY, "")
        set(value) = prefs.edit().putString(FAMILY, value).apply()

    var bonus: Boolean
        get() = prefs.getBoolean(BONUS, false)
        set(value) = prefs.edit().putBoolean(BONUS, value).apply()

    var date: String?
        get() = prefs.getString(DATE, "")
        set(value) = prefs.edit().putString(DATE, value).apply()

    var region: String?
        get() = prefs.getString(REGION, "")
        set(value) = prefs.edit().putString(REGION, value).apply()

    var regionPosition: Int?
        get() = prefs.getInt(REGION_POSITION, 0)
        set(value) = prefs.edit().putInt(REGION_POSITION, value!!).apply()

    var car: String?
        get() = prefs.getString(CAR, "")
        set(value) = prefs.edit().putString(CAR, value).apply()

    var carPosition: Int?
        get() = prefs.getInt(CAR_POSITION, 0)
        set(value) = prefs.edit().putInt(CAR_POSITION, value!!).apply()

    var morningODO: Int?
        get() = prefs.getInt(MORNING_ODO, 0)
        set(value) = prefs.edit().putInt(MORNING_ODO, value!!).apply()

    var morningFuel: Int?
        get() = prefs.getInt(MORNING_FUEL, 0)
        set(value) = prefs.edit().putInt(MORNING_FUEL, value!!).apply()

    var eveningODO: Int?
        get() = prefs.getInt(EVENING_ODO, 0)
        set(value) = prefs.edit().putInt(EVENING_ODO, value!!).apply()

    var eveningFuel: Int?
        get() = prefs.getInt(EVENING_FUEL, 0)
        set(value) = prefs.edit().putInt(EVENING_FUEL, value!!).apply()

    var lastOdoLargus: Int?
        get() = prefs.getInt(LAST_ODO_LARGUS, 0)
        set(value) = prefs.edit().putInt(LAST_ODO_LARGUS, value!!).apply()

    var lastOdoSandero: Int?
        get() = prefs.getInt(LAST_ODO_SANDERO, 0)
        set(value) = prefs.edit().putInt(LAST_ODO_SANDERO, value!!).apply()

    var lastOdoXRay: Int?
        get() = prefs.getInt(LAST_ODO_XRAY, 0)
        set(value) = prefs.edit().putInt(LAST_ODO_XRAY, value!!).apply()

    var lastOdoLargusNew: Int?
        get() = prefs.getInt(LAST_ODO_LARGUS_NEW, 0)
        set(value) = prefs.edit().putInt(LAST_ODO_LARGUS_NEW, value!!).apply()

    var lastFuelLargus: Int?
        get() = prefs.getInt(LAST_FUEL_LARGUS, 0)
        set(value) = prefs.edit().putInt(LAST_FUEL_LARGUS, value!!).apply()

    var lastFuelSandero: Int?
        get() = prefs.getInt(LAST_FUEL_SANDERO, 0)
        set(value) = prefs.edit().putInt(LAST_FUEL_SANDERO, value!!).apply()

    var lastFuelXRay: Int?
        get() = prefs.getInt(LAST_FUEL_XRAY, 0)
        set(value) = prefs.edit().putInt(LAST_FUEL_XRAY, value!!).apply()

    var lastFuelLargusNew: Int?
        get() = prefs.getInt(LAST_FUEL_LARGUS_NEW, 0)
        set(value) = prefs.edit().putInt(LAST_FUEL_LARGUS_NEW, value!!).apply()

    var addSystem: Int?
        get() = prefs.getInt(ADD_SYSTEM, 1)
        set(value) = prefs.edit().putInt(ADD_SYSTEM, value!!).apply()

    var status: Int?
        get() = prefs.getInt(STATUS, 0)
        set(value) = prefs.edit().putInt(STATUS, value!!).apply()

    var deliveryView: Int?
        get() = prefs.getInt(DELIVERY_VIEW, 0)
        set(value) = prefs.edit().putInt(DELIVERY_VIEW, value!!).apply()

    var shiftSystem: Int?
        get() = prefs.getInt(SHIFT_SYSTEM, 1)
        set(value) = prefs.edit().putInt(SHIFT_SYSTEM, value!!).apply()

    var xRayDB: Boolean
        get() = prefs.getBoolean(XRAY_DB, false)
        set(value) = prefs.edit().putBoolean(XRAY_DB, value).apply()
}