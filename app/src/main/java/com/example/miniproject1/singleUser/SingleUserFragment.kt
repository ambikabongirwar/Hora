package com.example.miniproject1.singleUser

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.miniproject1.R

class SingleUserFragment : Fragment(), View.OnClickListener {

    var navController: NavController? = null
    val TAG = "MainActivity"
    override fun getLifecycle(): Lifecycle {
        val lc = super.getLifecycle()
        Log.d(TAG, "LifeCycle method"+lc.toString())
        return lc
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d(TAG, "On view state restored")

    }

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
        view.findViewById<Button>(R.id.buttonRedirectToTasks).setOnClickListener(this)
        view.findViewById<Button>(R.id.buttonRedirectToMoodCheck).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.buttonRedirectToTasks -> navController!!.navigate(R.id.action_singleUserFragment_to_tasksFragment)
            R.id.buttonRedirectToMoodCheck -> navController!!.navigate(R.id.action_singleUserFragment_to_moodCheckFragment)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "on Attach fragment")
    }
}