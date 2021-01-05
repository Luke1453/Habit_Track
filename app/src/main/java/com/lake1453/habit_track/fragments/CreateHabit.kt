package com.lake1453.habit_track.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.lake1453.habit_track.R
import com.lake1453.habit_track.dialogs.EmptyFieldsDialog
import com.lake1453.habit_track.model.Habit
import com.lake1453.habit_track.viewModel.HabitViewModel
import kotlinx.android.synthetic.main.dialog_edit_habit.view.*
import kotlinx.android.synthetic.main.dialog_edit_habit.view.type_dropdown
import kotlinx.android.synthetic.main.fragment_create_habit.view.*
import java.text.SimpleDateFormat
import java.util.*

class CreateHabit : Fragment() {

    private lateinit var viewModel: HabitViewModel
    private lateinit var nameEditText: EditText
    private lateinit var descEditText: EditText
    private lateinit var startTimeTextView: TextView
    private lateinit var startTime: Date
    private lateinit var repeatCheckBox: CheckBox
    private lateinit var repeatPeriodSpinner: Spinner
    private lateinit var typeSpinner: Spinner

    val sdf = SimpleDateFormat.getDateTimeInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_create_habit, container, false)
        viewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        //setting data vals
        nameEditText = view.findViewById(R.id.habit_name)
        descEditText = view.findViewById(R.id.habit_desc)
        startTimeTextView = view.findViewById(R.id.date_time_view)
        repeatCheckBox = view.findViewById(R.id.habit_repeat_check)
        repeatPeriodSpinner = view.findViewById(R.id.repeat_dropdown)
        typeSpinner = view.type_dropdown

        val currentDate = sdf.format(Date())

        startTimeTextView.text = "Date: " + currentDate
        startTime = Date()

        // button handling
        val cancelBtn: Button = view.findViewById(R.id.create_habit_cancel_btn)
        val okBtn: Button = view.findViewById(R.id.create_habit_ok_btn)
        val pickTimeBtn: Button = view.findViewById(R.id.set_date_time_btn_create_habit)

        cancelBtn.setOnClickListener { returnToViewFrag() }
        pickTimeBtn.setOnClickListener { pickDateTime() }
        okBtn.setOnClickListener {
            val name: String = nameEditText.text.toString()
            val desc: String = descEditText.text.toString()
            if (name.isNotBlank() && desc.isNotBlank()) {
                createHabit()
            } else {
                val emptyFieldsDialog = EmptyFieldsDialog()
                emptyFieldsDialog.show(activity?.supportFragmentManager!!, "emptyFields")
            }
        }

        return view
    }

    private fun returnToViewFrag() {
        val viewHabitFragment = ViewHabits()
        val fragmentManager = parentFragmentManager
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, viewHabitFragment).commit()
    }

    private fun pickDateTime() {
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)

        DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, day ->
            TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, month, day, hour, minute)
                startTime = pickedDateTime.time
                startTimeTextView.text = "Date: " + sdf.format(startTime)
            }, startHour, startMinute, true).show()
        }, startYear, startMonth, startDay).show()
    }

    private fun createHabit() {
        val id: Long = Random().nextLong()
        val name: String = nameEditText.text.toString()
        val desc: String = descEditText.text.toString()
        val startTime: Date = this.startTime
        val repeatable: Boolean = repeatCheckBox.isChecked
        val repeatPeriod: Int = repeatPeriodSpinner.selectedItemId.toInt() + 1
        val habitType: Int = typeSpinner.selectedItemId.toInt() +1

        val habit = Habit(id, name, desc, startTime, repeatable, repeatPeriod, habitType)
        viewModel.createHabit(habit)
        returnToViewFrag() //may need to comment this line
    }

}