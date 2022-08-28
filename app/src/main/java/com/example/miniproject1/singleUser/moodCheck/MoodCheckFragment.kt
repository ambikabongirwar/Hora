package com.example.miniproject1.singleUser.moodCheck

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.miniproject1.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers.Main

class MoodCheckFragment : Fragment() {

    lateinit var bottomNav: BottomNavigationView
    lateinit var Music: Button
    lateinit var BreathExcercise: Button
    lateinit var smiling: ImageView
    lateinit var sad: ImageView
    lateinit var happy: ImageView
    lateinit var confused: ImageView

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Music = view.findViewById(R.id.Music)
        BreathExcercise= view.findViewById(R.id.BreathExcercise)
        smiling = view.findViewById(R.id.smiling)
        sad = view.findViewById(R.id.sad)
        happy = view.findViewById(R.id.happy)
        confused = view.findViewById(R.id.confused)

        smiling.setOnClickListener {
            alert("Lets Continue with Tasks", view.context)
        }
        sad.setOnClickListener {
            alert("Lets Do some Breath Excercise",view.context)
        }
        happy.setOnClickListener {
            alert("Music Treats EveryThing",view.context)
        }
        confused.setOnClickListener {
            alert("Get Some Peace With Breath Excercise",view.context)
        }

        Music.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.youtube.com/watch?v=cJOXu8CwDgM")
            intent.setPackage("com.google.android.youtube")
            startActivity(intent)
        }

        BreathExcercise.setOnClickListener {
            activity?.let{
                val intent = Intent (it, CountDownTimerActivity::class.java)
                Log.d("MoodCheckFragment", "intent created")
                it.startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    fun alert(message: String,context: Context) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(context)
        alertDialog.setTitle("Disclaimer")
        alertDialog.setMessage("\n" + message)
        alertDialog.setPositiveButton("Ok") { dialog, which ->
            dialog.cancel()
        }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomNav.setVisibility(View.VISIBLE)
    }
}