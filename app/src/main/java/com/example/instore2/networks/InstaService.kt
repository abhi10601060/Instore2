package com.example.instore2.networks

import com.example.instore2.models.StoryModel
import com.example.instore2.models.TrayModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface InstaService {

    @GET
    suspend fun getStories(@Url url : String, @Header("Cookie") str2 : String , @Header("User-Agent")  str3 : String) : Response<StoryModel>

    @GET
    suspend fun getStoryMedia(@Url url : String, @Header("Cookie") str2 : String , @Header("User-Agent")  str3 : String) : Response<TrayModel>

    @GET
    suspend fun getUrlMediaItem(@Url url : String, @Header("Cookie") str2 : String , @Header("User-Agent")  str3 : String) : Response<TrayModel>

}