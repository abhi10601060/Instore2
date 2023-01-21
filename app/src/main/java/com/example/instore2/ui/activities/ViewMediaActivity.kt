package com.example.instore2.ui.activities

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import com.bumptech.glide.Glide
import com.example.instore2.R

class ViewMediaActivity : AppCompatActivity() {

    lateinit var imageView : ImageView
    lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_media)

        initViews()

        if (intent != null){

            val parent = intent.getStringExtra("parent")

            if (parent == "storage"){
                val path = intent.getStringExtra("path")
                if (path != null){
                    if (path.endsWith(".mp4")){
                        showVideo(path)
                    }
                    else{
                        showImage(path)
                    }
                }
            }
            else if (parent == "main"){
                val url = intent.getStringExtra("url")
                val mediaType = intent.getIntExtra("type" , 0)
                if (url != null){
                    when(mediaType){
                        1 -> { showMainImage(url) }

                        2 -> { showVideo(url) }
                    }
                }

            }

        }



    }

    private fun showMainImage(url : String) {
        videoView.visibility  = View.GONE
        imageView.visibility = View.VISIBLE

        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(imageView)
    }

    private fun initViews() {
        imageView = findViewById(R.id.view_image)
        videoView = findViewById(R.id.view_Video)
    }


    private fun showVideo(path : String) {
        videoView.visibility  = View.VISIBLE
        imageView.visibility = View.GONE

        videoView.setVideoURI(Uri.parse(path))

        val mediaController = MediaController(this)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)
        videoView.start()

    }

    private fun showImage(path: String) {
        videoView.visibility  = View.GONE
        imageView.visibility = View.VISIBLE

        val bitmap = BitmapFactory.decodeFile(path)
        imageView.setImageBitmap(bitmap)
    }
}