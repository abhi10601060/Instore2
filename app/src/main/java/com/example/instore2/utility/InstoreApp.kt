package com.example.instore2.utility

import android.app.Application
import com.example.instore2.networks.InstaService
import com.example.instore2.networks.RetroInstance
import com.example.instore2.repos.MediaRepo
import com.example.instore2.repos.StorageRepo

class InstoreApp : Application() {

    lateinit var mediaRepo : MediaRepo
    lateinit var storageRepo : StorageRepo

    override fun onCreate() {
        super.onCreate()

        createRepo()
    }

    private fun createRepo() {
        val api = RetroInstance.getInstance().create(InstaService::class.java)
        val sharePrefs = SharePrefs(applicationContext)

        mediaRepo = MediaRepo(sharePrefs , api)
        storageRepo = StorageRepo()

    }
}