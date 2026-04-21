package com.example.sunlifeapps.view.adapter

import android.R.attr.onClick
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sunlifeapps.R
import com.example.sunlifeapps.data.pref.SilancarModel


class RecyclerviewAdapter(
    private val list: List<SilancarModel>,
    private val onClick: (SilancarModel) -> Unit
) : RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>() {

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtMenu: TextView = itemView.findViewById(R.id.txtMenu)
        val icon: ImageView = itemView.findViewById(R.id.imgMenu)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.txtMenu.text = item.title
        holder.icon.setImageResource(item.icon)

// 🔥 INI YANG PENTING
        holder.itemView.setOnClickListener {
            onClick(item)
        }

//        holder.itemView.setOnClickListener {
//            Toast.makeText(holder.itemView.context, item.title, Toast.LENGTH_SHORT).show()
//        }
    }

    override fun getItemCount(): Int = list.size
}