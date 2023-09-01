package com.example.android_lessons

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var count = 0
    private var tvValue: TextView? = null;
    private var button: Button? = null;

    companion object {
        private const val COUNT_ARGUMENT = "CountArgument"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(COUNT_ARGUMENT, count)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        count = savedInstanceState.getInt(COUNT_ARGUMENT)
        tvValue?.text = "The value is: $count"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvValue = findViewById<TextView>(R.id.textView)?.apply {
            text = "The value is: $count"
        }
        button = findViewById<Button>(R.id.button)?.apply {
            setOnClickListener {
                count++
                tvValue?.text = "The value is: $count"
            }
        }
        findViewById<Button>(R.id.button_second)?.also {
            it.setOnClickListener {
                startActivity(Intent(this, SecondActivity::class.java))
            }
        }
        findViewById<Button>(R.id.button_third)?.also {
            it.setOnClickListener {
                startActivity(Intent(this, RandomActorsActivity::class.java))
            }
        }
        findViewById<Button>(R.id.button_forth)?.also {
            it.setOnClickListener {
                startActivity(Intent(this, MultithreadingActivity::class.java))
            }
        }
        findViewById<Button>(R.id.button_fith)?.also {
            it.setOnClickListener {
                startActivity(Intent(this, LoadCatActivity::class.java))
            }
        }
    }

//    private fun moveToNextScreen() {
//        val intent = Intent(this, SecondActivity::class.java)
//
//        val transmittedString = "Строка для передачи"
//        intent.putExtra(SecondActivity.TRANSMITTED_STRING, transmittedString)
//        val transmittedInt = 12
//        intent.putExtra(SecondActivity.TRANSMITTED_INT, transmittedInt)
//        val transmittedBoolean = false
//        intent.putExtra(SecondActivity.TRANSMITTED_BOOL, transmittedBoolean)
//
//        startActivity(intent)
//    }
}