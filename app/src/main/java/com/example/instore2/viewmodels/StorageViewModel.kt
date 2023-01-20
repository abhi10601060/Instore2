package com.example.instore2.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instore2.repos.StorageRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class StorageViewModel(private val repo : StorageRepo) : ViewModel() {

    val photos : LiveData<MutableList<File>>
    get() = repo.photos

    val videos : LiveData<MutableList<File>>
    get() = repo.videos

    fun loadStoredContent(){
        viewModelScope.launch(Dispatchers.IO){
            repo.loadPhotos()
            repo.loadVideos()
        }
    }
}