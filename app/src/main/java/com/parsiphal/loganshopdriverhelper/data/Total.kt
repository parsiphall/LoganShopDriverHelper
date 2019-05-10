package com.parsiphal.loganshopdriverhelper.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Total(
    @PrimaryKey(autoGenerate = true) @ColumnInfo var id: Long = 0,
    @ColumnInfo var carModel: String = "",
    @ColumnInfo var date: String = "",
    @ColumnInfo var morningODO: Int = 0,
    @ColumnInfo var eveningODO: Int = 0,
    @ColumnInfo var morningFuel: Int = 0,
    @ColumnInfo var eveningFuel: Int = 0,
    @ColumnInfo var totalMoney: Int = 0,
    @ColumnInfo var loganDeliveryValue: Int = 0,
    @ColumnInfo var loganMoney: Int = 0,
    @ColumnInfo var loganCash: Int = 0,
    @ColumnInfo var loganCard: Int = 0,
    @ColumnInfo var vestaDeliveryValue: Int = 0,
    @ColumnInfo var vestaMoney: Int = 0,
    @ColumnInfo var vestaCash: Int = 0,
    @ColumnInfo var vestaCard: Int = 0,
    @ColumnInfo var expenses: Int = 0,
    @ColumnInfo var salary: Int = 0
)