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
}