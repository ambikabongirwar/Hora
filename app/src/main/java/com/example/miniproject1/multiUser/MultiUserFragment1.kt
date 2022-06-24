package com.example.miniproject1.multiUser

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.GroupActivity
import com.example.miniproject1.R
import com.example.miniproject1.multiUser.adapters.CustomAdapter
import com.example.miniproject1.multiUser.datasource.GroupNamesDatasource
import com.example.miniproject1.multiUser.home.GroupCardAdapter
import com.example.miniproject1.multiUser.model.Group
import com.example.miniproject1.multiUser.model.ItemsViewModel
import com.example.miniproject1.singleUser.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MultiUserFragment1 : Fragment(), ItemListener {

    private lateinit var navController: NavController
    private lateinit var mAuth: FirebaseAuth
    lateinit var db: FirebaseFirestore

    private lateinit var recyclerView: RecyclerView
    private lateinit var groupArrayList: ArrayList<ItemsViewModel>
    private lateinit var groupCardAdapter: CustomAdapter
    var TAG = "MultiUserFragment1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_multi_user1, container, false)
        mAuth = FirebaseAuth.getInstance()
        db = Firebase.firestore
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        Log.d(TAG, "onViewCreated")
        val logOutBtn: ImageButton = view.findViewById(R.id.ibLogOut)
        logOutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            navController.navigate(R.id.action_multiUserFragment1_to_registerFragment1)
        }

        /*val createGroup: Button = view.findViewById(R.id.button2)
        val groupName: EditText = view.findViewById(R.id.textView3)
        createGroup.setOnClickListener {
            Toast.makeText(context, "" + groupName.text, Toast.LENGTH_SHORT).show()
        }*/

        /*val createGroup: FloatingActionButton = view.findViewById(R.id.newGroup)
        createGroup.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            navController.navigate(R.id.action_multiUserFragment1_to_registerFragment1)
        }*/

        recyclerView = view.findViewById(R.id.rvGroupNames)
        groupArrayList = ArrayList()

        recyclerView.layoutManager = LinearLayoutManager(view.context)

        //val groupNames = GroupNamesDatasource(view.context).getGroupNamesList()

        //val groupNames = getData()
        groupCardAdapter = CustomAdapter(groupArrayList, this)

        recyclerView.adapter = groupCardAdapter

        eventChangeListener()

    }

    override fun onClicked(name: String) {
        val intent = Intent(context, GroupActivity::class.java)
        intent.putExtra("groupName", name)
        context?.startActivity(intent)
        //Toast.makeText(context, "Name is "+name, Toast.LENGTH_SHORT).show()
    }

    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("groups")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    groupArrayList.add(ItemsViewModel(document.data["Name"].toString()))
                    groupCardAdapter.notifyDataSetChanged()
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("MultiUser Fragment", "Error getting documents.", exception)
            }
    }


    /*fun getData(): ArrayList<Group> {
        val db = Firebase.firestore
        var groupArrayList: ArrayList<Group> = arrayListOf<Group>()
        db.collection("groups")
            .get()
            .addOnSuccessListener { result ->
                lateinit var eachElement: Group
                for (document in result) {
                    eachElement = Group(document.data["Name"].toString())
                    groupArrayList.add(eachElement)
                    Log.d(TAG, "after adding each element: "+groupArrayList)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents.", exception)
            }

        Log.d(TAG, "In datasource"+groupArrayList)
        return groupArrayList
    }*/
}