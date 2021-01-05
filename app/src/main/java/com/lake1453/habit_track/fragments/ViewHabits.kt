package com.lake1453.habit_track.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lake1453.habit_track.R
import com.lake1453.habit_track.adapters.AllHabitsAdapter
import com.lake1453.habit_track.viewModel.HabitViewModel
import kotlinx.android.synthetic.main.fragment_view_all_habits.habit_recycleView
import kotlinx.android.synthetic.main.fragment_view_habits.*
import kotlinx.android.synthetic.main.habit_list_tile.view.*
import java.text.SimpleDateFormat
import java.util.*

class ViewHabits : Fragment(), AllHabitsAdapter.Interaction {

    private lateinit var viewModel: HabitViewModel
    private lateinit var allHabitsAdapter: AllHabitsAdapter

    private lateinit var typeDropdown: Spinner

    private var date: Date = Date()
    private var type: Int = -1;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_habits, container, false)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val createFloatingBtn: FloatingActionButton = view.findViewById(R.id.createNew)
        createFloatingBtn.setOnClickListener {
            val createHabitFragment = CreateHabit()
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, createHabitFragment).commit()
        }

        viewModel = ViewModelProvider(this).get(HabitViewModel::class.java)
        allHabitsAdapter = AllHabitsAdapter(this)

        typeDropdown = view.findViewById(R.id.type_dropdown)
        typeDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                type = -1
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val typeArray = context?.resources?.let { arrayOf<String>(*it.getStringArray(R.array.habit_types)) }

                type = typeDropdown.selectedItemId.toInt() + 1

                viewModel.getHabitListByDayAndType(date, type).observe(viewLifecycleOwner, {
                    allHabitsAdapter.swapData(it)
                })
            }

        }

        viewModel.getHabitListByDayAndType(date, type).observe(viewLifecycleOwner, {
            allHabitsAdapter.swapData(it)
        })

        habit_recycleView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = allHabitsAdapter
        }



        set_date_time_btn_today_habit.setOnClickListener {

            val currentDateTime = Calendar.getInstance()
            val startYear = currentDateTime.get(Calendar.YEAR)
            val startMonth = currentDateTime.get(Calendar.MONTH)
            val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, day ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, month, day)
                date = pickedDateTime.time

                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val currentDate = sdf.format(date)
                date_time_view_today_habit.text = "Date: $currentDate"



                viewModel.getHabitListByDayAndType(date, type).observe(viewLifecycleOwner, {
                    allHabitsAdapter.swapData(it)
                })
            }, startYear, startMonth, startDay).show()


        }

        fun updateRecycleView(){

            viewModel.getHabitListByDayAndType(date, type).observe(viewLifecycleOwner, {
                allHabitsAdapter.swapData(it)
            })

        }
    }
}