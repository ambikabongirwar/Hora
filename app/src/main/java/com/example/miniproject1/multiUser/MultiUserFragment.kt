package com.example.miniproject1.multiUser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MultiUserFragment : Fragment(){

    lateinit var navController: NavController
    lateinit var mAuth: FirebaseAuth
    private var TAG = "Multi User Fragment"

    private lateinit var recyclerView: RecyclerView
    //private lateinit var groupArrayList: ArrayList<Groups>
    //private lateinit var groupCardAdapter: GroupCardAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_multi_user, container, false)
        mAuth = FirebaseAuth.getInstance();
        db = Firebase.firestore
        val logOutBtn: Button =view.findViewById(R.id.ibLogOut)
        /*logOutBtn.setOnClickListener{
            val fragment = RegisterFragment()
            val transaction = getFragmentManag fragmentManager?.beginTransaction()
            transaction?.replace(R.id.multiUserFragment, fragment)?.commit()
            //Navigation.findNavController(view).navigate(R.id.action_multiUserFragment_to_registerFragment)
        }*/
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        mAuth = FirebaseAuth.getInstance();
        db = Firebase.firestore
        val currentUser = mAuth.currentUser
        val logOutBtn: ImageButton = view.findViewById(R.id.ibLogOut)
        logOutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            Navigation.findNavController(view).navigate(R.id.action_multiUserFragment_to_registerFragment)
        }
        //view.findViewById<ImageButton>(R.id.ibLogOut).setOnClickListener(this)
        //view.findViewById<RecyclerView>(R.id.groupsRecyclerView).setOnClickListener(this)
        updateUI(view, currentUser)
    }

    fun updateUI(view: View, currentUser: FirebaseUser?) {
        /*recyclerView = view.findViewById(R.id.groupsRecyclerView)
        groupArrayList = arrayListOf()
        groupCardAdapter = GroupCardAdapter(groupArrayList)
        recyclerView.adapter = groupCardAdapter*/

        Log.d("MultiUserFragment", "inside group adapter0")
        //eventChangeListener()

        /*groupCardAdapter.setOnItemClickListener(object : GroupCardAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Log.d("MultiUserFragment", "inside group adapter")
                Toast.makeText(context, "You clicked on item number $position",Toast.LENGTH_SHORT).show()
            }
        })*/
    }

    /*override fun onClick(view: View?) {
        when(view?.id) {
            R.id.ibLogOut -> logoutAndRedirect()
        }
    }

    fun logoutAndRedirect() {
        Log.d("MultiUserFragment", "inside logout and redirect")
        FirebaseAuth.getInstance().signOut();
        navController.navigate(R.id.action_multiUserFragment_to_registerFragment)
    }*/

    /*fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("groups").
                addSnapshotListener(object: EventListener<QuerySnapshot>{
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        if (error != null) {
                            Log.e("Firebase error", error.message.toString())
                            return
                        }
                        for (dc: DocumentChange in value?.documentChanges!!) {
                            if(dc.type == DocumentChange.Type.ADDED) {
                                groupArrayList.add(dc.document.toObject(Groups::class.java))
                            }
                        }
                        groupCardAdapter.notifyDataSetChanged()
                    }
                })
        db.collection("groups")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    groupArrayList.add(Groups(document.data["Name"].toString()))
                    groupCardAdapter.notifyDataSetChanged()
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("MultiUser Fragment", "Error getting documents.", exception)
            }

        val docRef = db.collection("groups")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            val source = if (snapshot != null && snapshot.metadata.hasPendingWrites())
                "Local"
            else
                "Server"

            if (snapshot != null) {
                Log.d(TAG, "$source data: ${snapshot.documents}")
            } else {
                Log.d(TAG, "$source data: null")
            }
        }
    }*/
}