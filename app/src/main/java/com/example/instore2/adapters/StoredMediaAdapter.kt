package com.example.instore2.adapters

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instore2.R
import com.example.instore2.ui.activities.ViewMediaActivity
import java.io.File
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class StoredMediaAdapter(private val medias: MutableList<File>, private val context: Context): RecyclerView.Adapter<StoredMediaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stored_media_layout , parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val media = medias.get(position)

        holder.mediaName.setText(media.name)

        holder.options.setOnClickListener(View.OnClickListener {
            Log.d("ABHI", "onBindViewHolder: ${holder.adapterPosition} ")
            showPopup(holder.options , media )
        })

        if(media.absolutePath.endsWith(".mp4")){
            val bitmap = ThumbnailUtils.createVideoThumbnail(media.absolutePath, MediaStore.Video.Thumbnails.MINI_KIND)
            holder.image.setImageBitmap(bitmap)
        }
        else{
            val bitmap = BitmapFactory.decodeFile(media.absolutePath)
            holder.image.setImageBitmap(bitmap)
        }
        val sdt = SimpleDateFormat("dd/MM/yy hh.mm aaa")
        val date = sdt.format(Date(media.lastModified()))

        holder.details.text = "${getMB(media)} mb | $date"

        holder.parent.setOnClickListener(View.OnClickListener {
            val intent = Intent(context , ViewMediaActivity::class.java)
            intent.putExtra("parent" , "storage")
            intent.putExtra("path" , media.absolutePath)
            context.startActivity(intent)
        })
    }

    private fun getMB(file: File): String {
        val size = file.length().toDouble() / (1024*1024)
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        return df.format(size).toDouble().toString()
    }

    private fun showPopup(view: View , media : File ) {
        val popupMenu = PopupMenu(view.context , view , Gravity.LEFT)
        popupMenu.inflate(R.menu.stored_post_menu)
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
            override fun onMenuItemClick(p0: MenuItem?): Boolean {
                when(p0?.itemId){
                    R.id.delete_media ->{
                        media.delete()
                        medias.remove(media)
                        notifyDataSetChanged()
                    }

                    R.id.send_media -> {
                        if (media.endsWith(".mp4")){
                            sendPost("video/*" , "Share Video" , media)
                        }
                        else {
                            sendPost("image/*" , "Share Image" , media)
                        }

                    }
                }
                return false
            }
        })
    }

    private fun sendPost(type : String , title : String, file : File) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType(type)
        intent.putExtra(Intent.EXTRA_STREAM , Uri.parse(file.path))
        context.startActivity(Intent.createChooser(intent , title))
    }


    override fun getItemCount(): Int {
        return medias.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.stored_media_item_image)
        val mediaName = itemView.findViewById<TextView>(R.id.media_name)
        val options = itemView.findViewById<ImageView>(R.id.media_item_options_btn)
        val details = itemView.findViewById<TextView>(R.id.media_details)
        val parent = itemView.findViewById<RelativeLayout>(R.id.parent_store_media)

    }
}