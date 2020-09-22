package com.example.madlevel5task2.Model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "gameBacklog")
data class Game(

    @ColumnInfo
    var title: String,

    @ColumnInfo
    var platform: String,

    @ColumnInfo
    var day: Int,

    @ColumnInfo
    var month: Int,

    @ColumnInfo
    var year: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Long? = null

) : Parcelable
