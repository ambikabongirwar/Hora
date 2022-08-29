package com.example.miniproject1.singleUser.tasksalarm

import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.R
import com.example.miniproject1.singleUser.moodCheck.MoodCheckFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskListActivity: AppCompatActivity() {

    var users = arrayListOf<UserTable>()

    var adapter = UserAdapter(users)

    val db by lazy {
        AppDatabase.getDatabase(this)
    }

    lateinit var Rv: RecyclerView
    lateinit var btnadd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        Rv = findViewById(R.id.Rv)
        btnadd = findViewById(R.id.btnadd)
        Rv.apply {
            layoutManager = LinearLayoutManager(this@TaskListActivity)
            adapter = this@TaskListActivity.adapter
        }

        initSwipe()
        db.userDao().getTask().observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                users.clear()
                users.addAll(it)
                adapter.notifyDataSetChanged()
            }else{
                users.clear()
                adapter.notifyDataSetChanged()
            }
        })


        btnadd.setOnClickListener {
            val i = Intent( this, TaskCreationActivity::class.java)
            //Toast.makeText(this, "Inside add button", Toast.LENGTH_SHORT).show()
            startActivity(i)
        }
    }


    private fun initSwipe() {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.LEFT) {
                    GlobalScope.launch(Dispatchers.IO) {
                        db.userDao().deleteTask(adapter.getItemId(position))
                    }
                } else if (direction == ItemTouchHelper.RIGHT) {
                    GlobalScope.launch(Dispatchers.IO) {
                        db.userDao().FinishTask(adapter.getItemId(position))
                    }
                }
            }

            override fun onChildDraw(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView

                    val paint = Paint()
                    val icon: Bitmap

                    if (dX > 0) {

                        icon = BitmapFactory.decodeResource(
                            resources,
                            R.drawable.baseline_check_black_18dp
                        )

                        paint.color = Color.parseColor("#388E3C")

                        canvas.drawRect(
                            itemView.left.toFloat(), itemView.top.toFloat(),
                            itemView.left.toFloat() + dX, itemView.bottom.toFloat(), paint
                        )

                        canvas.drawBitmap(
                            icon,
                            itemView.left.toFloat(),
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                            paint
                        )


                    } else {
                        icon = BitmapFactory.decodeResource(
                            resources,
                            R.drawable.baseline_delete_outline_black_18dp
                        )

                        paint.color = Color.parseColor("#D32F2F")

                        canvas.drawRect(
                            itemView.right.toFloat() + dX, itemView.top.toFloat(),
                            itemView.right.toFloat(), itemView.bottom.toFloat(), paint
                        )

                        canvas.drawBitmap(
                            icon,
                            itemView.right.toFloat() - icon.width,
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                            paint
                        )
                    }
                    viewHolder.itemView.translationX = dX


                } else {
                    super.onChildDraw(
                        canvas,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }


        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(Rv)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu,menu)

        val item = menu.findItem(R.id.search)
        val searchView = item.actionView as SearchView
        item.setOnActionExpandListener(object :MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                displayTodo()
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                displayTodo()
                return true
            }

        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(!newText.isNullOrEmpty()){
                    displayTodo(newText)
                }
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.moodcheck -> {
                val intent = Intent(this@TaskListActivity, MoodCheckFragment::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun displayTodo(newText: String = "") {
        db.userDao().getTask().observe(this, Observer {
            if(it.isNotEmpty()){
                users.clear()
                users.addAll(
                    it.filter { todo ->
                        todo.title.contains(newText,true)
                    }
                )
                adapter.notifyDataSetChanged()
            }
        })
    }


    fun openNewTask(view: View) {
        startActivity(Intent(this, TaskCreationActivity::class.java))
    }

}