package com.example.android.todolist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.icu.text.CaseMap.Title
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TodoAdapter(
    private val todos : MutableList<Todo>

) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder> (){
    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
               false
            )

        )
    }
    fun addTodo(todo: Todo){
        todos.add(todo)
        notifyItemInserted(todos.size - 1)

    }
    fun deleteDoneTodo(){
        todos.removeAll { todo ->
            todo.ischecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStikeThrough(tvTodoTitle: TextView, isChecked: Boolean){
        if(isChecked){
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        }else{
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and  STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {

        val tvTodoTittle : TextView = holder.itemView.findViewById<TextView>(R.id.tvTodoTittle)
        val cbDone = holder.itemView.findViewById<CheckBox>(R.id.cbDone)

        val curTodo = todos[position]
        holder.itemView.apply {
            tvTodoTittle.text = curTodo.tittle
            cbDone.isChecked = curTodo.ischecked
            toggleStikeThrough(tvTodoTittle, curTodo.ischecked)
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStikeThrough(tvTodoTittle, isChecked)
                curTodo.ischecked = !curTodo.ischecked
            }

        }
    }

    override fun getItemCount(): Int {
        return todos.size

    }
}