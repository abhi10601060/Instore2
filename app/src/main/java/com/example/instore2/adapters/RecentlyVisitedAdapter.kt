package com.example.instore2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instore2.R
import com.example.instore2.models.UserModel
import de.hdodenhof.circleimageview.CircleImageView

class RecentlyVisitedAdapter(val context: Context) : ListAdapter<UserModel, RecentlyVisitedAdapter.ViewHolder>(DiffutilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.recent_visit_layout , parent , false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)

        Glide.with(context).asBitmap().load(user.profilepicurl).into(holder.image)

        holder.recent_name.text = user.username.toString()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<CircleImageView>(R.id.recent_visit_profile_image)
        val recent_name = itemView.findViewById<TextView>(R.id.recent_visit_profile_name)
    }

    class DiffutilCallback() : DiffUtil.ItemCallback<UserModel>(){
        override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem.pk == newItem.pk
        }

        override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return  oldItem.equals(newItem);
        }
    }


}