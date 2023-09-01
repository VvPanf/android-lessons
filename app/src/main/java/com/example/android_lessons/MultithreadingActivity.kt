package com.example.android_lessons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*
import java.lang.Runnable
import kotlin.coroutines.CoroutineContext

class MultithreadingActivity : AppCompatActivity() {
    companion object {
        private const val MESSAGE_KEY = "MESSAGE_KEY"
    }

    private var textView: TextView? = null
    private val myHandler = MyHandler()
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multithreading)
        textView = findViewById(R.id.tv_mult)
        findViewById<Button>(R.id.btn_thread)?.apply {
            setOnClickListener {
                MyThread().start()
                Thread(MyRunnable()).start()
            }
        }
        findViewById<Button>(R.id.btn_coroutine)?.apply {
            setOnClickListener {
                scope.launch {
                    doSomeJob()
                }
                val str = scope.async {
                    doAnotherJob()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    inner class MyHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val text = msg.data.getString(MESSAGE_KEY, "")
            textView?.post {
                textView?.text = text
            }
        }
    }

    inner class MyThread : Thread() {
        override fun run() {
            sleep(6000)
            val msg = Message()
            msg.data.putString(MESSAGE_KEY, "Hello from thread!")
            myHandler.sendMessage(msg)
        }
    }

    inner class MyRunnable : Runnable {
        override fun run() {
            Thread.sleep(4000)
            val msg = Message()
            msg.data.putString(MESSAGE_KEY, "Hello from runnable!")
            myHandler.sendMessage(msg)
        }
    }

    suspend fun doSomeJob() {
        delay(3000)
        textView?.post {
            textView?.text = "Hello from coroutine!"
        }
    }

    suspend fun doAnotherJob(): String {
        delay(2000)
        return "Done!"
    }
}