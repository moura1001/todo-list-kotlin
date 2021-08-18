package kodilone.app.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kodilone.app.todolist.adapter.ToDoAdapter
import kodilone.app.todolist.model.ToDo

class MainActivity : AppCompatActivity() {

    private var todosRecyclerView: RecyclerView? = null
    private var todoAdapter: ToDoAdapter? = null

    private val todoList: MutableList<ToDo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        todosRecyclerView = findViewById(R.id.todosRecyclerView)
        todosRecyclerView?.setLayoutManager(LinearLayoutManager(this))
        todoAdapter = ToDoAdapter(this)
        todosRecyclerView?.setAdapter(todoAdapter)

        val task = ToDo()
        task.task = "Todo task test"
        task.status = 0
        task.id = 1

        todoList.add(task)
        todoList.add(task)
        todoList.add(task)
        todoList.add(task)
        todoAdapter!!.setTasks(todoList)

    }
}