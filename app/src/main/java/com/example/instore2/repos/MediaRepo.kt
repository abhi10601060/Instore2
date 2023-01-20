package com.example.instore2.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instore2.models.StoryModel
import com.example.instore2.models.TrayModel
import com.example.instore2.networks.InstaService
import com.example.instore2.networks.Resource
import com.example.instore2.utility.SharePrefs
import retrofit2.Response

class MediaRepo(private val sharePrefs: SharePrefs , private val api : InstaService) {

    val MOZILLA_USR_AGENT = "\"Mozilla/5.0 (Windows NT 10.0; Win64; x64)\\AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.74 \\Safari/537.36 Edg/79.0.309.43\""
    val IPHONE_USER_AGENT = "\"Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+\""

    val cookie = "ds_user_id=" +
            sharePrefs.getString(SharePrefs.USERID)
                .toString() + "; sessionid=" +
            sharePrefs.getString(SharePrefs.SESSIONID).toString()


    private val storiesLivedata = MutableLiveData<Resource<StoryModel>>()

    val stories : LiveData<Resource<StoryModel>>
    get() = storiesLivedata

    suspend fun getStories(){
        storiesLivedata.postValue(Resource.Loading<StoryModel>())
        val response = api.getStories("https://i.instagram.com/api/v1/feed/reels_tray/" , cookie , IPHONE_USER_AGENT)
        storiesLivedata.postValue(handleStoryModel(response))
    }

    private fun handleStoryModel(response: Response<StoryModel>): Resource<StoryModel> {
        if (response.isSuccessful){
            if (response.body()!=null){
                return Resource.Success<StoryModel>(response.body()!!)
            }
        }
        return Resource.Error<StoryModel>(response.message())
    }


    private val mediaItemsLivedata = MutableLiveData<Resource<TrayModel>>()

    val mediaItems : LiveData<Resource<TrayModel>>
    get() = mediaItemsLivedata

    suspend fun getPersonalStories(pk : Long){
        mediaItemsLivedata.postValue(Resource.Loading<TrayModel>())
        val response = api.getStoryMedia("https://i.instagram.com/api/v1/feed/user/${pk}/reel_media/" , cookie, IPHONE_USER_AGENT)
        mediaItemsLivedata.postValue(handleMediaItems(response))
    }

    private fun handleMediaItems(response: Response<TrayModel>) : Resource<TrayModel>{
        if (response.isSuccessful){
            if (response.body() != null){
                return  Resource.Success<TrayModel>(response.body()!!)
            }
        }
        return Resource.Error<TrayModel>(response.message())
    }
}