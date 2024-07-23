package com.example.todolist

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.todolist.Models.TaskItem
import com.example.todolist.Repository.TaskItemsRepository

class App : Application() {

    override fun onCreate(){
        super.onCreate()

        TaskItemsRepository.getInstance().addTask(TaskItem(textTask = "aboba", importance = 1))
        //Log.d("db", TaskItemsRepository.getInstance().todoItems.value!!.size.toString())
        TaskItemsRepository.getInstance().todoItems.observeForever { it ->
            Log.d("db", it.size.toString())
        }
    }

    init{
        instance = this
    }

    companion object{
        private var instance:App? = null

        val context
            get() = applicationContext()

        private fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

}