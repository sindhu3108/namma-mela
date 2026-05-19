package com.example.nammamela

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CastAdapter(private val castList: List<CastMember>) : RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val image: ImageView = v.findViewById(R.id.castImage)
        val name: TextView = v.findViewById(R.id.castName)
        val role: TextView = v.findViewById(R.id.castRole)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cast, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val member = castList[position]
        holder.name.text = member.name
        holder.role.text = member.role
        holder.image.setImageResource(member.imageUrl)
    }

    override fun getItemCount(): Int = castList.size
}
