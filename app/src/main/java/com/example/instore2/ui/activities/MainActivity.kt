package com.example.instore2.ui.activities

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instore2.R
import com.example.instore2.adapters.MediaItemsAdapter
import com.example.instore2.adapters.RecentlyVisitedAdapter
import com.example.instore2.adapters.StoriesAdapter
import com.example.instore2.models.CurrentUserModel
import com.example.instore2.models.StoryModel
import com.example.instore2.models.TrayModel
import com.example.instore2.models.UserModel
import com.example.instore2.networks.Resource
import com.example.instore2.utility.InstoreApp
import com.example.instore2.viewmodels.MainViewModel
import com.example.instore2.viewmodels.MainViewModelFactory
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView
import de.hdodenhof.circleimageview.CircleImageView


class MainActivity : AppCompatActivity() , StoriesAdapter.StoryIconClicked , MediaItemsAdapter.DownloadButtonClicked{

    lateinit var viewModel : MainViewModel
    lateinit var storiesRV : RecyclerView
    lateinit var mediaItemsRV : RecyclerView
    lateinit var recentVisitsRV : RecyclerView
    lateinit var searchBar : EditText
    lateinit var btnPreview : Button
    lateinit var toolbar: MaterialToolbar
    lateinit var currentUserProfileImage : CircleImageView
    lateinit var currentUserProfileName : TextView
    lateinit var recentlyVisitedCard : MaterialCardView
    lateinit var downloadAllButton : Button
    private var  mediaItemsAdapter : MediaItemsAdapter? = null
    var currentUserID : Long = 1234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        setSupportActionBar(toolbar)

        val mediaRepo = (application as InstoreApp).mediaRepo
        viewModel = ViewModelProvider(this , MainViewModelFactory(mediaRepo)).get(MainViewModel::class.java)

        viewModel.getCurrentUser()
        setCurrentUser()
        viewModel.getStories()
        setStories()
        setMediaItem()
        viewModel.getRecentSearches()
        setRecentVisits()

        btnPreview.setOnClickListener(View.OnClickListener {
            val url = searchBar.text.toString().trim()
            Log.d("PREVIEW", "onCreate: ${url.toString()}")
            if (url.contains("https://www.instagram.com/")){
                viewModel.getUrlMediaItems(url)
            }
        })

        downloadAllButton.setOnClickListener(View.OnClickListener {
            mediaItemsAdapter?.downloadAll()
            if (mediaItemsAdapter == null) Log.d("ABHI", "doawnload All : mediaItemAdapter is null")
        })

    }

    private fun setRecentVisits() {
        viewModel.recentSearches.observe(this , object : Observer<ArrayList<UserModel>> {
            override fun onChanged(it: ArrayList<UserModel>?) {
                if (it != null){
                    recentlyVisitedCard.visibility = View.VISIBLE
                    val recentVisitAdapter = RecentlyVisitedAdapter(this@MainActivity)
                    recentVisitAdapter.submitList(it.reversed())
                    recentVisitsRV.adapter = recentVisitAdapter
                }
                else{
                    recentlyVisitedCard.visibility = View.GONE
                    Log.d("RECENT", "onChanged: recent search is empty")
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.storage_menu, menu)
        return true
    }

    private fun initViews() {
        storiesRV = findViewById(R.id.story_profiles_RV)
        mediaItemsRV = findViewById(R.id.media_items_RV)
        searchBar = findViewById(R.id.edt_search_url_bar)
        btnPreview = findViewById(R.id.btn_preview)
        toolbar = findViewById(R.id.toolbar)
        currentUserProfileImage = findViewById(R.id.user_profile_image)
        currentUserProfileName = findViewById(R.id.txt_user_name)
        recentVisitsRV = findViewById(R.id.recent_visit_RV)
        recentlyVisitedCard = findViewById(R.id.recent_visit_card)
        downloadAllButton = findViewById(R.id.btn_download_all)
    }

    private fun setCurrentUser() {
        viewModel.currentUser.observe(this , Observer {
            when(it){

                is Resource.Error<CurrentUserModel> -> {
                    Toast.makeText(this, "Current User Did not found...", Toast.LENGTH_SHORT).show()}

                is Resource.Success<CurrentUserModel> -> {
                    val user = it.data?.user
                    if (user != null){
                        Glide.with(this)
                            .asBitmap()
                            .load(user.profilepicurl.toString())
                            .into(currentUserProfileImage)

                        currentUserProfileName.text = user.username.toString()
                        currentUserID = user.pk
                    }
                }

            }
        })
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
                            val stories = storyModel.tray.filter { it.user != null }
                            val storiesAdapter = StoriesAdapter(this , this)
                            storiesAdapter.submitList(stories)
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
                                mediaItemsAdapter = MediaItemsAdapter(this , trayModel.items.get(0).user, this)
                                if (trayModel.items.get(0).mediatype == 8){
                                    mediaItemsAdapter!!.submitList(trayModel.items.get(0).carousel_media)
                                }
                                else{
                                    mediaItemsAdapter!!.submitList(trayModel.items)
                                }
                                mediaItemsRV.adapter = mediaItemsAdapter
                                mediaItemsRV.layoutManager = GridLayoutManager(this , 2)
                            }
                            else{
                                mediaItemsAdapter = MediaItemsAdapter(this , trayModel.user, this)
                                mediaItemsAdapter!!.submitList(trayModel.items)
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
            viewModel.putRecentSearch(currentUserID , user)
            Log.d("RECENT", "onDownloadButtonClicked: $currentUserID")
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.storage_menu_item -> {
                val intent = Intent(this , StorageActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}