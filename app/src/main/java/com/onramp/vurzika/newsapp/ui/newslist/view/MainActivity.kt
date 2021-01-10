package com.onramp.vurzika.newsapp.ui.newslist.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.onramp.vurzika.newsapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // Navigation UI: support for navigating back via app back

    override fun onSupportNavigateUp(): Boolean {
        // handle back button press via navigation component
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(AppBarConfiguration(navController.graph))
                || super.onSupportNavigateUp()
    }
}