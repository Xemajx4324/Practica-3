package com.mexiti.cronoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo("title")
    val title: String, // Tipo de dato 1: String

    @ColumnInfo("priority")
    val priority: Int, // Tipo de dato 2: Int

    @ColumnInfo("is_completed")
    val isCompleted: Boolean, // Tipo de dato 3: Boolean

    @ColumnInfo("due_date")
    val dueDate: Long // Tipo de dato 4: Long (para guardar la fecha)
)