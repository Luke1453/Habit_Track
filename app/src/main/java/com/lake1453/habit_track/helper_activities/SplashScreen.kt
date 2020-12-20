package com.lake1453.habit_track.helper_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.lake1453.habit_track.MainActivity
import com.lake1453.habit_track.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Hides actionBar
        supportActionBar?.hide()

        // redirect after 2 sec
        Handler(Looper.getMainLooper()).postDelayed({
            val LogIntent = Intent(this, LogIn::class.java)
            startActivity(LogIntent)
            finish()
        }, 2000)
    }
}