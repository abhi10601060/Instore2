package com.example.instore2.networks

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroInstance {

    fun getInstance() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://www.instagram.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}