package kodilone.app.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import kodilone.app.todolist.MainActivity
import kodilone.app.todolist.R
import kodilone.app.todolist.model.ToDo

class ToDoAdapter(private val activity: MainActivity) : RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    private var todoList: List<ToDo>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = todoList!![position]
        holder.task.text = item.task
        holder.task.isChecked = toBoolean(item.status)
    }

    private fun toBoolean(n: Int): Boolean {
        return n != 0
    }

    override fun getItemCount(): Int {
        return todoList!!.size
    }

    fun setTasks(todoList: List<ToDo>?) {
        this.todoList = todoList
        notifyDataSetChanged()
    }

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        var task: CheckBox = view.findViewById(R.id.todoCheckBox)
    }
}