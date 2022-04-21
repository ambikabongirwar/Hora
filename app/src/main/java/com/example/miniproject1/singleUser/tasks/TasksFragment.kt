package com.example.miniproject1.singleUser.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class TasksFragment : Fragment() {

    lateinit var bottomNav: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        bottomNav = container?.getRootView()?.findViewById(R.id.bottom_navigation)!!
        bottomNav.setVisibility(View.GONE)

        //val taskList = DataSource().loadTasks()
        val taskList = mutableListOf<Task>(
            Task("Complete Seminar", true),
            Task("Complete mini project", false)
        )
        val rvtasks = view.findViewById<RecyclerView>(R.id.rvAllTasks)
        rvtasks.adapter = TasksAdapter(this, taskList)
        val adapter = rvtasks.adapter

        val btnAddTask: Button = view.findViewById(R.id.btnAddTask)

        btnAddTask.setOnClickListener{
            val etTask = view.findViewById<EditText>(R.id.enter_task)
            val title = etTask.text.toString()
            //DataSource().addTasks(title, false)
            taskList.add(Task(title, false))
            adapter!!.notifyItemInserted(taskList.size - 1)
        }

        //recyclerView.setHasFixedSize(true)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomNav.setVisibility(View.VISIBLE)
    }
}