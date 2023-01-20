package com.example.instore2.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instore2.R
import com.example.instore2.adapters.StoredMediaAdapter
import com.example.instore2.ui.activities.StorageActivity
import com.example.instore2.viewmodels.StorageViewModel

class PhotosFragment : Fragment(R.layout.fragment_photos_layout) {

    lateinit var photosRV : RecyclerView
    lateinit var storageViewModel: StorageViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photosRV = view.findViewById(R.id.photos_RV)
        storageViewModel = (activity as StorageActivity).storageViewModel

        storageViewModel.photos.observe(viewLifecycleOwner , Observer {photos ->
            val storedPhotosAdapter = context?.let { StoredMediaAdapter(photos , it) }
            photosRV.adapter = storedPhotosAdapter
            photosRV.layoutManager = GridLayoutManager(context, 2)
        })

    }
}