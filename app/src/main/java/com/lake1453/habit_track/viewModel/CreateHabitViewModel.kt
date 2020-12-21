package com.lake1453.habit_track.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.lake1453.habit_track.model.Habit
import com.lake1453.habit_track.repos.HabitRepo

class CreateHabitViewModel : ViewModel() {
    private val habitRepo = HabitRepo

    companion object {
        private val TAG: String? = CreateHabitViewModel::class.simpleName
    }

    init {
        Log.i(TAG, "GameViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }

    fun createHabit(habitData: Habit) {
        Log.i("GameViewModel", habitData.toString())
        habitRepo.addToHabits(habitData).addOnSuccessListener {
            Log.w(TAG, "Habit added to DB")
        }.addOnFailureListener{
            Log.w(TAG, "Habit not added to DB: $it")
        }

    }

}