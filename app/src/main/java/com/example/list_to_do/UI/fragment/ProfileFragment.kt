package com.example.list_to_do.UI.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.list_to_do.R
import com.example.list_to_do.adapter.ProfileTaskPagerAdapter
import com.example.list_to_do.databinding.FragmentProfileBinding
import com.example.list_to_do.viewModel.TaskViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.TextView
import com.example.list_to_do.Repositary.UserRepositoryImpl

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TaskViewModel
    private lateinit var userRepository: UserRepositoryImpl
    private lateinit var auth: FirebaseAuth

    // UI Elements
    private lateinit var profileImage: CircleImageView
    private lateinit var userName: TextView
    private lateinit var userBio: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize components
        auth = FirebaseAuth.getInstance()
        viewModel = TaskViewModel()
        userRepository = UserRepositoryImpl()

        // Setup UI elements
        profileImage = binding.profileImage
        userName = binding.userName
        userBio = binding.userBio

        // Load user data
        loadUserData()
        setupTaskStats()
        setupViewPager()

    }

    private fun loadUserData() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(requireContext(), "User not authenticated", Toast.LENGTH_SHORT).show()
            requireActivity().finish()
            return
        }

        userRepository.getUser(currentUser.uid) { user, success, message ->
            if (success && user != null) {
                userName.text = user.username
                userBio.text = user.about
                loadProfileImage(user.profile)
            } else {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadProfileImage(imageString: String?) {
        if (!imageString.isNullOrEmpty()) {
            try {
                val decodedBytes = Base64.decode(imageString, Base64.DEFAULT)
                val decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                profileImage.setImageBitmap(decodedBitmap)
            } catch (e: IllegalArgumentException) {
                profileImage.setImageResource(R.drawable.baseline_person_24)
            }
        } else {
            profileImage.setImageResource(R.drawable.baseline_person_24)
        }
    }

    private fun setupTaskStats() {
        viewModel.getTasks { tasks ->
            val pendingCount = tasks.count { !it.isCompleted }
            val completedCount = tasks.count { it.isCompleted }

            binding.pendingCount.text = pendingCount.toString()
            binding.completedCount.text = completedCount.toString()
        }
    }

    private fun setupViewPager() {
        val viewPager: ViewPager2 = binding.viewPager
        val tabLayout: TabLayout = binding.tabLayout

        val adapter = ProfileTaskPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when(position) {
                0 -> "Pending"
                1 -> "Completed"
                else -> ""
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}