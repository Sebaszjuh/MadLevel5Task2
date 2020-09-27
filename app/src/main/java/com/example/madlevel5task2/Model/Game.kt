package com.example.madlevel5task2.Model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "gameBacklog")
data class Game(

    @ColumnInfo
    var title: String,

    @ColumnInfo
    var platform: String,

    @ColumnInfo
    var release: Calendar,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Long? = null

) : Parcelable
