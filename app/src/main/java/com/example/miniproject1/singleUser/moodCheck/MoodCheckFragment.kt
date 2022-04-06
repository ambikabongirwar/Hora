package com.example.miniproject1.singleUser.moodCheck

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.miniproject1.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MoodCheckFragment : Fragment() {

    lateinit var bottomNav: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mood_check, container, false)
        bottomNav = container?.getRootView()?.findViewById(R.id.bottom_navigation)!!
        bottomNav.setVisibility(View.GONE)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomNav.setVisibility(View.VISIBLE)
    }
}