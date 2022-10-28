package com.example.ecommercapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ecommercapp.R
import com.example.ecommercapp.databinding.IntroductionFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroductionFragment : Fragment(R.layout.introduction_fragment) {
    lateinit var binding:IntroductionFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=IntroductionFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnIntro.setOnClickListener {
            findNavController()
                .navigate(R.id.action_introductionFragment_to_accountOptionsFragment)
        }
    }
}