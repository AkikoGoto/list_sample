package jp.onlineconsultant.listsample

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.onlineconsultant.listsample.data.repository.TaskRepository
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


/**
 * タスク一覧のためのViewModel　
 *  下記をサンプルにして作成
 * https://qiita.com/Tsutou/items/69a28ebbd69b69e51703
 * @author Akiko Goto
 * @since Ver 1.0
 *
 */
class TaskListViewModel constructor(private val repository: TaskRepository) : ViewModel() {

    val TAG: String = "TaskListViewModel"
    var taskListLiveData = MutableLiveData<List<Task>>()

    init {
        loadTaskList()
    }


    private fun loadTaskList() {
        viewModelScope.launch {
            try {

                Log.d(TAG, "viewModelScope.launch ")
                val listNotLiveData = repository.getTaskList()

                // メンバー変数のLiveDataにこの値を送っている　これが大事！！ここは、LiveDataを送るのではなく、リスト形式を送るのがミソ
                taskListLiveData.postValue(listNotLiveData)


            } catch (e: Exception) {

                Log.e(TAG, "データ取得中にエラー " + e)

            }
        }
    }

    fun onButtonClicked(context: Context) {

        val task1 = Task(null, "ジョギングする", 1, 1, "2020/02/30", null, "2020/01/30", "2020/01/30");
        val task2 = Task(null, "麻雀する", 1, 1, "2020/02/10", null, "2020/01/30", "2020/01/30");
        val task3 = Task(null, "牛乳を買う", 1, 1, "2020/02/15", null, "2020/01/30", "2020/01/30");
        val task4 = Task(null, "人狼をする", 1, 1, "2020/02/20", null, "2020/01/30", "2020/01/30");

        var task_array = ArrayList<Task>()
        task_array.add(task1)
        task_array.add(task2)
        task_array.add(task3)
        task_array.add(task4)

        viewModelScope.launch {

            val index = Random().nextInt(task_array.size) // ランダムに選択された 0 以上 4 未満の整数
            val result = task_array.get(index)
            addTaskDb(context, result)

            //これはやらないくてもいいのかと思ってましたが、タスクリストをリフレッシュして表示するのに必要です。
            loadTaskList()
        }
    }


    suspend fun addTaskDb(context: Context, task: Task) {
        try {

            repository.addTask(context, task)

        } catch (cause: Throwable) {
            // If anything throws an exception, inform the caller
            Log.e("TaskViewModel", "エラー　でーたべーす")
            Toast.makeText(context, "データベースエラーで更新できませんでした!", Toast.LENGTH_LONG).show();
        }
    }



    //引数が必要な時は、Factoryが必要
    class Factory(private val repository: TaskRepository) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TaskListViewModel(repository) as T
        }
    }

}