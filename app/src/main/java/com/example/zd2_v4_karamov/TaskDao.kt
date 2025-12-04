package com.example.zd2_v4_karamov

import android.icu.util.Calendar
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Locale

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item : Task)
    @Query("DELETE FROM tasks WHERE done = 1")
    fun deleteCompletedTasks()
    @Query("SELECT EXISTS(SELECT 1 FROM Tasks WHERE name = :sentname AND date = :sentdate LIMIT 1)")
    fun taskExists(sentname : String, sentdate : String): Boolean
    @Query("""
        SELECT * FROM tasks 
        ORDER BY 
            CASE category
                WHEN 'Срочная' THEN 1
                WHEN 'Важная' THEN 2
                WHEN 'Обычная' THEN 3
                ELSE 4
            END ASC,
            date ASC
    """)
    fun getAllItems() : Flow<List<Task>>
    @Delete
    fun delete(item : Task)
    @Update
    fun updateTask(item: Task)

}