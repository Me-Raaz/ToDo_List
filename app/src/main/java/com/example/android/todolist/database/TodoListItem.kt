package com.example.android.todolist.database

import com.example.android.todolist.Todo

data class TodoListItem(val title: String, val isChecked: Int) {

    companion object {

        fun fromTodo(item: Todo) = TodoListItem(
            item.tittle,
            if (item.ischecked) TodoItemContract.TodoItem.ITEM_CHECKED else TodoItemContract.TodoItem.ITEM_UNCHECKED
        )

        fun toTodo(item: TodoListItem) = Todo(
            item.title,
            item.isChecked == TodoItemContract.TodoItem.ITEM_CHECKED
        )
    }

}
