package com.example.miniproject1.multiUser.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.R
import com.example.miniproject1.multiUser.ItemListener
import com.example.miniproject1.multiUser.MultiUserFragment1
import com.example.miniproject1.multiUser.model.GroupsPageViewModel

class CustomAdapter(private val groupsList: List<GroupsPageViewModel>, val listener : MultiUserFragment1) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.group_card_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(groupsList[position].text, groupsList[position].delete, listener)

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return groupsList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        val deleteBtn: ImageButton = itemView.findViewById(R.id.ibDelete)
        fun bind(groupName: String, delete: Boolean, listener: ItemListener) {
            textView.text = groupName
            textView.setOnClickListener{itemView -> listener.onClicked(groupName)}
            deleteBtn.setOnClickListener{itemView -> listener.onClicked(groupName + "Delete")}
        }
    }
}
