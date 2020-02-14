package jp.onlineconsultant.taskadmin.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import jp.onlineconsultant.listsample.Task


@Dao
interface TaskDao {

    @Query("SELECT * FROM task ORDER By id DESC LIMIT 30")
    suspend fun taskListNotLiveData(): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(task: Task)

    @Delete
    fun delete(task: Task)

}
