package com.shruti.todoroomdatabase

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SubTaskAdapter(val item : ArrayList<SubTaskEntity>, val subinterface: SubTaskAdapter.subInterface) : RecyclerView.Adapter<SubTaskAdapter.ViewHolder>() {
    class ViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.tvsub)
        val delete = view.findViewById<ImageView>(R.id.iv_delete)
        val checkbox = view.findViewById<CheckBox>(R.id.checkbox)
        val date = view.findViewById<TextView>(R.id.date)
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
        holder.checkbox.isChecked = item[position].completed
        if (item[position].completed) {
            holder.name.setTextColor(Color.GRAY)
            holder.date.setText("Completed")
        } else {
            holder.name.setTextColor(Color.BLACK)
            holder.date.setText(item[position].date)
        }
        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            item[position].completed = isChecked
            if (isChecked) {
                holder.name.setTextColor(Color.GRAY)
                holder.date.setText("Completed")
                subinterface.todoMark(item[position], position)
            } else {
                holder.name.setTextColor(Color.BLACK)
                holder.date.setText(item[position].date)
                subinterface.todoMark(item[position], position)
            }

        }
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
        fun todoMark(subTaskEntity: SubTaskEntity,position: Int)
    }

}