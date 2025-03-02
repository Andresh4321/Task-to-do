package com.example.list_to_do.UI.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.list_to_do.R
import com.example.list_to_do.UI.Activity.DashboardActivity
import com.example.list_to_do.UI.Activity.EditProfileActivity
import com.example.list_to_do.UI.Activity.Login_Activity
import com.google.firebase.auth.FirebaseAuth


class SettingFragment : Fragment() {
    lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        // Set onClick listeners for each LinearLayout
        val profileLayout: LinearLayout = view.findViewById(R.id.profile_section)
        val logoutLayout: LinearLayout = view.findViewById(R.id.logout_section)
        val editLayout:LinearLayout=view.findViewById(R.id.Edit_Profile)
        val editgenreLayout:LinearLayout=view.findViewById(R.id.edit_cat)

        // Handle click for Profile section
        profileLayout.setOnClickListener {
            // Navigate to DashboardActivity and open ProfileFragment
            val intent = Intent(requireContext(), DashboardActivity::class.java)
            intent.putExtra("SELECTED_TAB", R.id.nav_profile)  // Send signal to open Profile tab
            startActivity(intent)
        }

        // Handle click for Profile section
        editgenreLayout.setOnClickListener {
            // Navigate to DashboardActivity and open ProfileFragment
            val intent = Intent(requireContext(), DashboardActivity::class.java)
            intent.putExtra("SELECTED_TAB", R.id.nav_home)  // Send signal to open Profile tab
            startActivity(intent)
        }




        // Handle click for Profile section
        editLayout.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }

        // Handle click for Logout section
        logoutLayout.setOnClickListener {
            // Clear user session or Firebase authentication if applicable
            FirebaseAuth.getInstance().signOut()

            // Navigate to Login Activity
            val intent = Intent(requireContext(), Login_Activity::class.java)
            startActivity(intent)

            // Optionally, finish the current activity to prevent going back to it
            activity?.finish()
        }

        return view
    }
}