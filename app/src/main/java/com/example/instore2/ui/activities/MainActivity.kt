package com.example.instore2.ui.activities

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instore2.R
import com.example.instore2.adapters.MediaItemsAdapter
import com.example.instore2.adapters.StoriesAdapter
import com.example.instore2.models.StoryModel
import com.example.instore2.models.TrayModel
import com.example.instore2.models.UserModel
import com.example.instore2.networks.Resource
import com.example.instore2.utility.InstoreApp
import com.example.instore2.viewmodels.MainViewModel
import com.example.instore2.viewmodels.MainViewModelFactory


class MainActivity : AppCompatActivity() , StoriesAdapter.StoryIconClicked , MediaItemsAdapter.DownloadButtonClicked{

    lateinit var viewModel : MainViewModel
    lateinit var storiesRV : RecyclerView
    lateinit var mediaItemsRV : RecyclerView
    lateinit var searchBar : EditText
    lateinit var btnPreview : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        val mediaRepo = (application as InstoreApp).mediaRepo
        viewModel = ViewModelProvider(this , MainViewModelFactory(mediaRepo)).get(MainViewModel::class.java)

        viewModel.getStories()
        setStories()
        setMediaItem()

        btnPreview.setOnClickListener(View.OnClickListener {
            val url = searchBar.text.toString().trim()
            Log.d("PREVIEW", "onCreate: ${url.toString()}")
            if (url.contains("https://www.instagram.com/")){
                viewModel.getUrlMediaItems(url)
            }
        })
    }

    private fun initViews() {
        storiesRV = findViewById(R.id.story_profiles_RV)
        mediaItemsRV = findViewById(R.id.media_items_RV)
        searchBar = findViewById(R.id.edt_search_url_bar)
        btnPreview = findViewById(R.id.btn_preview)
    }

    private fun setStories(){
        viewModel.stories.observe(this , Observer {
            when(it){

                is Resource.Loading<StoryModel> -> {
                    // TODO: show progress bar
                }

                is Resource.Error<StoryModel> -> {
                    // TODO: show error dialogue
                }

                is Resource.Success<StoryModel> -> {
                    if (it.data != null){
                        val storyModel = it.data
                        if (storyModel.tray != null){
                            val storiesAdapter = StoriesAdapter(this , this)
                            storiesAdapter.submitList(storyModel.tray)
                            storiesRV.adapter = storiesAdapter
                        }
                    }
                }

            }
        })
    }

    override fun storyIconOnClicked(user: UserModel) {
        viewModel.getPersonalStories(user.pk)
    }

    private fun setMediaItem(){
        viewModel.mediaItems.observe(this , Observer {
            when(it){

                is Resource.Loading<TrayModel> -> {
                    // TODO: show progress bar
                }

                is Resource.Error<TrayModel> -> {
                    // TODO: show error dialogue
                }

                is Resource.Success<TrayModel> -> {
                    if (it.data != null){
                        val trayModel = it.data
                        if (trayModel.items != null){

                            if(trayModel.num_results == 1){
                                val mediaItemsAdapter = MediaItemsAdapter(this , trayModel.items.get(0).user, this)
                                if (trayModel.items.get(0).mediatype == 8){
                                    mediaItemsAdapter.submitList(trayModel.items.get(0).carousel_media)
                                }
                                else{
                                    mediaItemsAdapter.submitList(trayModel.items)
                                }
                                mediaItemsRV.adapter = mediaItemsAdapter
                                mediaItemsRV.layoutManager = GridLayoutManager(this , 2)
                            }
                            else{
                                val mediaItemsAdapter = MediaItemsAdapter(this , trayModel.user, this)
                                mediaItemsAdapter.submitList(trayModel.items)
                                mediaItemsRV.adapter = mediaItemsAdapter
                                mediaItemsRV.layoutManager = GridLayoutManager(this , 2)
                            }
                        }
                    }
                }

            }
        })
    }

    override fun onDownloadButtonClicked(mediaType: Int, url: String, user: UserModel) {
        val permission = checkStoragePermission()

        if (permission){
            var path = ""
            if(mediaType == 1){
                path = "/Instore2/photos/" + user.username + "_${java.util.Calendar.getInstance().timeInMillis.toString()}" + ".jpg"
            }
            else{
                path = "/Instore2/videos/"+ user.username + "_${java.util.Calendar.getInstance().timeInMillis.toString()}" + ".mp4"
            }

            val request = DownloadManager.Request(Uri.parse(url))
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI and DownloadManager.Request.NETWORK_MOBILE)
            request.setTitle("Downloading file...")
            request.setDescription("------")
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            request.allowScanningByMediaScanner()
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,path )

            val manager = (getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager)
            manager.enqueue(request)
            Toast.makeText(this, "Downloading started...", Toast.LENGTH_SHORT).show()
        }
        else{
            askStoragePermissions()
        }

    }

    private fun checkStoragePermission() : Boolean{
        if (ContextCompat.checkSelfPermission(this ,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    private fun askStoragePermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE) , 200)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){

            200 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "External Storage Permission Granted...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}