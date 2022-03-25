package com.example.mysubmission2_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListUserAdapter(private val listuser: ArrayList<ResponseUserDetail>, onItemClick: OnItemClickCallback): RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback = onItemClick

    class ViewHolder (view: View): RecyclerView.ViewHolder(view){
        val img_photo: ImageView = view.findViewById(R.id.img_item_photo)
        val item_name: TextView = view.findViewById(R.id.tv_item_name)
        val item_username: TextView = view.findViewById(R.id.tv_username)
        val item_location: TextView = view.findViewById(R.id.tv_location)
        val item_company: TextView = view.findViewById(R.id.tv_location)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listuser[position]

        holder.item_name.text = data.name
        holder.item_username.text = data.login
        holder.item_company.text = data.company
        holder.item_location.text = data.location
        Glide.with(holder.itemView.context)
            .load(data.avatarUrl)
            .into(holder.img_photo)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listuser[holder.adapterPosition]) }
    }


    override fun getItemCount(): Int = listuser.size

    interface OnItemClickCallback {
        fun onItemClicked(data: ResponseUserDetail)
    }
}