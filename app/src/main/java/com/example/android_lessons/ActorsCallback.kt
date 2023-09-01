package com.example.android_lessons

import androidx.recyclerview.widget.DiffUtil

class ActorsCallback : DiffUtil.ItemCallback<Actor>() {
    override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem.oscar == newItem.oscar
    }
}