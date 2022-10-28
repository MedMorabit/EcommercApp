package com.example.ecommercapp.fragments.shoppingFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ecommercapp.R
import com.example.ecommercapp.adapters.HomePagerAdapter
import com.example.ecommercapp.databinding.FragmentHomeBinding
import com.example.ecommercapp.fragments.category.*
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {
lateinit var binding:FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryList= arrayListOf<Fragment>(
            MainCategoryFragment(),
            ChairFragment(),
            CupboardFragment(),
            TableFragment(),
            AccessoryFragment(),
            FurnitureFragment()

        )
        val homePagerAdapter=HomePagerAdapter(childFragmentManager,categoryList, lifecycle)
        binding.homeViewPager.adapter=homePagerAdapter

        TabLayoutMediator(binding.homeTabLayout,binding.homeViewPager){tab,position ->
            when(position){
                0 -> tab.text="Main"
                1 -> tab.text="Chair"
                2 -> tab.text="Cupboard"
                3 -> tab.text="Table"
                4 -> tab.text="Accessory"
                5 -> tab.text="Furniture"
            }

        }.attach()

    }

}