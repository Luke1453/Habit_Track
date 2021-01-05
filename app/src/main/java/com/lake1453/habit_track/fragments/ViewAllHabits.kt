package com.lake1453.habit_track.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lake1453.habit_track.R
import com.lake1453.habit_track.adapters.AllHabitsAdapter
import com.lake1453.habit_track.dialogs.EditHabitDialog
import com.lake1453.habit_track.model.Habit
import com.lake1453.habit_track.viewModel.HabitViewModel
import kotlinx.android.synthetic.main.fragment_view_all_habits.*

class ViewAllHabits : Fragment(), AllHabitsAdapter.Interaction {

    private lateinit var viewModel: HabitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_all_habits, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        val allHabitsAdapter: AllHabitsAdapter by lazy { AllHabitsAdapter(this) }


        viewModel.getHabitList().observe(viewLifecycleOwner, {
            allHabitsAdapter.submitList(it)
        })

        habit_recycleView.apply {
            layoutManager = LinearLayoutManager(context)
            // Set the custom adapter to the RecycleView
            adapter = allHabitsAdapter

            allHabitsAdapter.onItemClick = { habit ->
                val editHabitDialog = EditHabitDialog(habit)
                editHabitDialog.show(activity?.supportFragmentManager!!, "Delete Habit")

                // updating recycle view
                viewModel.getHabitList().observe(viewLifecycleOwner, {
                    allHabitsAdapter.swapData(it)
                })
            }

        }

    }

}