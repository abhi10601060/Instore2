package com.example.instore2.adapters

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
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

        holder.parent.setOnClickListener(View.OnClickListener {
            launchInstagram(user)
        })
    }

    private fun launchInstagram(user: UserModel?) {
        val uriForInsta = Uri.parse("http://instagram.com/_u/${user?.username}")
        val intentForInsta = Intent(Intent.ACTION_VIEW, uriForInsta)

        val uriForBrowser = Uri.parse("http://instagram.com/${user?.username}")
        val intentForBrowser = Intent(Intent.ACTION_VIEW , uriForBrowser)

        try {
            context.startActivity(intentForInsta)
        }
        catch (e : ActivityNotFoundException){
            context.startActivity(intentForBrowser)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<CircleImageView>(R.id.recent_visit_profile_image)
        val recent_name = itemView.findViewById<TextView>(R.id.recent_visit_profile_name)
        val parent = itemView.findViewById<RelativeLayout>(R.id.recent_visit_parent)
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