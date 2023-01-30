package com.example.instore2.ui.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.instore2.R
import com.example.instore2.ui.activities.DummyStartActivity
import com.example.instore2.ui.activities.MainActivity
import com.example.instore2.utility.SharePrefs

class LoginDialogue : DialogFragment() {

    lateinit var noButton : Button
    lateinit var yesButton : Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = LayoutInflater.from(activity).inflate(R.layout.login_dialogue_layout , null)
        initViews(view)
        val builder = AlertDialog.Builder(activity).setView(view)

        yesButton.setOnClickListener(View.OnClickListener {
            saveUrl()
            val intent = Intent(requireActivity() , DummyStartActivity::class.java)
            intent.putExtra("purpose" , "login")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        })

        noButton.setOnClickListener(View.OnClickListener {
            dialog?.cancel()
        })

        return builder.create()
    }

    private fun saveUrl() {
        val url = (activity as MainActivity).searchBar.text.toString()
        SharePrefs.getInstance(activity).putString(SharePrefs.INCOMING_URL , url)
    }

    private fun initViews(view: View) {
        yesButton = view.findViewById(R.id.dialogue_btn_yes_login)
        noButton = view.findViewById(R.id.dialogue_btn_no_login)
    }
}