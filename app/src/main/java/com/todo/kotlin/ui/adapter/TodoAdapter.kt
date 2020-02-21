package com.todo.kotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.todo.kotlin.R
import com.todo.kotlin.databinding.ItemTodoBinding
import com.todo.kotlin.local.model.TodoEntity

class TodoAdapter:RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    private var todoList = ArrayList<TodoEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_todo, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(todoList[position])
    }

    fun setData(todoList:List<TodoEntity>){
        val diffCallback = TodoDiffCallback(this.todoList, todoList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.todoList.clear()
        this.todoList.addAll(todoList)
        diffResult.dispatchUpdatesTo(this)
    }

    class TodoDiffCallback(private val oldList:List<TodoEntity>, private val newList: List<TodoEntity>): DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldList[oldItemPosition].id == newList[newItemPosition].id)
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldList[oldItemPosition] == newList[newItemPosition])
        }

    }

    class ViewHolder(private val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: TodoEntity) {
            binding.tdoItem = todo
        }
    }
}