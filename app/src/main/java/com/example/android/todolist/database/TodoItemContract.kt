package com.example.android.todolist.database

import android.provider.BaseColumns

object TodoItemContract {

     const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${TodoItem.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${TodoItem.COLUMN_NAME_TITLE} TEXT," +
                "${TodoItem.COLUMN_NAME_IS_CHECKED} INT)"

     const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TodoItem.TABLE_NAME}"

    object TodoItem : BaseColumns {
        const val TABLE_NAME = "entry"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_IS_CHECKED = "isChecked"

        const val ITEM_CHECKED = 1
        const val ITEM_UNCHECKED = 0
    }

}