package jp.onlineconsultant.listsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.stetho.Stetho
import jp.onlineconsultant.listsample.data.database.getDatabase
import jp.onlineconsultant.listsample.data.repository.TaskRepository
import jp.onlineconsultant.listsample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var taskListViewModel: TaskListViewModel
    private lateinit var taskListAdapter: TaskListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Stetho.initialize(
            Stetho.newInitializerBuilder(applicationContext)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(applicationContext))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(applicationContext))
                .build()
        )


        setContentView(R.layout.activity_main)

        val database =
            getDatabase(this)
        val repository =
            TaskRepository(database.taskDao)

        taskListViewModel = ViewModelProviders.of(this, TaskListViewModel.Factory(repository))
            .get(TaskListViewModel::class.java)

        taskListAdapter = TaskListAdapter(taskClickCallback)


        //dataBinding用のレイアウトリソース
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.apply {
            taskList.adapter = taskListAdapter
        }

        setRecycleView(binding)

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {

            taskListViewModel.onButtonClicked(this)

        }

        observeViewModel(taskListViewModel)


    }

    private fun setRecycleView(binding: ActivityMainBinding) {
        val recyclerView: RecyclerView = binding.root.findViewById(R.id.task_list);
        //このレイアウトマネージャーとかの定義がないと、RecyclerViewが表示されない
        recyclerView.setHasFixedSize(true); // RecyclerViewのサイズを維持し続ける
        recyclerView.setLayoutManager(LinearLayoutManager(this));
        recyclerView.setItemAnimator(DefaultItemAnimator());

        // RecyclerView自体の大きさが変わらないことが分かっている時、
        // このオプションを付けておくと、パフォーマンスが改善されるらしい
        recyclerView.setHasFixedSize(true);
    }

    private val taskClickCallback = object : TaskClickCallback {
        override fun onClick(task: Task) {
            //適宜実装してください
        }
    }

    private fun observeViewModel(viewModel: TaskListViewModel) {

        val taskObserver = Observer<List<Task?>?> { tasks ->

            tasks?.let { taskListAdapter.setTaskList(it) }

        }

        viewModel.taskListLiveData.observe(this, taskObserver)


    }

}
