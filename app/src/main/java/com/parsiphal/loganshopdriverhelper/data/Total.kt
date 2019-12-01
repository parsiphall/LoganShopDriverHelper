package com.parsiphal.loganshopdriverhelper.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Total(
    @PrimaryKey(autoGenerate = true) @ColumnInfo var id: Long = 0,
    @ColumnInfo var dayOrMonth: Int = 0,
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
    @ColumnInfo var salary: Int = 0,
    @ColumnInfo var totalCash: Int = 0,
    @ColumnInfo var totalCard: Int = 0,
    @ColumnInfo var totalShifts: Int = 0,
    @ColumnInfo var totalDeliveries: Int = 0,
    @ColumnInfo var expensesString: String = "0",
    @ColumnInfo var deltaODO: Int = 0,
    @ColumnInfo var totalMove: Int = 0,
    @ColumnInfo var totalTask: Int = 0,
    @ColumnInfo var loganMove: Int = 0,
    @ColumnInfo var loganTask: Int = 0,
    @ColumnInfo var vestaMove: Int = 0,
    @ColumnInfo var vestaTask: Int = 0,
    @ColumnInfo var expensesFuel: Int = 0,
    @ColumnInfo var expensesWash: Int = 0,
    @ColumnInfo var expensesOther: Int = 0,
    @ColumnInfo var movesWithSalary: Int = 0,
    @ColumnInfo var tasksWithSalary: Int = 0,
    @ColumnInfo var prepay: Int = 0,
    @ColumnInfo var holidayPay: Int = 0,
    @ColumnInfo var carIndex: Int = -1,
    @ColumnInfo var largusShifts: Int = 0,
    @ColumnInfo var sanderoShifts: Int = 0,
    @ColumnInfo var xrayShifts: Int = 0,
    @ColumnInfo var loganMoveFromZhukova: Int = 0,
    @ColumnInfo var loganMoveFromKulturi: Int = 0,
    @ColumnInfo var loganMoveFromSedova: Int = 0,
    @ColumnInfo var loganMoveFromHimikov: Int = 0,
    @ColumnInfo var loganMoveToZhukova: Int = 0,
    @ColumnInfo var loganMoveToKulturi: Int = 0,
    @ColumnInfo var loganMoveToSedova: Int = 0,
    @ColumnInfo var loganMoveToHimikov: Int = 0,
    @ColumnInfo var vestaMoveFromZhukova: Int = 0,
    @ColumnInfo var vestaMoveFromKulturi: Int = 0,
    @ColumnInfo var vestaMoveFromSedova: Int = 0,
    @ColumnInfo var vestaMoveFromHimikov: Int = 0,
    @ColumnInfo var vestaMoveToZhukova: Int = 0,
    @ColumnInfo var vestaMoveToKulturi: Int = 0,
    @ColumnInfo var vestaMoveToSedova: Int = 0,
    @ColumnInfo var vestaMoveToHimikov: Int = 0,
    @ColumnInfo var loganTaskFromZhukova: Int = 0,
    @ColumnInfo var loganTaskFromKulturi: Int = 0,
    @ColumnInfo var loganTaskFromSedova: Int = 0,
    @ColumnInfo var loganTaskFromHimikov: Int = 0,
    @ColumnInfo var loganTaskToZhukova: Int = 0,
    @ColumnInfo var loganTaskToKulturi: Int = 0,
    @ColumnInfo var loganTaskToSedova: Int = 0,
    @ColumnInfo var loganTaskToHimikov: Int = 0,
    @ColumnInfo var loganTaskElse: Int = 0,
    @ColumnInfo var vestaTaskFromZhukova: Int = 0,
    @ColumnInfo var vestaTaskFromKulturi: Int = 0,
    @ColumnInfo var vestaTaskFromSedova: Int = 0,
    @ColumnInfo var vestaTaskFromHimikov: Int = 0,
    @ColumnInfo var vestaTaskToZhukova: Int = 0,
    @ColumnInfo var vestaTaskToKulturi: Int = 0,
    @ColumnInfo var vestaTaskToSedova: Int = 0,
    @ColumnInfo var vestaTaskToHimikov: Int = 0,
    @ColumnInfo var vestaTaskElse: Int = 0
) : Serializable