package com.parsiphal.loganshopdriverhelper.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Delivery(
    @PrimaryKey(autoGenerate = true) @ColumnInfo var id: Long = 0,
    @ColumnInfo var deliveryDate: String = "",
    @ColumnInfo var workType: String = "",
    @ColumnInfo var deliveryType: String = "",
    @ColumnInfo var payType: String = "",
    @ColumnInfo var address: String = "",
    @ColumnInfo var cost: Int = 0,
    @ColumnInfo var comment: String = ""
)