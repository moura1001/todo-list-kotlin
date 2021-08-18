package kodilone.app.todolist.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import kodilone.app.todolist.AddNewToDo
import kodilone.app.todolist.MainActivity
import kodilone.app.todolist.R
import kodilone.app.todolist.infra.DatabaseHandler
import kodilone.app.todolist.model.ToDo


class ToDoAdapter(private val activity: MainActivity, private val db: DatabaseHandler)
    : RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    private var todoList: MutableList<ToDo>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        db.openDatabase()
        val item = todoList!![position]
        holder.task.text = item.task
        holder.task.isChecked = toBoolean(item.status)
        holder.task.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                db.updateStatus(item.id, 1)
            } else {
                db.updateStatus(item.id, 0)
            }
        }
    }

    private fun toBoolean(n: Int): Boolean {
        return n != 0
    }

    override fun getItemCount(): Int {
        return todoList!!.size
    }

    fun setTasks(todoList: MutableList<ToDo>?) {
        this.todoList = todoList
        notifyDataSetChanged()
    }

    fun editItem(position: Int) {
        val item: ToDo = todoList!![position]
        val bundle = Bundle()
        bundle.putInt("id", item.id)
        bundle.putString("task", item.task)
        val fragment = AddNewToDo()
        fragment.setArguments(bundle)
        fragment.show(activity.supportFragmentManager, AddNewToDo.TAG)
    }

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        var task: CheckBox = view.findViewById(R.id.todoCheckBox)
    }
}