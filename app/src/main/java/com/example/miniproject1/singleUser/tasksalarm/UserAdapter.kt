package com.example.miniproject1.singleUser.tasksalarm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.R
import com.google.firebase.firestore.model.mutation.Precondition.updateTime
import java.text.SimpleDateFormat
import java.util.*

class UserAdapter( val users: List<UserTable>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun getItemId(position: Int): Long {
        return users [position].id
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        var viewColorTag: View = itemView.findViewById(R.id.viewColorTag)
        var txtShowTitle: TextView = itemView.findViewById(R.id.txtShowTitle)
        var txtShowtask: TextView = itemView.findViewById(R.id.txtShowtask)
        var txtShowTime: TextView = itemView.findViewById(R.id.txtShowTime)
        var txtShowDate: TextView = itemView.findViewById(R.id.txtShowDate)

        fun bind(user: UserTable) {
            val colors = itemView.resources.getIntArray(R.array.random_color)
            val randomColor = colors[Random().nextInt(colors.size)]
            viewColorTag.setBackgroundColor(randomColor)
            txtShowTitle.text = user.title
            txtShowtask.text = user.description
            updateTime(user.time)
            updateDate(user.date)
        }

        fun updateTime(time: Long) {
            val myFormat = "hh:mm a"
            val sdf = SimpleDateFormat(myFormat)
            txtShowTime.text = sdf.format(Date(time))
        }

        fun updateDate(date: Long) {
            val myFormat = "EEE, d MMM yyyy"
            val sdf = SimpleDateFormat(myFormat)
            txtShowDate.text = sdf.format(Date(date))
        }
    }
}