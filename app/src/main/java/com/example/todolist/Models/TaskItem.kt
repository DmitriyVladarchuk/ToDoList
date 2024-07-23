package com.example.todolist.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "tasks")
data class TaskItem(
    @PrimaryKey
    @ColumnInfo(name = "idTask")
    val id: UUID = UUID.randomUUID(),
    var isDone: Boolean = false,
    var creationDate: Date = Date(),
    var textTask: String,
    var importance: Int,
    var deadline: Date? = null,
)
