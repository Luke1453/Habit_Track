package com.lake1453.habit_track.repos

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.lake1453.habit_track.model.Habit

object HabitRepo {

    private val db = FirebaseFirestore.getInstance()
    private val user = FirebaseAuth.getInstance().currentUser

    fun addToHabits(habit: Habit): Task<Void> {
        val document = db.
        collection("Users").
        document(user!!.uid).
        collection("Habits").
        document("${habit.id}")

        return document.set(habit)
    }

    fun updateHabit(habit: Habit): Task<Void> {
        val document = db.
        collection("Users").
        document(user!!.uid).
        collection("Habits").
        document("${habit.id}")

        return document.set(habit)
    }

    fun getHabits(): CollectionReference {
        return db.collection("Users/${user!!.uid}/Habits")
    }

    fun removeHabit(habitID: Long): Task<Void> {
        val document = db.
        collection("Users/${user!!.uid}/Habits").
        document("$habitID")

        return document.delete()
    }
}