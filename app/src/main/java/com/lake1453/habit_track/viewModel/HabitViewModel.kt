package com.lake1453.habit_track.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lake1453.habit_track.model.Habit
import com.lake1453.habit_track.repos.HabitRepo
import org.joda.time.DateTime
import org.joda.time.Days
import java.util.*
import javax.security.auth.callback.Callback

class HabitViewModel : ViewModel() {
    private val habitRepo = HabitRepo
    val habitList: MutableLiveData<List<Habit>> = MutableLiveData()

    companion object {
        private val TAG: String? = HabitViewModel::class.simpleName
    }

    init {
        Log.i(TAG, "HabitViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "HabitViewModel destroyed!")
    }

    fun createHabit(habitData: Habit) {
        Log.i(TAG, habitData.toString())
        habitRepo.addToHabits(habitData).addOnSuccessListener {
            Log.w(TAG, "Habit added to DB")
        }.addOnFailureListener {
            Log.w(TAG, "Habit not added to DB: $it")
        }
    }

    fun updateHabit(habitData: Habit) {
        Log.i(TAG, habitData.toString())
        habitRepo.updateHabit(habitData).addOnSuccessListener {
            Log.w(TAG, "Habit updated in DB")
        }.addOnFailureListener {
            Log.w(TAG, "Habit not updated in DB: $it")
        }
    }

    fun getHabitList(): LiveData<List<Habit>> {
        habitRepo.getHabits().addSnapshotListener { value, _ ->
            val tempHabitList: MutableList<Habit> = mutableListOf()
            value!!.forEach { document ->
                val habit = document.toObject(Habit::class.java)
                tempHabitList.add(habit)
            }
            habitList.value = tempHabitList
        }

        return habitList
    }

    fun getHabitListByDay(reqDate: Date): LiveData<List<Habit>> {
        habitRepo.getHabits().addSnapshotListener { value, _ ->
            val tempHabitList: MutableList<Habit> = mutableListOf()
            value!!.forEach { document ->
                val habit = document.toObject(Habit::class.java)
                if (checkHabitDate(reqDate, habit)) {
                    tempHabitList.add(habit)
                }
            }
            habitList.value = tempHabitList
        }
        return habitList
    }

    fun removeHabit(habitID: Long) {

        habitRepo.removeHabit(habitID).addOnFailureListener {
            Log.w(TAG, "Failed to remove habit: $it")
        }
    }

    private fun checkHabitDate(requestDate: Date, habit: Habit): Boolean {
        val reqDate = trimDate(requestDate)
        val habitDate = trimDate(habit.startTime)

        println(reqDate.toString())
        println(habitDate.toString())
        println(habit.toString())

        if (!habit.repeatable) {
            return reqDate == habitDate
        } else {

            val daysBetween = Days.daysBetween(habitDate, reqDate).days
            Log.d(TAG, daysBetween.toString())

            return when {
                daysBetween == 0 -> {
                    true
                }
                daysBetween < 0 -> {
                    false
                }
                else -> {
                    daysBetween % habit.repeatPeriod == 0
                }
            }
        }
    }


    private fun trimDate(date: Date): DateTime {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return DateTime(calendar.time)

    }

}