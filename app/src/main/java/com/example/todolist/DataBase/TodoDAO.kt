package com.example.todolist.DataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.Models.TaskItem
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface TodoDAO {

    @Query("SELECT * FROM tasks")
    fun getAllTask(): Flow<List<TaskItem>>

    @Query("SELECT * FROM tasks WHERE idTask=:id")
    fun getTaskById(id: UUID): TaskItem

    @Insert(entity = TaskItem::class)
    suspend fun insertTask(taskItem: TaskItem)

    @Update(entity = TaskItem::class)
    suspend fun updateTask(taskItem: TaskItem)

    @Delete(entity = TaskItem::class)
    suspend fun deleteTask(taskItem: TaskItem)

    @Query("DELETE FROM tasks WHERE isDone=1")
    suspend fun deleteIsDoneTasks()

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTask()

}