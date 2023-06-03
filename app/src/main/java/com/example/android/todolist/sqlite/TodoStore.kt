package com.example.android.todolist.sqlite

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import androidx.lifecycle.Lifecycle
import com.example.android.todolist.Todo

class TodoStore(private val context: Context, val lifecycle: Lifecycle) {

    private val dbHelper by lazy { TodosDbHelper(context) }
    private val readyOnlyDb by lazy { dbHelper.readableDatabase }


    fun addToDb(todo: Todo) {
        // Gets the data repository in write mode
        val db = dbHelper.writableDatabase

// Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, todo.tittle)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED, if (todo.ischecked) 1 else 0)
        }

// Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert(TodoContract.TodoEntry.TABLE_NAME, null, values)


    }

    fun getAllTodos(): List<Todo> {

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        val projection = arrayOf(
            BaseColumns._ID,
            TodoContract.TodoEntry.COLUMN_NAME_TITLE,
            TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED
        )

// Filter results WHERE "title" = 'My Title'
        val selection = "${TodoContract.TodoEntry.COLUMN_NAME_TITLE} = ?"
        val selectionArgs = arrayOf("My Title")

// How you want the results sorted in the resulting Cursor
        val sortOrder = "${TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED} DESC"

        val cursor = readyOnlyDb.query(
            TodoContract.TodoEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )

        val itemIds = mutableListOf<Todo>()
        with(cursor) {
            moveToFirst()
            while (moveToNext()) {
                val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val title =
                    getString(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TITLE))
                val isChecked =
                    getInt(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED))
                itemIds.add(
                    Todo(
                        title,
                        isChecked == 1
                    )
                )
            }
        }
        cursor.close()
        return itemIds
    }


}