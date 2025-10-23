package com.mexiti.cronoapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mexiti.cronoapp.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    // CAMBIO AQUÍ: Se añade "ORDER BY" para ordenar la lista
    @Query("SELECT * FROM tasks ORDER BY is_completed ASC, priority DESC")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTaskById(id: Long): Flow<Task>

    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}