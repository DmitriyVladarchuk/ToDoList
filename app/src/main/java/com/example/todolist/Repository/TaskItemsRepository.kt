package com.example.todolist.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.todolist.App
import com.example.todolist.DataBase.LocalDB
import com.example.todolist.Models.TaskItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class TaskItemsRepository private constructor() {

    companion object {
        private var INSTANCE: TaskItemsRepository? = null

        fun getInstance(): TaskItemsRepository {
            if (INSTANCE == null) {
                INSTANCE = TaskItemsRepository()
            }
            return INSTANCE ?: throw IllegalStateException("TodoItemsRepository Репозиторий не инициализирован.")
        }
    }

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun onDestroy() {
        coroutineScope.cancel()
    }

    private val localDB by lazy {
        LocalDB.getBataBase(App.context).dao()
    }

    val todoItems: LiveData<List<TaskItem>> = localDB.getAllTask().asLiveData()

    fun addTask(taskItem: TaskItem) {
        coroutineScope.launch {
            localDB.insertTask(taskItem)
        }
    }

    fun updateTask(taskItem: TaskItem) {
        coroutineScope.launch {
            localDB.updateTask(taskItem)
        }
    }

    fun deleteTask(taskItem: TaskItem) {
        coroutineScope.launch {
            localDB.deleteTask(taskItem)
        }
    }

    fun deleteIsDoneTasks() {
        coroutineScope.launch {
            localDB.deleteIsDoneTasks()
        }
    }

    fun deleteAllTasks() {
        coroutineScope.launch {
            localDB.deleteAllTask()
        }
    }

}