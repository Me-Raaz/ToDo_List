package com.example.android.todolist.DataBase

import android.content.ContentValues
import android.content.Context
import android.icu.text.CaseMap.Title
import android.provider.BaseColumns
import com.example.android.todolist.MainActivity
import com.example.android.todolist.TodoAdapter


class TodoItemsStore(private val context: Context) {

    private val db = FeedReaderDbHelper(context)
    private val dbReadable = db.readableDatabase



/*

    fun store() {
        val tableName = FeedReaderContract.FeedEntry.TABLE_NAME
        val titlekey =FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE
        val isCheckedkey =FeedReaderContract.FeedEntry.COLUMN_NAME_IsChecked

        val sqlDataEntry = "INSERT INTO $tableName ($titlekey, $isCheckedkey) VALUES (\'Hello\',\'0\' );"

        db.writableDatabase.execSQL(
            sqlDataEntry
        )
    }
*/



    fun StoreTodoItem(item: TodoListItem) {

        val db = db.writableDatabase


    /*
        //SQL command For JAVA.....................
        //SQL command For JAVA.....................
        //SQL command For JAVA.....................
        //SQL command For JAVA.....................
        //SQL command For JAVA.....................
        //SQL command For JAVA.....................
    */

        val values = ContentValues().apply {
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, item.tittle)
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_IsChecked, item.Ischecked)
        }

        val newRowId = db?.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)

    }

    fun queryAlldata() :List<TodoListItem> {


        val projection = arrayOf(
            BaseColumns._ID,
            FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
            FeedReaderContract.FeedEntry.COLUMN_NAME_IsChecked
        )


        val cursor = dbReadable.query(
            FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
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
                val title = getString(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE))
                val isChecked = getInt(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_IsChecked))
                itemIds.add(TodoListItem(title, isChecked))
            }
        }
        cursor.close()

        return itemIds
    }


    fun deleteTodoItem(item: TodoListItem) {
/*
    fun delete(){
        val tableName = FeedReaderContract.FeedEntry.TABLE_NAME
        val titlekey =FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE


        val sqlDataEntry = "DELETE FROM $tableName WHERE $titlekey=\'null\';"
        db.writableDatabase.execSQL(
            sqlDataEntry
        )
    }
*/

        val db = db.writableDatabase
        // Define 'where' part of query.
        val selection = "${FeedReaderContract.FeedEntry.COLUMN_NAME_IsChecked} LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf("MyTitle")


        val deletedRows = db.delete(FeedReaderContract.FeedEntry.TABLE_NAME,selection,selectionArgs)
    }

    fun updateTodoItem(oldItem: TodoListItem, newItem: TodoListItem) {
        //TODO implement it
    }


}