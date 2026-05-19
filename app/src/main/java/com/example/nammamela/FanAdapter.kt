package com.example.nammamela

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FanAdapter(private val list: List<String>) : RecyclerView.Adapter<FanAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = v.findViewById(R.id.fanName)
        val comment: TextView = v.findViewById(R.id.fanComment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fan_comment, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = holder.itemView.context.getString(R.string.fan_user_format)
        holder.comment.text = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }
}