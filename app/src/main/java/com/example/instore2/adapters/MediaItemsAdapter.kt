package com.example.instore2.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instore2.R
import com.example.instore2.models.ItemModel
import com.example.instore2.models.UserModel
import com.example.instore2.ui.activities.ViewMediaActivity
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text

class MediaItemsAdapter(private val context: Context , private val user: UserModel, private val downloadButtonClicked: DownloadButtonClicked) : ListAdapter<ItemModel, MediaItemsAdapter.ViewHolder>(DiffUtilCallBack()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.media_item_layout, parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        Glide.with(context)
            .asBitmap()
            .load(item.imageversions2.candidates[0].url.toString())
            .into(holder.image)

        Glide.with(context)
            .asBitmap()
            .load(user.profilepicurl)
            .into(holder.profileImage)

        holder.userName.text = user.username.toString()

        holder.downloadButton.setOnClickListener(View.OnClickListener {
            downloadMediaItem(item)
        })

        holder.image.setOnClickListener(View.OnClickListener {
            val intent = Intent(context , ViewMediaActivity::class.java)
            intent.putExtra("parent" , "main")
            intent.putExtra("type" , item.mediatype)
            when(item.mediatype){
                1 -> {intent.putExtra("url" , item.imageversions2.candidates[0].url.toString())}

                2 -> {intent.putExtra("url" , item.videoversions[0].url.toString())}
            }
            context.startActivity(intent)
        })
    }

    fun downloadMediaItem(item : ItemModel){
        if (item.mediatype ==1){
            downloadButtonClicked.onDownloadButtonClicked(item.mediatype , item.imageversions2.candidates.get(0).url ,user)
        }
        else{
            downloadButtonClicked.onDownloadButtonClicked(item.mediatype , item.videoversions.get(0).url , user)
        }
    }

    fun downloadAll(){
        Log.d("ABHI", "downloadAll:  called ")
        for (item in currentList){
            downloadMediaItem(item)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.media_item_image)

        val profileImage = itemView.findViewById<CircleImageView>(R.id.mediaItem_user_profile_image)
        val userName = itemView.findViewById<TextView>(R.id.media_item_user_name)
        val downloadButton = itemView.findViewById<ImageView>(R.id.media_item_download_btn)

    }
    class DiffUtilCallBack : androidx.recyclerview.widget.DiffUtil.ItemCallback<ItemModel>() {
        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem.equals(newItem)
        }

    }

    interface DownloadButtonClicked{
        fun onDownloadButtonClicked(mediaType : Int, url : String, user: UserModel)
    }
}