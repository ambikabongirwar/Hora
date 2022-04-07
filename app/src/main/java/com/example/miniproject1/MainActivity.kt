package com.example.miniproject1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

//@Suppress("DEPRECATED_IDENTITY_EQUALS", "DEPRECATION")
class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val fragmentContainer = findViewById<View>(R.id.fragmentContainer)

        bottomNav.setupWithNavController(fragmentContainer.findNavController())

        //hideDefaultNavBar()

    }

    /*override fun onResume() {
        super.onResume()
        hideDefaultNavBar()
    }

    fun hideDefaultNavBar() {
        this.getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY /*or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE*/
        )
    }*/

}