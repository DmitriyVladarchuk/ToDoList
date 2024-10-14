package com.example.todolist.ui.view

import androidx.lifecycle.ViewModel
import com.example.todolist.Models.TaskItem
import com.example.todolist.Repository.TaskItemsRepository

class HomeViewModel : ViewModel() {

    val todoItemList = TaskItemsRepository.getInstance().todoItems

    fun addItem(item: TaskItem) {
        TaskItemsRepository.getInstance().addTask(item)
    }

    fun editItem(item: TaskItem) {
        TaskItemsRepository.getInstance().updateTask(item)
    }

    fun deleteItem(item: TaskItem) {
        TaskItemsRepository.getInstance().deleteTask(item)
    }

}