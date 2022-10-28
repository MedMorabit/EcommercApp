package com.example.ecommercapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomePagerAdapter(
    fm:FragmentManager,
    private val fragmentList:List<Fragment>,
    lifeCycle:Lifecycle) : FragmentStateAdapter(fm,lifeCycle) {

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
          return fragmentList[position]
    }
}