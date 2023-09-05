package com.example.android_lessons

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.room.*
import org.w3c.dom.Text
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    @Entity(tableName = "users")
    data class User (
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: Long? = null,
        @ColumnInfo(name = "login")
        val login: String?,
        @ColumnInfo(name = "password")
        val password: String?
    )

    @Dao
    interface UserDao {
        @Query("SELECT * FROM users where login = :login")
        fun getByLogin(login: String): User?

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        fun insertUser(user: User): Long
    }

    @Database(entities = [User::class], version = 1)
    abstract class UserDatabase : RoomDatabase() {
        abstract fun userDao(): UserDao

        companion object {
            const val DATABASE_NAME = "Users.db"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val editLogin = findViewById<EditText>(R.id.edit_login)
        val editPassword = findViewById<EditText>(R.id.edit_passwd)
        val textLogin = findViewById<TextView>(R.id.text_login)
        val db = Room.databaseBuilder(
            this,
            UserDatabase::class.java,
            UserDatabase.DATABASE_NAME
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        val userDao = db.userDao()

        findViewById<Button>(R.id.btn_login_user).apply {
            setOnClickListener {
                val login = editLogin.text.toString()
                val password = editPassword.text.toString()
                val user = userDao.getByLogin(login)
                if (user != null && user.password == password) {
                    textLogin.text = "Вы успешно вошли в систему ${user}"
                } else {
                    textLogin.text = "Неверный логин или пароль"
                }
            }
        }

        findViewById<Button>(R.id.btn_new_user).apply {
            setOnClickListener {
                val login = editLogin.text.toString()
                val password = editPassword.text.toString()
                val id = userDao.insertUser(User(login = login, password = password))
                textLogin.text = "Новый пользователь с id=${id} добавлен"
            }
        }
    }
}