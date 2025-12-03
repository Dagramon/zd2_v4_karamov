package com.example.zd2_v4_karamov

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [Task::class], version = 1, exportSchema = false)
abstract class MainDB : RoomDatabase() {
    abstract fun getDao() : TaskDao
    companion object{
        fun getDb(context: Context): MainDB
        {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDB::class.java,
                "tasks.db"
            ).build()
        }
    }
}