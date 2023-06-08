package com.example.android.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.todolist.R.id.Edit_tittle
import com.example.android.todolist.database.TodoItemContract
import com.example.android.todolist.database.TodoItemStore
import com.example.android.todolist.database.TodoListItem

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val store = TodoItemStore(this)
        todoAdapter = TodoAdapter(mutableListOf()) {
            item : Todo, isChecked : Boolean->
            store.updateCheckStatus(TodoListItem.fromTodo(item), isChecked)
        }


        val recyclerView = findViewById<RecyclerView>(R.id.rvTodoItems)
        val Add_button = findViewById<Button>(R.id.Add_button)
        val Edit_tittle =  findViewById<EditText>(Edit_tittle)
        val Del_button = findViewById<Button>(R.id.Del_button)

        recyclerView.adapter = todoAdapter
        recyclerView.layoutManager =  LinearLayoutManager(this)

        Add_button.setOnClickListener{
            val todoTitle = Edit_tittle.text.toString()
            if(todoTitle.isNotEmpty()) {
                val todo = Todo(todoTitle)
                todoAdapter.addTodo(todo)
                Edit_tittle.text.clear()
                store.storeTodoItem(TodoListItem(title = todoTitle, TodoItemContract.TodoItem.ITEM_UNCHECKED))
            }
        }
        Del_button.setOnClickListener {
            todoAdapter.deleteDoneTodo()
            store.deleteCompletedItems()
        }
        val allItems = store.queryAllData()
        for (item in allItems) {
            todoAdapter.addTodo(TodoListItem.toTodo(item))
        }

    }
}