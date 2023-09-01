package com.example.android_lessons

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalArgumentException

class RandomActorsAdapter(
    context: Context
) : ListAdapter<Actor, RandomActorsAdapter.RandomActorViewHolder>(ActorsCallback()) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomActorViewHolder =
        RandomActorViewHolder(inflater.inflate(R.layout.list_item_actor, parent, false))


    override fun onBindViewHolder(holder: RandomActorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RandomActorViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnClickListener {
//        private val avatar: ImageView = itemView.findViewById(R.id.avatar)
        private val name: TextView = itemView.findViewById(R.id.name)
        private val oscar: ImageView = itemView.findViewById(R.id.oscar)

        init {
            itemView.setOnClickListener {
                onClick(it)
            }
        }

        fun bind(actor: Actor) {
//        loadImageAsync(actor.avatar, avatar)
            name.text = actor.name
            if (actor.oscar)
                oscar.visibility = View.VISIBLE
            else
                View.GONE
        }

        override fun onClick(view: View) {
            if (name.textColors == ColorStateList.valueOf(Color.RED))
                name.setTextColor(Color.BLACK)
            else
                name.setTextColor(Color.RED)
        }
    }
}