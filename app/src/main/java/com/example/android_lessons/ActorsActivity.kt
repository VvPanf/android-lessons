package com.example.android_lessons

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ActorsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actors)
        val list = findViewById<RecyclerView>(R.id.actorList)
        list.setHasFixedSize(true)
        val actors = generateActors()
        val adapter = ActorsAdapter(this, actors)
        adapter.setHasStableIds(true)
        list.adapter = adapter
    }
}