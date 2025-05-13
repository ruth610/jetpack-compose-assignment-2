package com.todoapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//creating a database that has only one table
@Database(entities = [TodoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
//        ensures only one instance of the database is created
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
//                Build the Room database
                Room.databaseBuilder(
//                    Prevents memory leaks(A memory leak happens when a
//                    computer program fails to release memory that it no longer needs.) by using app context.
                    context.applicationContext,
//                    The name of the SQLite database file created locally
                    AppDatabase::class.java,
                    "todo_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}
