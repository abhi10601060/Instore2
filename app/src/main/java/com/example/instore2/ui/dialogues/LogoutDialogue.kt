package com.example.instore2.ui.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.instore2.R
import com.example.instore2.ui.activities.DummyStartActivity
import com.example.instore2.ui.activities.LoginActivity
import com.example.instore2.utility.InstoreApp
import com.example.instore2.utility.SharePrefs

class LogoutDialogue : DialogFragment() {

    lateinit var cancelButton : Button
    lateinit var logoutButton : Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(activity).inflate(R.layout.logout_dialogue_layout , null)

        val builder = AlertDialog.Builder(activity).setView(view)

        initViews(view)

        cancelButton.setOnClickListener(View.OnClickListener {
            dialog?.cancel()
        })

        logoutButton.setOnClickListener(View.OnClickListener {
            SharePrefs.getInstance(requireContext()).logout()
            (requireActivity().application as InstoreApp).createRepo()
            val intent = Intent(requireActivity() , DummyStartActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        })
        return builder.create()
    }

    private fun initViews(view: View) {
        cancelButton = view.findViewById(R.id.dialogue_btn_cancel)
        logoutButton = view.findViewById(R.id.dialogue_btn_logout)
    }

}