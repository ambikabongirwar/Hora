package com.example.miniproject1.multiUser

import android.accessibilityservice.GestureDescription
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.GroupActivity
import com.example.miniproject1.R
import com.example.miniproject1.multiUser.adapters.CustomAdapter
import com.example.miniproject1.multiUser.model.Group
import com.example.miniproject1.multiUser.model.ItemsViewModel
import com.example.miniproject1.multiUser.model.MembersAndTasksModel
import com.firebase.ui.auth.AuthUI.getApplicationContext
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MultiUserFragment1 : Fragment(), ItemListener {

    private lateinit var navController: NavController
    private lateinit var mAuth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    lateinit var email: String

    private lateinit var recyclerView: RecyclerView
    private lateinit var groupArrayList: ArrayList<ItemsViewModel>
    private lateinit var customAdapter: CustomAdapter
    var TAG = "MultiUserFragment1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_multi_user1, container, false)
        mAuth = FirebaseAuth.getInstance()
        db = Firebase.firestore
        groupArrayList = ArrayList()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val user = mAuth.currentUser
        user.let { email = user?.email.toString() }

        Log.d(TAG, "onViewCreated")
        val logOutBtn: ImageButton = view.findViewById(R.id.ibProfile)
        logOutBtn.setOnClickListener {
            navController.navigate(R.id.action_multiUserFragment1_to_profileFragment)
        }

        val newGroupBtn: FloatingActionButton = view.findViewById(R.id.newGroup)
        newGroupBtn.setOnClickListener{
            val getGroupNameDialogView = LayoutInflater.from(context).inflate(R.layout.simple_et,null)
            val builder = AlertDialog.Builder(context).setView(getGroupNameDialogView).setTitle("Enter Group Name: ")
            val alertDialog = builder.show()
            getGroupNameDialogView.findViewById<Button>(R.id.createGroupWithNameBtn).setOnClickListener {
                val groupName = getGroupNameDialogView.findViewById<EditText>(R.id.etNewGroupName).text.toString()
                addGroupName(groupName);
                Toast.makeText(context,
                    ""+groupName , Toast.LENGTH_SHORT).show()
                alertDialog.dismiss()
            }
        }

        val recyclerview = view.findViewById<RecyclerView>(R.id.rvGroupNames)

        recyclerview.layoutManager = LinearLayoutManager(view.context)
        customAdapter = CustomAdapter(groupArrayList, this)

        recyclerview.adapter = customAdapter

        eventChangeListener()


    }

    override fun onClicked(name: String) {
        var name = name.trim()
        //Toast.makeText(context, "abc" + name.slice((name.length-6)..name.length-1), Toast.LENGTH_SHORT).show()
        if (name.length > 6 && name.slice((name.length-6)..name.length-1) == "Delete") {
            Toast.makeText(context, name.slice(0..name.length-7) + " deleted", Toast.LENGTH_SHORT).show()
            //Deleting all tasks from members collection
            db.collection("members")
                .whereEqualTo("groupName", name.slice(0..name.length-7))
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        //Toast.makeText(this, "Document exists", Toast.LENGTH_SHORT).show()
                        db.collection("members").document(document.id)
                            .delete()
                            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }

            //Deleting the group from groups collection
            db.collection("groups")
                .whereEqualTo("name", name.slice(0..name.length-7))
                .whereArrayContains("participants", email)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        //Toast.makeText(this, "Document exists", Toast.LENGTH_SHORT).show()
                        db.collection("groups").document(document.id)
                            .delete()
                            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }
        else {
            val intent = Intent(context, GroupActivity::class.java)
            intent.putExtra("groupName", name)
            context?.startActivity(intent)
            //Toast.makeText(context, "Name is "+name, Toast.LENGTH_SHORT).show()
        }
    }

    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("groups")
            .whereArrayContainsAny("participants", listOf(email))
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    groupArrayList.add(ItemsViewModel(document.data["name"].toString()))
                    customAdapter.notifyDataSetChanged()
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("MultiUser Fragment", "Error getting documents.", exception)
            }
    }

    private fun addGroupName(GroupName: String) {
        db = FirebaseFirestore.getInstance()
        Log.d(TAG, "Email id of current user: " + email)
        val arr : ArrayList<String> = ArrayList()
        arr.add(email)
        val newGroup = Group(GroupName, arr)
        db.collection("groups")
            .add(newGroup)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                customAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
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