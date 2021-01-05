package com.lake1453.habit_track.dialogs

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.text.set
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.lake1453.habit_track.R
import com.lake1453.habit_track.model.Habit
import com.lake1453.habit_track.viewModel.HabitViewModel
import kotlinx.android.synthetic.main.dialog_edit_habit.view.*
import kotlinx.android.synthetic.main.dialog_edit_habit.view.habit_desc
import kotlinx.android.synthetic.main.dialog_edit_habit.view.habit_name
import kotlinx.android.synthetic.main.habit_list_tile.view.*
import java.text.SimpleDateFormat
import java.util.*

class EditHabitDialog(private val habit: Habit) : DialogFragment() {
    private lateinit var viewModel: HabitViewModel

    private lateinit var nameEditText: EditText
    private lateinit var descEditText: EditText
    private lateinit var startTimeTextView: TextView
    private lateinit var repeatCheckBox: CheckBox
    private lateinit var repeatPeriodSpinner: Spinner

    private var wasDateChanged: Boolean = false
    private lateinit var newDate: Date
    val sdf = SimpleDateFormat.getDateTimeInstance()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.dialog_edit_habit, container, false)

        viewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        //naming fields
        nameEditText = view.habit_name
        descEditText = view.habit_desc
        startTimeTextView = view.date_time_view
        repeatCheckBox = view.habit_repeat_check
        repeatPeriodSpinner = view.repeat_dropdown

        // Populating fields
        nameEditText.setText(habit.name)
        descEditText.setText(habit.description)
        repeatCheckBox.isChecked = habit.repeatable
        repeatPeriodSpinner.setSelection(habit.repeatPeriod - 1)

        // dealing with date
        startTimeTextView.text = "Date: " + sdf.format(habit.startTime)

        view.edit_habit_cancel_btn.setOnClickListener {
            dismiss()
        }
        view.edit_habit_save_btn.setOnClickListener {
            updateHabit()
        }
        view.edit_habit_delete_btn.setOnClickListener {
            viewModel.removeHabit(habit.id)
            dismiss()
        }
        view.set_date_time_btn_create_habit.setOnClickListener {
            pickDateTime()
        }

        return view
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
                newDate = pickedDateTime.time
                startTimeTextView.text = "Date: " + sdf.format(newDate)
                wasDateChanged = true
            }, startHour, startMinute, true).show()
        }, startYear, startMonth, startDay).show()
    }

    private fun updateHabit(){
        habit.name = nameEditText.text.toString()
        habit.description = descEditText.text.toString()
        habit.repeatable = repeatCheckBox.isChecked == true
        habit.repeatPeriod = repeatPeriodSpinner.selectedItemId.toInt() + 1

        if (wasDateChanged){
            habit.startTime = newDate
        }

        viewModel.updateHabit(habit)
        dismiss()
    }
}