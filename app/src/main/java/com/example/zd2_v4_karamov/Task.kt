package com.example.zd2_v4_karamov

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "label")
    val label : String,
    @ColumnInfo(name = "description")
    val description : String,
    @ColumnInfo(name = "done")
    var done : Boolean
)