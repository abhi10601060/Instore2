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
import com.example.instore2.models.TrayModel
import com.example.instore2.models.UserModel
import de.hdodenhof.circleimageview.CircleImageView

class StoriesAdapter(private val context: Context ,private val storyIconClicked: StoryIconClicked ) : ListAdapter<TrayModel, StoriesAdapter.ViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.story_profile_layout , parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val trayModel = getItem(position)

        Glide.with(context).asBitmap()
            .load(trayModel.user.profilepicurl)
            .into(holder.profileImage)

        holder.profileName.text = trayModel.user.username.toString()

        holder.profileImage.setOnClickListener(View.OnClickListener {
            storyIconClicked.storyIconOnClicked(trayModel.user)
        })

    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val profileImage = itemView.findViewById<CircleImageView>(R.id.story_layout_profile_image)
        val profileName = itemView.findViewById<TextView>(R.id.story_layout_profile_name)
    }

    class DiffUtilCallBack : androidx.recyclerview.widget.DiffUtil.ItemCallback<TrayModel>() {
        override fun areItemsTheSame(oldItem: TrayModel, newItem: TrayModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TrayModel, newItem: TrayModel): Boolean {
            return oldItem.equals(newItem)
        }

    }

    interface StoryIconClicked {

        fun storyIconOnClicked(user : UserModel)

    }

}