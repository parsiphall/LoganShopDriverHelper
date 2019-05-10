package com.parsiphal.loganshopdriverhelper.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Shift(
    @PrimaryKey(autoGenerate = true) @ColumnInfo var id: Long = 0,
    @ColumnInfo var shiftDate: String = "",
    @ColumnInfo var carModel: String = "",
    @ColumnInfo var morningODO: Int = 0,
    @ColumnInfo var morningFuel: Int = 0,
    @ColumnInfo var eveningODO: Int = 0,
    @ColumnInfo var eveningFuel: Int = 0
)