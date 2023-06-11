package com.example.android.todolist.DataBase

import com.example.android.todolist.Todo

data class TodoListItem (val tittle : String, val Ischecked : Int )

fun From_UI_To_DataBase(item: Todo) = TodoListItem(
    item.tittle,
    if (item.ischecked)
        FeedReaderContract.IsTicked
    else
        FeedReaderContract.Is_Not_Ticked
)

fun From_DataBase_To_UI(item: TodoListItem) = Todo(
    item.tittle,
    (item.Ischecked) == FeedReaderContract.IsTicked
)