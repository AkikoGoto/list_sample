package jp.onlineconsultant.taskadmin.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import jp.onlineconsultant.listsample.Task


@Dao
interface TaskDao {
    @Query("SELECT * FROM task ORDER By id DESC LIMIT 30")
    //fun getAll(): List<Task>
    fun taskList(): LiveData<List<Task>>

    @Query("SELECT * FROM task ORDER By id DESC LIMIT 30")
    suspend fun taskListNotLiveData(): List<Task>

    @Query("SELECT * FROM task LIMIT 1")
    fun getNewest(): Task


    @get:Query("SELECT * FROM task ORDER BY id DESC LIMIT 1 ")
    val newestTaskLiveData:LiveData<Task?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(task: Task)

    @Delete
    fun delete(task: Task)

    @get:Query("SELECT * FROM task ORDER BY id DESC LIMIT 1")
    val oneTaskLiveData:LiveData<Task?>


}
