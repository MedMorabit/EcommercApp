package com.example.ecommercapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecommercapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
    }
}