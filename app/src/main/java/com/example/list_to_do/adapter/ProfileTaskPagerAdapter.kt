package com.example.list_to_do.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.list_to_do.UI.fragment.CompleteFragment
import com.example.list_to_do.UI.fragment.PendingFragment

class ProfileTaskPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> PendingFragment()
            1 -> CompleteFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}