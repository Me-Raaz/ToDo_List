package com.example.android.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.todolist.DataBase.FeedReaderContract
import com.example.android.todolist.DataBase.From_DataBase_To_UI
import com.example.android.todolist.DataBase.TodoListItem
import com.example.android.todolist.DataBase.TodoItemsStore
import com.example.android.todolist.R.id.Edit_tittle

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val store = TodoItemsStore(this)
        todoAdapter = TodoAdapter(mutableListOf()) { tittle, ischecked ->
            store.updateTodoItem(tittle, ischecked)
        }


        val recyclerView = findViewById<RecyclerView>(R.id.rvTodoItems)
        val Add_button = findViewById<Button>(R.id.Add_button)
        val Edit_tittle =  findViewById<EditText>(Edit_tittle)
        val Del_button = findViewById<Button>(R.id.Del_button)

        //App Bar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "List Your Task Here Buddy"


        recyclerView.adapter = todoAdapter
        recyclerView.layoutManager =  LinearLayoutManager(this)

        Add_button.setOnClickListener{
            val todoTitle = Edit_tittle.text.toString()
            if(todoTitle.isNotEmpty()) {
                val todo = Todo(todoTitle)
                todoAdapter.addTodo(todo)
                Edit_tittle.text.clear()
                store.StoreTodoItem(TodoListItem(tittle = todoTitle , FeedReaderContract.Is_Not_Ticked))
            }

        }
        Del_button.setOnClickListener {
            todoAdapter.deleteDoneTodo()
            store.deleteCompleteItem()
        }
        val allItems = store.queryAlldata()
        for (item in allItems) {
            todoAdapter.addTodo(From_DataBase_To_UI(item))
        }

    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
/*        R.id.action_settings -> {
            // User chose the "Settings" item, show the app settings UI...
            true
        }*/

        R.id.action_favorite -> {
            // User chose the "Favorite" action, mark the current item
            // as a favorite...
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}