package com.example.android.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.todolist.R.id.Edit_tittle

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoAdapter = TodoAdapter(mutableListOf())


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
            }
        }
        Del_button.setOnClickListener {
            todoAdapter.deleteDoneTodo()
        }

    }
}