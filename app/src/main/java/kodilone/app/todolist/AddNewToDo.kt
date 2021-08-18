package kodilone.app.todolist

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.widget.EditText
import kodilone.app.todolist.infra.DatabaseHandler
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import android.text.TextWatcher
import android.text.Editable
import kodilone.app.todolist.model.ToDo
import android.content.DialogInterface
import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.Button

class AddNewToDo : BottomSheetDialogFragment() {
    private var newTaskText: EditText? = null
    private var newTaskSaveButton: Button? = null
    private var db: DatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.new_todo, container, false)
        dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newTaskText = requireView().findViewById(R.id.newTodoText)
        newTaskSaveButton = requireView().findViewById(R.id.newTodoButton)
        var isUpdate = false
        val bundle = arguments
        if (bundle != null) {
            isUpdate = true
            val task = bundle.getString("task")
            newTaskText?.setText(task)
            assert(task != null)
            if (task!!.length > 0) newTaskSaveButton?.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorPrimaryDark
                )
            )
        }

        db = DatabaseHandler(activity)
        db!!.openDatabase()

        newTaskText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString() == "") {
                    newTaskSaveButton?.setEnabled(false)
                    newTaskSaveButton?.setTextColor(Color.GRAY)
                } else {
                    newTaskSaveButton?.setEnabled(true)
                    newTaskSaveButton?.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.colorPrimaryDark
                        )
                    )
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        val finalIsUpdate = isUpdate
        newTaskSaveButton?.setOnClickListener(View.OnClickListener {
            val text = newTaskText?.getText().toString()
            if (finalIsUpdate) {
                db!!.updateTask(bundle!!.getInt("id"), text)
            } else {
                val task = ToDo()
                task.task = text
                task.status = 0
                db!!.insertTask(task)
            }
            dismiss()
        })
    }

    override fun onDismiss(dialog: DialogInterface) {
        val activity: Activity? = activity
        if (activity is DialogCloseListener) (activity as DialogCloseListener).handleDialogClose(
            dialog
        )
    }

    companion object {
        const val TAG = "ActionBottomDialog"
        fun newInstance(): AddNewToDo {
            return AddNewToDo()
        }
    }
}