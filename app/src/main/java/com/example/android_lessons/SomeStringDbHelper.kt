package com.example.android_lessons

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class SomeStringDbHelper(context: Context) : SQLiteOpenHelper(context, "SomeStringDB.db", null, 1) {
    companion object {
        object SomeStringColumns : BaseColumns {
            const val TABLE_NAME = "some_strings"
            const val COLUMN_NAME_STR = "str"
        }

        const val CREATE_QUERY = """
            CREATE TABLE ${SomeStringColumns.TABLE_NAME} (
            ${BaseColumns._ID} INTEGER PRIMARY KEY,
            ${SomeStringColumns.COLUMN_NAME_STR} TEXT
            )
        """

        const val DELETE_QUERY = "DROP TABLE IF EXISTS ${SomeStringColumns.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DELETE_QUERY)
        onCreate(db)
    }
}