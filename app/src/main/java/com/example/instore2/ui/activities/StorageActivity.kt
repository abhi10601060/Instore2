package com.example.instore2.ui.activities

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.instore2.R
import com.example.instore2.adapters.StorageViewPagerAdapter
import com.example.instore2.utility.InstoreApp
import com.example.instore2.utility.StorageViewModelFactory
import com.example.instore2.viewmodels.StorageViewModel
import com.google.android.material.tabs.TabLayout

class StorageActivity : AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2
    lateinit var storageViewModel: StorageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)

        val permission = checkStoragePermission()
        Log.d("ABHI", "onCreate: Permission : $permission ")

        initViews()
        setupViewPagerWithTablayout()

        val repo = (application as InstoreApp).storageRepo
        storageViewModel = ViewModelProvider( this , StorageViewModelFactory(repo)).get(StorageViewModel::class.java)

        storageViewModel.loadStoredContent()



    }

    private fun checkStoragePermission() : Boolean{
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        askStoragePermissions()
        return false
    }

    private fun askStoragePermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE) , 200)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){

            200 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "External Storage Permission Granted...", Toast.LENGTH_SHORT).show()
                }
            }
        }
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