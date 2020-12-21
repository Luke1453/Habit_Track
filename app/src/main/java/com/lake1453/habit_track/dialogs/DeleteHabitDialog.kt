package com.lake1453.habit_track.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.lake1453.habit_track.R
import com.lake1453.habit_track.viewModel.HabitViewModel
import kotlinx.android.synthetic.main.dialog_delete_habit.view.*

class DeleteHabitDialog(private val habitID: Long) : DialogFragment() {
    private lateinit var viewModel: HabitViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.dialog_delete_habit, container, false)

        viewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        view.cancel_button.setOnClickListener {
            dismiss()
        }
        view.ok_button.setOnClickListener {
            viewModel.removeHabit(habitID)
            dismiss()
        }

        return view
    }
}