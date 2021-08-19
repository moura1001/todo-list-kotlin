package kodilone.app.todolist

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kodilone.app.todolist.adapter.ToDoAdapter
import kodilone.app.todolist.infra.DatabaseHandler
import kodilone.app.todolist.model.ToDo


class MainActivity : AppCompatActivity(), DialogCloseListener {

    private var todosRecyclerView: RecyclerView? = null
    private var todoAdapter: ToDoAdapter? = null
    private var fab: FloatingActionButton? = null

    private var todoList: MutableList<ToDo> = ArrayList()
    private var db: DatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        db = DatabaseHandler(this)
        db!!.openDatabase()

        todosRecyclerView = findViewById(R.id.todosRecyclerView)
        todosRecyclerView?.setLayoutManager(LinearLayoutManager(this))
        todoAdapter = ToDoAdapter(this, db!!)
        todosRecyclerView?.setAdapter(todoAdapter)

        val itemTouchHelper = ItemTouchHelper(RecyclerItemTouchHelper(todoAdapter!!))
        itemTouchHelper.attachToRecyclerView(todosRecyclerView)

        fab = findViewById(R.id.fab)

        todoList = db!!.allTasks
        todoList.reverse()
        todoAdapter!!.setTasks(todoList)

        fab?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                AddNewToDo.newInstance().show(supportFragmentManager, AddNewToDo.TAG)
            }
        })
    }

    override fun handleDialogClose(dialog: DialogInterface?) {
        todoList = db!!.allTasks
        todoList.reverse()
        todoAdapter!!.setTasks(todoList)
        todoAdapter!!.notifyDataSetChanged()
    }
}