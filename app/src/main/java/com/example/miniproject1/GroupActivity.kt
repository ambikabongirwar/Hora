package com.example.miniproject1

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.miniproject1.multiUser.ItemListener
import com.example.miniproject1.multiUser.adapters.GroupsAdapter
import com.example.miniproject1.multiUser.model.NamesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class GroupActivity : AppCompatActivity(), ItemListener {

    lateinit var namesAndEmailArrayList: ArrayList<NamesViewModel>
    lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth
    lateinit var email: String
    lateinit var name: String

    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var groupName: String
    private lateinit var adapter: GroupsAdapter
    var TAG = "GroupActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        groupName = intent.getStringExtra("groupName").toString()
        val groupNameTv = findViewById<TextView>(R.id.groupNametv)
        groupNameTv.text = groupName

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        user.let { email = user?.email.toString() }

        val recyclerview = findViewById<RecyclerView>(R.id.rvGroups)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)

        namesAndEmailArrayList = ArrayList()

        adapter = GroupsAdapter(namesAndEmailArrayList, this)

        recyclerview.adapter = adapter
        getData()

        val newMemberBtn: FloatingActionButton = findViewById(R.id.newParticipant)
        newMemberBtn.setOnClickListener{
            val getGroupNameDialogView = LayoutInflater.from(this).inflate(R.layout.simple_et,null)
            val builder = AlertDialog.Builder(this).setView(getGroupNameDialogView).setTitle("Enter Valid Email Address: ")
            val alertDialog = builder.show()
            getGroupNameDialogView.findViewById<Button>(R.id.createGroupWithNameBtn).setOnClickListener {
                val memberEmail = getGroupNameDialogView.findViewById<EditText>(R.id.etNewGroupName).text.toString()
                verifyAndAddMember(memberEmail);
                alertDialog.dismiss()
            }
        }

        swipeRefreshLayout = findViewById(R.id.container)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            val i = Intent(this, GroupActivity::class.java)
            i.putExtra("groupName", groupName)
            finish()
            overridePendingTransition(0, 0)
            startActivity(i)
            overridePendingTransition(0, 0)
        }
    }

    override fun onClicked(emailId: String) {
        val intent = Intent(this, EachMemberActivity::class.java)
        Log.d(TAG, "Clicked on a member email id: " + emailId)
        intent.putExtra("groupName", groupName)
        intent.putExtra("emailId", emailId)
        startActivity(intent)
    }

    fun onDeleteClicked(memberEmail: String) {
        //Toast.makeText(this, memberEmail + " deleted", Toast.LENGTH_SHORT).show()
        db = FirebaseFirestore.getInstance()
        var memberEmail = memberEmail.trim()

        //TODO("Optimize this code") - Boolean variable doesn't work due to async issues

        if (memberEmail == email) {
            //Toast.makeText(this, "This is your email", Toast.LENGTH_SHORT).show()
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Do you want to leave this group?")

            builder.setPositiveButton("Yes") { dialog, which ->
                db.collection("members")
                    .whereEqualTo("emailId",memberEmail)
                    .whereEqualTo("groupName", groupName)
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

                //Deleting from groups collection:
                db.collection("groups")
                    .whereEqualTo("name", groupName)
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            //Toast.makeText(this, "Document exists", Toast.LENGTH_SHORT).show()
                            db.collection("groups").document(document.id)
                                .update("participants", FieldValue.arrayRemove(memberEmail))
                                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                                .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.w(TAG, "Error getting documents.", exception)
                    }
            }

            builder.setNegativeButton("No") { dialog, which ->
                Toast.makeText(this,
                    "no", Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }
        else {
            //Deleting all documents in the group having email as the email of user to delete from members collection
            db.collection("members")
                .whereEqualTo("emailId", memberEmail)
                .whereEqualTo("groupName", groupName)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        //Toast.makeText(this, "Document exists", Toast.LENGTH_SHORT).show()
                        db.collection("members").document(document.id)
                            .delete()
                            .addOnSuccessListener {
                                Log.d(
                                    TAG,
                                    "DocumentSnapshot successfully deleted!"
                                )
                            }
                            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }

            //Deleting from groups collection:
            db.collection("groups")
                .whereEqualTo("name", groupName)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        //Toast.makeText(this, "Document exists", Toast.LENGTH_SHORT).show()
                        db.collection("groups").document(document.id)
                            .update("participants", FieldValue.arrayRemove(memberEmail))
                            .addOnSuccessListener {
                                Log.d(
                                    TAG,
                                    "DocumentSnapshot successfully deleted!"
                                )
                            }
                            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }
    }

    private fun getData() {
        db = FirebaseFirestore.getInstance()
        db.collection("groups")
            .whereEqualTo("name", groupName)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var participants = document.data["participants"].toString()
                    participants = participants.slice(1..participants.length - 2)
                    Log.d(TAG, "DocumentSnapshot Particpants data: ${participants}")
                    if (participants != "") {
                        for (member in participants.split(",")) {
                            var member = member.trim()
                            db.collection("users")
                                .whereEqualTo("Email Address", member)
                                .get()
                                .addOnSuccessListener { result ->
                                    for (document in result) {
                                        name = document.data["First Name"].toString() + " " + document.data["Last Name"].toString()
                                        //Toast.makeText(this, "" + name + ": " + member, Toast.LENGTH_SHORT).show()
                                        namesAndEmailArrayList.add(NamesViewModel(member, name))
                                    }
                                    adapter.notifyDataSetChanged()
                                }
                                .addOnFailureListener { exception ->
                                    Log.w(TAG, "Error getting documents.", exception)
                                }
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun verifyAndAddMember(memberEmail: String) {
        db = FirebaseFirestore.getInstance()
        //Checking if the email address is valid:
        db.collection("users").whereEqualTo("Email Address", memberEmail)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    //Checking if the person is already a part of the group:
                    db.collection("groups")
                        .whereEqualTo("name", groupName)
                        .get()
                        .addOnSuccessListener { result ->
                            for (document in result) {
                                db.collection("groups").document(document.id)
                                    .update("participants", FieldValue.arrayUnion(memberEmail))
                                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                                    .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.w(TAG, "Error getting documents.", exception)
                        }
                }
                else {
                    Toast.makeText(this, "User with email address " + memberEmail + " doesn't exist.", Toast.LENGTH_SHORT).show();
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}