package com.example.miniproject1.singleUser

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.miniproject1.R
import com.example.miniproject1.singleUser.moodCheck.CountDownTimerActivity
import com.example.miniproject1.singleUser.tasksalarm.TaskListActivity

class SingleUserFragment : Fragment(), View.OnClickListener {

    var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.buttonRedirectToTasks).setOnClickListener {
            activity?.let{
                val intent = Intent (it, TaskListActivity::class.java)
                Log.d("MoodCheckFragment", "intent created")
                it.startActivity(intent)
            }
        }
        view.findViewById<Button>(R.id.buttonRedirectToMoodCheck).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.buttonRedirectToMoodCheck -> navController!!.navigate(R.id.action_singleUserFragment_to_moodCheckFragment)
        }
    }
}