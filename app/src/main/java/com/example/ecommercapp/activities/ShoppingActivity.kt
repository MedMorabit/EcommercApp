package com.example.ecommercapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ecommercapp.R
import com.example.ecommercapp.databinding.ActivityShoppingBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingActivity: AppCompatActivity() {
    lateinit var  binding:ActivityShoppingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        binding=ActivityShoppingBinding.inflate(layoutInflater)
        val navController=findNavController(R.id.shoppingFragmentContainer)
        binding.bottomNavigationView.setupWithNavController(navController)

    }
}