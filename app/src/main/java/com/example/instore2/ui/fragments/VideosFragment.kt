package com.example.instore2.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.instore2.R
import com.example.instore2.ui.activities.StorageActivity
import com.example.instore2.viewmodels.StorageViewModel

class VideosFragment : Fragment(R.layout.fragment_videos_layout) {

    lateinit var videosRV : RecyclerView
    lateinit var storageViewModel: StorageViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videosRV = view.findViewById(R.id.videos_RV)
        storageViewModel = (activity as StorageActivity).storageViewModel

        storageViewModel.photos.observe(viewLifecycleOwner , Observer {

        })

    }
}