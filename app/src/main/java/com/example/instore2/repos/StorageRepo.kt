package com.example.instore2.repos

import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.File

class StorageRepo {
    private val photosLiveData  = MutableLiveData<MutableList<File>>()
    private val videosLivedata  = MutableLiveData<MutableList<File>>()


    val photos : LiveData<MutableList<File>>
        get() = photosLiveData

    val videos : LiveData<MutableList<File>>
        get() = videosLivedata


    fun loadPhotos(){
        val photosDirectory = File("${Environment.getExternalStorageDirectory()}/Download/Instore2/photos" )

        val photos = photosDirectory.listFiles()

        Log.d("ABHI", "PHOTOS: ${photos.toString()} -- ${photosDirectory.toString()}")

        val imagePosts = photos?.filter {
            it.absolutePath.endsWith(".jpg") ||
                    it.absolutePath.endsWith(".png") ||
                    it.absolutePath.endsWith(".jpeg")
        }

        if (imagePosts != null) {
            for (photo in imagePosts){
                Log.d("ABHI", "PHOTO: ${photo.toString()}")
            }
        }
        if (imagePosts != null){
            photosLiveData.postValue(imagePosts.toMutableList())
        }
    }

    fun loadVideos(){
        val videosDirectory = File("${Environment.getExternalStorageDirectory()}/Download/Instore2/videos" )

        val videos = videosDirectory.listFiles()

        Log.d("ABHI", "getReels: ${videos.toString()} -- ${videosDirectory.toString()}")

        val videosMedia = videos?.filter {
            it.absolutePath.endsWith(".mp4")
        }

        if (videosMedia != null) {
            for (video in videosMedia){

                Log.d("ABHI", "gwtReels: ${video.toString()}")
            }
        }
        if (videosMedia != null) {
            videosLivedata.postValue(videosMedia.toMutableList())
        }

    }

}