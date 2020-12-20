package com.lake1453.habit_track

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.drawerlayout.widget.DrawerLayout
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setting up the navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        drawerLayout = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        navigationView.setupWithNavController(navController)

        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    // overriding fun to access menu items from drawer
    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}