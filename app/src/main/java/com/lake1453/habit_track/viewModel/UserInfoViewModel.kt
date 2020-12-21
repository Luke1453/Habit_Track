package com.lake1453.habit_track.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.lake1453.habit_track.model.UserInfo
import com.lake1453.habit_track.repos.UserInfoRepo

class UserInfoViewModel : ViewModel() {
    private val userInfoRepo = UserInfoRepo

    companion object {
        private val TAG: String? = UserInfoViewModel::class.simpleName
    }

    init {
        Log.i(TAG, "ViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "ViewModel destroyed!")
    }

    fun setUserInfo(userInfo: UserInfo) {
        Log.i(TAG, userInfo.toString())
        userInfoRepo.setUserInfo(userInfo).addOnSuccessListener {
            Log.w(TAG, "UserInfo added to DB")
        }.addOnFailureListener {
            Log.w(TAG, "UserInfo not added to DB: $it")
        }
    }



}