package com.example.instore2.viewmodels

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
}