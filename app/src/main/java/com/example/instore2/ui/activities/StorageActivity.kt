package com.example.instore2.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.instore2.R
import com.example.instore2.adapters.StorageViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class StorageActivity : AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)

        initViews()
        setupViewPagerWithTablayout()

    }

    private fun initViews() {
        tabLayout = findViewById(R.id.storage_tablayout)
        viewPager = findViewById(R.id.storage_view_pager)
    }

    private fun setupViewPagerWithTablayout() {
        val storageViewPagerAdapter = StorageViewPagerAdapter(supportFragmentManager , lifecycle)
        viewPager.adapter = storageViewPagerAdapter

        tabLayout.setOnTabSelectedListener(object  : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { viewPager.setCurrentItem(it) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }
}