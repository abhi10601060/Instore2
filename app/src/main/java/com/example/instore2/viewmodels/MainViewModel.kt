package com.example.instore2.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instore2.models.StoryModel
import com.example.instore2.models.TrayModel
import com.example.instore2.networks.Resource
import com.example.instore2.repos.MediaRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(val repo : MediaRepo) : ViewModel() {

    val stories : LiveData<Resource<StoryModel>>
    get() = repo.stories

    fun getStories(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getStories()
        }
    }

    val mediaItems : LiveData<Resource<TrayModel>>
    get() =  repo.mediaItems

    fun getPersonalStories(pk : Long){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getPersonalStories(pk)
        }
    }

    fun getUrlMediaItems(rawUrl : String){
        val url = getJsonUrl(rawUrl)
        Log.d("PREVIEW", "onCreate: $url")
        viewModelScope.launch(Dispatchers.IO) {
            repo.getUrlMediaItem(url)
        }
    }

    fun getJsonUrl(rawUrl: String) : String{
        val suffix = "__a=1&__d=dis"
        val newUrl = rawUrl.replaceAfter("/?" , suffix , "abcde" )
        return newUrl
    }
}