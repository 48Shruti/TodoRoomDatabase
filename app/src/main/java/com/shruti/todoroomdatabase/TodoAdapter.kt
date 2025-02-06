package com.shruti.todoroomdatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(val item : ArrayList<TodoEntity>, val todointerface: TodoAdapter.todoInterface) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    class ViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.tvname)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo_view, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(item[position].name)
        holder.itemView.setOnClickListener {
            todointerface.show(item[position],position)
        }
    }
    interface todoInterface{
        fun show(todoEntity: TodoEntity,position: Int)
    }
}