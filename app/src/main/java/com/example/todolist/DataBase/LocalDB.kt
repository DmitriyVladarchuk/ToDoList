package com.example.todolist.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todolist.Models.TaskItem

@Database(
    entities = [TaskItem::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class LocalDB: RoomDatabase() {
    abstract fun dao(): TodoDAO

    companion object{
        @Volatile
        private var INSTANCE: LocalDB? = null

        fun getBataBase(context: Context): LocalDB{
            return INSTANCE ?: synchronized(this){
                buildDataBase(context).also{ INSTANCE = it }
            }
        }

        private fun buildDataBase(context: Context) = Room
            .databaseBuilder(context, LocalDB::class.java, "TodoList_database")
            .fallbackToDestructiveMigration()
            .build()

    }
}