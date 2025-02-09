package com.shruti.todoroomdatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SubTaskAdapter(val item : ArrayList<SubTaskEntity>, val subinterface: SubTaskAdapter.subInterface) : RecyclerView.Adapter<SubTaskAdapter.ViewHolder>() {
    class ViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.tvsub)
        val delete = view.findViewById<ImageView>(R.id.iv_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_subtask_todo, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(item[position].subTaskName)
        holder.itemView.setOnClickListener {
            subinterface.show(item[position],position)
        }
        holder.delete.setOnClickListener {
            subinterface.delete(item[position], position)
        }

    }
    interface subInterface{
        fun show(subTaskEntity: SubTaskEntity,position: Int)
        fun delete(subTaskEntity: SubTaskEntity,position: Int)
    }

}