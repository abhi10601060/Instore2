package com.example.instore2.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.instore2.R
import com.example.instore2.utility.InstoreApp
import com.example.instore2.utility.SharePrefs

class DummyStartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dummy_start)


        if (!SharePrefs.getInstance(this).getBoolean(SharePrefs.IS_INSTAGRAM_LOGIN)){
            startActivityForResult(Intent(this , LoginActivity::class.java) , 100)
        }
        else{
            startActivity(Intent(this , MainActivity::class.java))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(resultCode){

            100 -> {
                (application as InstoreApp).createRepo()
                startActivity(Intent(this, MainActivity::class.java))
            }

        }
    }
}