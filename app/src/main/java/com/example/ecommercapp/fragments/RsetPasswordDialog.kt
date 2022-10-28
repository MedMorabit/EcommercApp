package com.example.ecommercapp.fragments

import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.ecommercapp.R
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.setBottomSheetDialog(
 sentEmail:(String) ->Unit
){
  val dialog=BottomSheetDialog(requireContext())
    val view=layoutInflater.inflate(R.layout.reset_password_dialog,null)
    dialog.setContentView(view)
    dialog.show()
    val edtEmail=view.findViewById<EditText>(R.id.edt_resetPass)
    val sent=view.findViewById<Button>(R.id.btn_send)
    val cancel=view.findViewById<Button>(R.id.btn_cancel)
    sent.setOnClickListener {
        val email=edtEmail.text.toString().trim()
        sentEmail(email)
        dialog.dismiss()
    }
    cancel.setOnClickListener {
        dialog.dismiss()
    }

}