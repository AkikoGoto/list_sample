package jp.onlineconsultant.listsample.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import jp.onlineconsultant.listsample.Task
import jp.onlineconsultant.taskadmin.data.dao.TaskDao


@Database(entities = [Task::class],
        version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val taskDao : TaskDao
}

private lateinit var INSTANCE: AppDatabase
/**
 * Instantiate a database from a context.
 */
fun getDatabase(context: Context): AppDatabase {
    synchronized(AppDatabase::class) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room
                .databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "task-admin"
                )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}

