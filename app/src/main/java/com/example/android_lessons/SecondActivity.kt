package com.example.android_lessons

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.util.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        findViewById<Button>(R.id.second_button1)?.apply { setOnClickListener { showAlertDialog() } }
        findViewById<Button>(R.id.second_button2)?.apply { setOnClickListener { showDialog() } }
        findViewById<Button>(R.id.second_button3)?.apply { setOnClickListener { showTimePicker() } }
        findViewById<Button>(R.id.second_button4)?.apply { setOnClickListener { showDatePicker() } }
        findViewById<Button>(R.id.second_button5)?.apply { setOnClickListener { showBottomShiftDialog() } }
        findViewById<Button>(R.id.second_button6)?.apply { setOnClickListener { showToast() } }
        findViewById<Button>(R.id.second_button7)?.apply { setOnClickListener { showSnackBar(it) } }
    }

    private fun showAlertDialog() {
        val dialog = LifeLongQuestionFragment()
        dialog.show(supportFragmentManager, "dialogFragment")
    }

    private fun showDialog() {
        TODO()
    }

    private fun showTimePicker() {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        TimePickerDialog(this, {p0, p1, p2 ->
            Toast.makeText(this, "you choosed $p1:$p2", Toast.LENGTH_SHORT).show()
        }, hour, minute, true).show()
    }

    private fun showDatePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this, {p0, p1, p2, p3 ->
            Toast.makeText(this, "you choosed $p3/${p2+1}/$p1", Toast.LENGTH_SHORT).show()
        }, year, month, day).show()
    }

    private fun showBottomShiftDialog() {
        TODO()
    }

    private fun showToast() {
        Toast.makeText(this, "Toast Created!", Toast.LENGTH_SHORT).show()
    }

    private fun showSnackBar(view: View) {
        Snackbar.make(view, "Snack bar created!!!", Snackbar.LENGTH_SHORT).apply {
            setAction("Undo") {
                Toast.makeText(context, "Close Snack bar", Toast.LENGTH_SHORT).show()
            }
            show()
        }
    }
}