package com.example.instore2.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.instore2.R
import com.example.instore2.models.CurrentUserModel
import com.example.instore2.networks.InstaService
import com.example.instore2.networks.Resource
import com.example.instore2.networks.RetroInstance
import com.example.instore2.utility.InstoreApp
import com.example.instore2.utility.SharePrefs
import com.example.instore2.viewmodels.MainViewModel
import com.example.instore2.viewmodels.MainViewModelFactory

class DummyStartActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var loginButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dummy_start)

        initViews()

        val repo = (application as InstoreApp).mediaRepo
        viewModel = ViewModelProvider(this , MainViewModelFactory(repo)).get(MainViewModel::class.java)

        viewModel.getCurrentUser()

        viewModel.currentUser.observe(this , Observer {
            when(it){
                is Resource.Success<CurrentUserModel> -> {
                    Log.d("ACTIVITY", "onCreate:  called")
                    val intent = Intent(this , MainActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }

                is Resource.Error<CurrentUserModel> -> { loginButton.visibility = View.VISIBLE }
            }
        })


        loginButton.setOnClickListener(View.OnClickListener {
            startActivityForResult(Intent(this , LoginActivity::class.java) , 100)
        })
//        if (!SharePrefs.getInstance(this).getBoolean(SharePrefs.IS_INSTAGRAM_LOGIN)){
//            startActivityForResult(Intent(this , LoginActivity::class.java) , 100)
//        }
//        else{
//            startActivity(Intent(this , MainActivity::class.java))
//        }

    }

    private fun initViews() {
        loginButton = findViewById(R.id.login_button)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(resultCode){

            100 -> {
                (application as InstoreApp).createRepo()
                Log.d("ACTIVITY", "onActivityResult: called")
                val intent = Intent(this , MainActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }

        }
    }
}