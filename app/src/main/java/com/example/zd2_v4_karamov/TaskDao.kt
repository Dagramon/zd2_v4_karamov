package com.example.zd2_v4_karamov

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item : Task)
    @Query("DELETE FROM tasks WHERE done = 1")
    fun deleteCompletedTasks()
    @Query("SELECT * FROM Tasks")
    fun getAllItems() : Flow<List<Task>>
    @Delete
    fun delete(item : Task)
    @Update
    fun updateTask(item: Task)
}