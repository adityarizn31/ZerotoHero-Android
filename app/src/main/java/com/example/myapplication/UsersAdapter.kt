package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemUsersBinding

class UsersAdapter (private val list : List<User>, private val onClick: (User) -> Unit) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    class ViewHolder (val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsersAdapter.ViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersAdapter.ViewHolder, position: Int) {
        val user = list[position]
        holder.binding.tvName.text = user.name
        holder.binding.tvEmail.text = user.email
    }

    override fun getItemCount(): Int = list.size
}