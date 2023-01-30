package com.example.instore2.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
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
import com.github.ybq.android.spinkit.SpinKitView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DummyStartActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var loginButton : Button
    lateinit var progressBar : SpinKitView
    lateinit var withoutLoginButton : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dummy_start)

        initViews()

        val repo = (application as InstoreApp).mediaRepo
        viewModel = ViewModelProvider(this , MainViewModelFactory(repo)).get(MainViewModel::class.java)

//        viewModel.getCurrentUser()
//
//        viewModel.currentUser.observe(this , Observer {
//            when(it){
//                is Resource.Success<CurrentUserModel> -> {
//                    Log.d("ACTIVITY", "onCreate:  called")
//                    val intent = Intent(this , MainActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    startActivity(intent)
//                }
//
//                is Resource.Error<CurrentUserModel> -> {
//                    progressBar.visibility = View.GONE
//                    loginButton.visibility = View.VISIBLE
//                }
//            }
//        })

        GlobalScope.launch(Dispatchers.IO){
            val response = viewModel.currentUserCheck()
            if (response.isSuccessful){
                launch(Dispatchers.Main) {
                    Log.d("ACTIVITY", "onCreate:  called")
                    val intent = Intent(this@DummyStartActivity , MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            else{
                launch(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    loginButton.visibility = View.VISIBLE
                }
            }
        }


        loginButton.setOnClickListener(View.OnClickListener {
            startActivityForResult(Intent(this , LoginActivity::class.java) , 100)
        })
//        if (!SharePrefs.getInstance(this).getBoolean(SharePrefs.IS_INSTAGRAM_LOGIN)){
//            startActivityForResult(Intent(this , LoginActivity::class.java) , 100)
//        }
//        else{
//            startActivity(Intent(this , MainActivity::class.java))
//        }

        withoutLoginButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@DummyStartActivity , MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        })

    }

    private fun initViews() {
        loginButton = findViewById(R.id.login_button)
        progressBar = findViewById(R.id.login_progress_bar)
        withoutLoginButton = findViewById(R.id.txt_without_login_btn)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(resultCode){

            100 -> {
                (application as InstoreApp).createRepo()
                Log.d("ACTIVITY", "onActivityResult: called")
                val intent = Intent(this , MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

        }
    }
}