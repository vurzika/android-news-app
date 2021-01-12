package com.onramp.vurzika.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.onramp.vurzika.newsapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigationComponentWithBottomNavigationView()
    }

    private fun setupNavigationComponentWithBottomNavigationView() {
        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_navigation)
                .setupWithNavController(navController)

    }

    // Navigation UI: support for navigating back via app back

    override fun onSupportNavigateUp(): Boolean {
        // handle back button press via navigation component
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(AppBarConfiguration(setOf(R.id.latestNewsFragment, R.id.offlineNewsFragment)))
                || super.onSupportNavigateUp()
    }
}