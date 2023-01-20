package com.example.instore2.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class MainActivity : AppCompatActivity() , StoriesAdapter.StoryIconClicked{

    lateinit var viewModel : MainViewModel
    lateinit var storiesRV : RecyclerView
    lateinit var mediaItemsRV : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        val mediaRepo = (application as InstoreApp).mediaRepo
        viewModel = ViewModelProvider(this , MainViewModelFactory(mediaRepo)).get(MainViewModel::class.java)

        viewModel.getStories()
        setStories()
        setMediaItem()
    }

    private fun initViews() {
        storiesRV = findViewById(R.id.story_profiles_RV)
        mediaItemsRV = findViewById(R.id.media_items_RV)
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
                            val mediaItemsAdapter = MediaItemsAdapter(this , trayModel.user)
                            mediaItemsAdapter.submitList(trayModel.items)
                            mediaItemsRV.adapter = mediaItemsAdapter
                            mediaItemsRV.layoutManager = GridLayoutManager(this , 2)
                        }
                    }
                }

            }
        })
    }

}