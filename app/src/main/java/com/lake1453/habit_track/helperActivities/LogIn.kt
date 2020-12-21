package com.lake1453.habit_track.helperActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.lake1453.habit_track.MainActivity
import com.lake1453.habit_track.R

class LogIn : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        Log.d("LogInActivity", "Starting Firebase Auth")
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({
            if (user != null){
                val mainActivityIntent = Intent(this, MainActivity::class.java)
                startActivity(mainActivityIntent)
                finish()
            }else{
                val singInIntent = Intent(this, SignInActivity::class.java)
                startActivity(singInIntent)
                finish()
            }
        }, 0)
    }
}