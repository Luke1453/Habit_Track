package com.lake1453.habit_track.repos

import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lake1453.habit_track.model.UserInfo

object UserInfoRepo {

    private val db = FirebaseFirestore.getInstance()
    private val user = FirebaseAuth.getInstance().currentUser

    fun setUserInfo(userInfo: UserInfo): Task<Void> {
        val document = db.
        collection("Users").
        document(user!!.uid)

        return document.set(userInfo)
    }

//    fun fetchUserInfo(): UserInfo{
//        val docRef = db.collection("Users").document(user!!.uid)
//        docRef.get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
//                    document.toObject(UserInfo::class.java)
//
//                } else {
//                    Log.d(TAG, "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d(TAG, "get failed with ", exception)
//            }
//    }
}