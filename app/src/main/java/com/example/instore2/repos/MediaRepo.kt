package com.example.instore2.repos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instore2.models.*
import com.example.instore2.networks.InstaService
import com.example.instore2.networks.Resource
import com.example.instore2.utility.SharePrefs
import retrofit2.Response

class MediaRepo(private val sharePrefs: SharePrefs , private val api : InstaService) {

//    val MOZILLA_USR_AGENT = "\"Mozilla/5.0 (Windows NT 10.0; Win64; x64)\\AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.74 \\Safari/537.36 Edg/79.0.309.43\""
    val MOZILLA_USR_AGENT = sharePrefs.getString(SharePrefs.MOZILLA_USER_AGENT)
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
                Log.d("PREVIEW", "onCreate: response preview success")
                return  Resource.Success<TrayModel>(response.body()!!)
            }
        }
        Log.d("PREVIEW", "onCreate: response preview Error")
        return Resource.Error<TrayModel>(response.message())
    }

    suspend fun getUrlStory(url: String){
        Log.d("PREVIEW", "onCreate: repo getUrl called")
        mediaItemsLivedata.postValue(Resource.Loading<TrayModel>())
        val response = api.getUrlMediaItem(url , cookie , IPHONE_USER_AGENT)
        mediaItemsLivedata.postValue(handleMediaItems(response))
    }

    suspend fun getUrlMediaItem(url : String){
        Log.d("PREVIEW", "onCreate: repo getUrl called with user agent $MOZILLA_USR_AGENT")
        mediaItemsLivedata.postValue(Resource.Loading<TrayModel>())
        val response = api.getUrlMediaItem(url , cookie , MOZILLA_USR_AGENT)
        mediaItemsLivedata.postValue(handleMediaItems(response))
    }

    private val currentUserLiveData = MutableLiveData<Resource<CurrentUserModel>>()

    val currentUser : LiveData<Resource<CurrentUserModel>>
    get() = currentUserLiveData

    suspend fun getCurrentUser(){
        val response = api.getCurrentUser("https://i.instagram.com/api/v1/accounts/current_user" , cookie , IPHONE_USER_AGENT)
        currentUserLiveData.postValue(handleCurrentUser(response))
    }

    private fun handleCurrentUser(response: Response<CurrentUserModel>) : Resource<CurrentUserModel>{
        if (response.isSuccessful){
            if (response.body() != null){
                Log.d("Current user", "onCreate: current user success")
                return  Resource.Success<CurrentUserModel>(response.body()!!)
            }
        }
        Log.d("Current user", "onCreate: current user Error")
        return Resource.Error<CurrentUserModel>(response.message())
    }

    private val recentSearchLivedata = MutableLiveData<ArrayList<UserModel>>()

    val recentSearches : LiveData<ArrayList<UserModel>>
    get() = recentSearchLivedata

    fun addRecentSearch(owner : Long , search : UserModel){
        sharePrefs.putRecentSearch(search)
        getRecentSearches()
    }

    fun getRecentSearches(){
        Log.d("RECENT", "getRecentSearches: called")
        val recentSearch = sharePrefs.getRecentSearches()
        Log.d("RECENT", "getRecentSearches: retreived ${recentSearch.toString()}")
        recentSearchLivedata.postValue(recentSearch)
    }

    suspend fun getUser(url: String){
        Log.d("USER", "onCreate: repo getUser called")
        mediaItemsLivedata.postValue(Resource.Loading<TrayModel>())
        val response = api.getUser(url , cookie , IPHONE_USER_AGENT)
        mediaItemsLivedata.postValue(handleUser(response))
    }

    private fun handleUser(response: Response<UserSearchModel>)  : Resource<TrayModel>{
        if (response.isSuccessful){
            if (response.body() != null){
                Log.d("Current user", "onCreate: current user success")
                val trayModel = getTrayFromUser(response.body()!!)
                return Resource.Success<TrayModel>(trayModel)
            }
        }
        Log.d(" user", "onCreate: current user Error")
        return Resource.Error<TrayModel>(response.message())
    }

    private fun getTrayFromUser(userSearchModel: UserSearchModel) : TrayModel {
        val user = userSearchModel.graphql.user
        user.profilepicurl = user.profile_pic_url_hd
        val candidatesModel = CandidatesModel()
        candidatesModel.url = user.profile_pic_url_hd
        val imageVersionModel = ImageVersionModel()
        imageVersionModel.candidates = listOf(candidatesModel)
        val itemModel = ItemModel()
        itemModel.imageversions2 = imageVersionModel
        itemModel.mediatype = 1

        val trayModel = TrayModel()
        trayModel.user = user
        trayModel.items = listOf(itemModel)
        return trayModel
    }

    suspend fun currentUserCheck() : Response<CurrentUserModel>{
        return api.getCurrentUser("https://i.instagram.com/api/v1/accounts/current_user" , cookie , IPHONE_USER_AGENT)
    }
}