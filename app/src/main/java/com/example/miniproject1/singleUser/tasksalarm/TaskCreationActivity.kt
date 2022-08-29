package com.example.miniproject1.singleUser.tasksalarm

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.miniproject1.R
import com.example.miniproject1.databinding.ActivityTaskCreationBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

const val DB_NAME = "todo.db"
class TaskCreationActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var myCalendar: Calendar

    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    lateinit var timeSetListener: TimePickerDialog.OnTimeSetListener

    lateinit var dateEdt: TextInputEditText;
    lateinit var timeEdt: TextInputEditText;
    lateinit var btnsave: Button;
    lateinit var spinnerCategory: Spinner;
    lateinit var edit_text_title: TextInputEditText;
    lateinit var edit_text_note: TextInputEditText;
    lateinit var timeInptLay: TextInputLayout;

    var finalDate = 0L
    var finalTime = 0L

    private val labels = arrayListOf("Personal", "Business", "Shopping", "BirthDay", "Reminder")

    val db by lazy {
        AppDatabase.getDatabase(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_creation)

        dateEdt = findViewById(R.id.dateEdt)
        timeEdt = findViewById(R.id.timeEdt)
        btnsave = findViewById(R.id.btnsave)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        edit_text_title = findViewById(R.id.edit_text_title)
        edit_text_note = findViewById(R.id.edit_text_note)
        timeInptLay = findViewById(R.id.timeInptLay)

        dateEdt.setOnClickListener(this)
        timeEdt.setOnClickListener(this)
        btnsave.setOnClickListener(this)

        setUpSpinner()

    }

    private fun setUpSpinner() {
        val adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, labels)
        labels.sort()
        spinnerCategory.adapter = adapter

    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.dateEdt -> {
                setListener()
            }
            R.id.timeEdt -> {
                setTimeListener()
            }
            R.id.btnsave -> {
                saveTodo()
            }
        }
    }

    private fun saveTodo() {


        var month = 0
        var dayofmonth = 0
        var year = 0
        var hourOfDay = 0
        var min = 0

        val category = spinnerCategory.selectedItem.toString()
        val title = edit_text_title.text.toString()
        val description = edit_text_note.text.toString()
        val date = dateEdt.text.toString()
        val time = timeEdt.text.toString()

        if (title.isEmpty()) {
            edit_text_title.error = "Title required"
        }
        if (description.isEmpty()) {
            edit_text_note.error = "Note required"
        }
        if (date.isEmpty()){
            dateEdt.error = "Date required"
        }
        if (time.isEmpty()) {
            timeEdt.error = "Time required"
        }


        else {


            GlobalScope.launch(Dispatchers.Main) {
                val id = withContext(Dispatchers.IO) {

                    return@withContext db.userDao().insertTask(
                        UserTable(
                            category,
                            title,
                            description,
                            finalDate,
                            finalTime
                        )
                    )
                }

                Log.d("Alarm Title", "$month , $finalDate : ${myCalendar.time}")
                id?.let {
                    setAlarm( myCalendar,0, it, title,hourOfDay, min) //hourOfDay, min
                }
            }
            finish()

        }
    }

    fun setTimeListener() {

        myCalendar = Calendar.getInstance()

        timeSetListener =
            TimePickerDialog.OnTimeSetListener() { _: TimePicker, hourOfDay: Int, min: Int ->
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                myCalendar.set(Calendar.MINUTE, min)
                updateTime()

            }

        val timePickerDialog = TimePickerDialog(
            this, timeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY),
            myCalendar.get(Calendar.MINUTE), false
        )

        timePickerDialog.show()

    }

    fun updateTime() { //private fun
        val myFormat = "hh:mm a"
        val sdf = SimpleDateFormat(myFormat)
        finalTime = myCalendar.time.time
        timeEdt.setText(sdf.format(myCalendar.time))

    }

    private fun setListener() {
        myCalendar = Calendar.getInstance()

        dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayofmonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth)
            updateDate()

        }

        val datePickerDialog = DatePickerDialog(
            this, dateSetListener, myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun updateDate() {
        val myFormat = "EEE, d MMM yyyy"
        val sdf = SimpleDateFormat(myFormat)
        dateEdt.setText(sdf.format(myCalendar.time))
        finalDate = myCalendar.time.time
        timeInptLay.visibility = View.VISIBLE
    }

    fun setAlarm(myCalendar: Calendar, i: Int, id: Long, title: String,hourOfDay:Int, min:Int ) {


        val alarmManager: AlarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra("INTENT_NOTIFY", true)
        intent.putExtra("isShow", i)
        intent.putExtra("id", id)
        intent.putExtra("title", title)

        val pandingIntent: PendingIntent = PendingIntent.getBroadcast(this, (0..2147483647).random(), intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,  myCalendar.timeInMillis , pandingIntent)
        }

    }
}