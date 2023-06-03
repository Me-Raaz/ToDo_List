package com.example.android.todolist.sqlite

import android.provider.BaseColumns

object TodoContract {
    // Table contents are grouped together in an anonymous object.
    object TodoEntry : BaseColumns {
        const val TABLE_NAME = "entry"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_IS_CHECKED = "isChecked"
    }
}

