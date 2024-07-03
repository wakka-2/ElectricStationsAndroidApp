package com.app.electricstations.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.electricstations.R
import com.app.electricstations.model.Course
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList


/*
Created by Aiman Qaid on 19,يوليو,2020
Contact me at wakka-2@hotmail.com
*/
class CoursesAdapter(private var items: ArrayList<Course>) :
    RecyclerView.Adapter<CoursesAdapter.NavigationItemViewHolder>() {

    private lateinit var context: Context

    class NavigationItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationItemViewHolder {
        context = parent.context
        val navItem = LayoutInflater.from(parent.context).inflate(R.layout.subject_item, parent, false)
        return NavigationItemViewHolder(navItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NavigationItemViewHolder, position: Int) {

        val iv = holder.itemView.findViewById<ImageView>(R.id.ivIcon)

        Picasso.get()
            .load(items[position].icon.link)
            .into(iv)
        holder.itemView.findViewById<TextView>(R.id.tvSubject).text = items[position].title
    }

}