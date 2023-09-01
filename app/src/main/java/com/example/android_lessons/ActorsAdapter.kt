package com.example.android_lessons

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalArgumentException

class ActorsAdapter(
    context: Context,
    var actors: List<Actor>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_FOOTER = 1
        private const val TYPE_ACTOR = 2
    }
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun getItemCount(): Int = actors.size + 2
    override fun getItemId(position: Int): Long = position.toLong()
    fun getItem(position: Int): Actor = actors[position - 1]

    override fun getItemViewType(position: Int): Int =
        when(position) {
            0 -> TYPE_HEADER
            itemCount - 1 -> TYPE_FOOTER
            else -> TYPE_ACTOR
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when(viewType) {
            TYPE_HEADER -> HeaderViewHolder(inflater.inflate(R.layout.list_item_header, parent, false))
            TYPE_FOOTER -> FooterViewHolder(inflater.inflate(R.layout.list_item_footer, parent, false))
            TYPE_ACTOR -> ActorViewHolder(inflater.inflate(R.layout.list_item_actor, parent, false))
            else -> throw IllegalArgumentException()
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ActorViewHolder -> {
                holder.bind(getItem(position))
            }
            is FooterViewHolder -> holder.bind(actors.size)
        }
    }

    class ActorViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnClickListener {
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

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val count: TextView = itemView.findViewById(R.id.count)

        fun bind(count: Int) {
            this.count.text = "Total items: " + count.toString()
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)
}