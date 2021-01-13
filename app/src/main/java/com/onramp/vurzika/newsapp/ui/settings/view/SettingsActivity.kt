package com.onramp.vurzika.newsapp.ui.settings.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.onramp.vurzika.newsapp.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    @Inject
    lateinit var settingsFragment: SettingsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.settings, settingsFragment)
                    .commit()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Up Button

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}