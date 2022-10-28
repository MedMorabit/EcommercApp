package com.example.ecommercapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ecommercapp.R
import com.example.ecommercapp.databinding.AccountOptionFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountOptionsFragment :Fragment(R.layout.account_option_fragment) {
    lateinit var binding:AccountOptionFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=AccountOptionFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            registerButtonAccountOption.setOnClickListener {
                findNavController().navigate(R.id.action_accountOptionsFragment_to_registerFragment)
            }
            loginButtonAccountOption.setOnClickListener {
                findNavController().navigate(R.id.action_accountOptionsFragment_to_loginFragment)

            }
        }
    }
}