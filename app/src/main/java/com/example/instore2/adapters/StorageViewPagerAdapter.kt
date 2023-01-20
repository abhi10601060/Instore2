package com.example.instore2.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.instore2.ui.fragments.PhotosFragment
import com.example.instore2.ui.fragments.VideosFragment

class StorageViewPagerAdapter(val fragmentManager: FragmentManager, lifecycle: Lifecycle ) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0){
            return  PhotosFragment()
        }
        return  VideosFragment()
    }

}