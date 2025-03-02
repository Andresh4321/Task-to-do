package com.example.list_to_do.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.list_to_do.UI.Activity.DashboardActivity
import com.example.list_to_do.UI.fragment.CompleteFragment
import com.example.list_to_do.UI.fragment.PendingFragment

class TaskFragmentAdapter(activity: DashboardActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PendingFragment ()
            1 -> CompleteFragment()
            else -> PendingFragment()
        }
    }
}