package com.example.instore2.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.instore2.R
import com.example.instore2.utility.SharePrefs

class DummyStartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dummy_start)


        if (!SharePrefs.getInstance(this).getBoolean(SharePrefs.IS_INSTAGRAM_LOGIN)){
            startActivityForResult(Intent(this , LoginActivity::class.java) , 100)
        }

    }
}