package com.example.android.todolist.database

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns

class TodoItemStore(private val context: Context) {

    private val db = TodoItemDbHelper(context)
    private val dbReadable = db.readableDatabase


    fun storeTodoItem(item: TodoListItem) {
        // Gets the data repository in write mode
        val db = db.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(TodoItemContract.TodoItem.COLUMN_NAME_TITLE, item.title)
            put(TodoItemContract.TodoItem.COLUMN_NAME_IS_CHECKED, item.isChecked)
        }

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert(TodoItemContract.TodoItem.TABLE_NAME, null, values)

    }

    fun queryAllData(): List<TodoListItem> {

        val projection = arrayOf(
            BaseColumns._ID,
            TodoItemContract.TodoItem.COLUMN_NAME_TITLE,
            TodoItemContract.TodoItem.COLUMN_NAME_IS_CHECKED
        )

        val cursor = dbReadable.query(
            TodoItemContract.TodoItem.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        ) ?: return emptyList()

        val itemIds = mutableListOf<TodoListItem>()
        with(cursor) {
            while (moveToNext()) {
                val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val title =
                    getString(getColumnIndexOrThrow(TodoItemContract.TodoItem.COLUMN_NAME_TITLE))
                val isChecked =
                    getInt(getColumnIndexOrThrow(TodoItemContract.TodoItem.COLUMN_NAME_IS_CHECKED))
                itemIds.add(TodoListItem(title, isChecked))
            }
        }
        cursor.close()

        return itemIds
    }


    fun deleteCompletedItems() {
        // Define 'where' part of query.
        val selection = "${TodoItemContract.TodoItem.COLUMN_NAME_IS_CHECKED} LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(TodoItemContract.TodoItem.ITEM_CHECKED.toString())
        // Issue SQL statement.
        val deletedRows = dbReadable.delete(
            TodoItemContract.TodoItem.TABLE_NAME,
            selection,
            selectionArgs
        )
    }

    fun updateCheckStatus(oldItem: TodoListItem, isChecked: Boolean) {
        val db = db.writableDatabase

        // New value for one column
        val updatedChecked = if (isChecked) TodoItemContract.TodoItem.ITEM_CHECKED else TodoItemContract.TodoItem.ITEM_UNCHECKED
        val values = ContentValues().apply {
            put(TodoItemContract.TodoItem.COLUMN_NAME_IS_CHECKED, updatedChecked)
        }

        // Which row to update, based on the title
        val selection = "${TodoItemContract.TodoItem.COLUMN_NAME_TITLE} LIKE ?"
        val selectionArgs = arrayOf(oldItem.title)
        val count = db.update(
            TodoItemContract.TodoItem.TABLE_NAME,
            values,
            selection,
            selectionArgs)
    }


}