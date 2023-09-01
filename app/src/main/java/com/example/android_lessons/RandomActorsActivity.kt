package com.example.android_lessons

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.android_lessons.databinding.ActivityRandomActorsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class RandomActorsActivity : AppCompatActivity() {
    private val actors = generateActors()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_actors)
        val adapter = RandomActorsAdapter(this)
        adapter.submitList(actors)
        findViewById<RecyclerView>(R.id.randomActorList).also {
            it.adapter = adapter
            it.setHasFixedSize(true)
        }
        findViewById<FloatingActionButton>(R.id.fab).also {
            it.setOnClickListener {
                adapter.submitList(actors.shuffled())
            }
        }
    }
}