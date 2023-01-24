package com.example.instore2.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instore2.models.CurrentUserModel
import com.example.instore2.models.StoryModel
import com.example.instore2.models.TrayModel
import com.example.instore2.models.UserModel
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

    fun handleEnteredLink(rawUrl: String){
        if (!rawUrl.contains("/p/") && !rawUrl.contains("/reel/") && !rawUrl.contains("/stories/") && !rawUrl.contains("/tv/")){
            getUserMedia(rawUrl)
        }
        else if (rawUrl.contains("/stories/")){
            getStoryMediaFromUrl(rawUrl)
        }
        else{
            getUrlMediaItems(rawUrl)
        }
    }

    fun getStoryMediaFromUrl(rawUrl: String) {
        Log.d("STORY", "getStoryMediaFromUrl:  called")
        var mediaId = ""
        for (i in rawUrl.indices){
            if (rawUrl[i] == '?'){
                break
            }
            else if (rawUrl.substring(i).startsWith("/?")){
                break
            }
            else if (rawUrl[i] == '/'){
                mediaId = ""
            }
            else{
                mediaId += rawUrl[i]
            }
        }
        val newUrl = "https://i.instagram.com/api/v1/media/${mediaId}/info"
        Log.d("STORY", "getStoryMediaFromUrl: $mediaId ")
        viewModelScope.launch(Dispatchers.IO) {
            repo.getUrlStory(newUrl)
        }

    }

    private fun getUserMedia(rawUrl: String) {
        val suffix = "__a=1&__d=dis"
        val newUrl = rawUrl.replaceAfter("?" , suffix , "abcde" )
        viewModelScope.launch(Dispatchers.IO) {
            repo.getUser(newUrl)
        }
    }

    fun getUrlMediaItems(rawUrl : String){
        val url = getJsonUrl(rawUrl)
        Log.d("STORY", "onCreate: $url")
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

    val currentUser : LiveData<Resource<CurrentUserModel>>
    get() = repo.currentUser

    fun getCurrentUser(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getCurrentUser()
        }
    }

    val recentSearches : LiveData<ArrayList<UserModel>>
    get() = repo.recentSearches

    fun getRecentSearches(){
        repo.getRecentSearches()
    }

    fun putRecentSearch(owner: Long , search : UserModel){
        repo.addRecentSearch(owner, search)
    }
}