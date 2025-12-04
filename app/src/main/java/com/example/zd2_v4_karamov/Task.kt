package com.example.zd2_v4_karamov

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(tableName = "Tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "name")
    val name : String,
    @ColumnInfo(name = "description")
    val description : String,
    @ColumnInfo(name = "date")
    var date : String,
    @ColumnInfo(name = "category")
    var category : String,
    @ColumnInfo(name = "done")
    var done : Boolean
)