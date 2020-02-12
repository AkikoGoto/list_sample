package jp.onlineconsultant.listsample.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import jp.onlineconsultant.listsample.R
import jp.onlineconsultant.listsample.Task
import jp.onlineconsultant.taskadmin.data.dao.TaskDao

/**
 *
 */
class TaskRepository(val taskDao: TaskDao) {


    suspend fun getTaskList(): List<Task> {

        try {

            val list = taskDao.taskListNotLiveData()
            return list


        } catch (cause: Throwable) {

            Log.e("TaskRepository", cause.toString())
            val list = emptyList<Task>()
            return list
        }


    }

    suspend fun addTask(context:Context, task:Task) {

        try {

            taskDao.insertOne(task)

        } catch (cause: Throwable) {

            Log.e("TaskRepository", cause.toString())
            Toast.makeText(context, "task_cannot_create_db_error" + cause.toString(), Toast.LENGTH_LONG).show();

        }
    }

}