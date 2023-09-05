package com.example.android_lessons

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Button
import android.widget.EditText
import kotlin.random.Random

class StorageActivity : AppCompatActivity() {
    companion object {
        private const val FILE_STR = "FILE_STR"
        private const val PREFS_FILE_NAME = "some-file"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)
        val textFile = findViewById<EditText>(R.id.editFile)
        val textDatabase = findViewById<EditText>(R.id.editDatabase)

        // Restore values
        textFile.setText(restoreStingFromFile())
        textDatabase.setText(restoreStringFromDb())

        // Save values
        findViewById<Button>(R.id.btnFileSave)?.apply {
            setOnClickListener {
                saveStringToFile(textFile.text.toString())
            }
        }
        findViewById<Button>(R.id.btnDatabaseSave)?.apply {
            setOnClickListener {
                saveStringToDb(textDatabase.text.toString())
            }
        }
    }

    private fun saveStringToFile(text: String) {
        val sharedPrefs = getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString(FILE_STR, text)
        editor.apply()
    }

    private fun restoreStingFromFile(): String {
        val sharedPrefs = getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        return sharedPrefs.getString(FILE_STR, "") ?: ""
    }

    private fun saveStringToDb(text: String) {
        val dbHelper = SomeStringDbHelper(this)
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(SomeStringDbHelper.Companion.SomeStringColumns.COLUMN_NAME_STR, text)
        }
        db.insert(SomeStringDbHelper.Companion.SomeStringColumns.TABLE_NAME, null, values)
    }

    private fun restoreStringFromDb(): String {
        val dbHelper = SomeStringDbHelper(this)
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            BaseColumns._ID,
            SomeStringDbHelper.Companion.SomeStringColumns.COLUMN_NAME_STR
        )
        val cursor = db.query(
            SomeStringDbHelper.Companion.SomeStringColumns.TABLE_NAME,
            projection,
            null, null, null, null, null
        )
        var result = ""
        with(cursor) {
            while (moveToNext()) {
                val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val str = getString(getColumnIndexOrThrow(SomeStringDbHelper.Companion.SomeStringColumns.COLUMN_NAME_STR))
                result = itemId.toString() + str
            }
        }
        cursor.close()
        return result
    }
}