package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemUserBinding

class UsersAdapter (private val listUser : List<User>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersAdapter.ViewHolder, position: Int) {
        val user = listUser[position]
        holder.binding
    }

    override fun getItemCount(): Int = listUser.size
}