package com.onramp.vurzika.newsapp.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.onramp.vurzika.newsapp.R

// Special Lifecycle Observer that connects provided toolbar
// with navigation fragment to allow having different toolbars per view
class NavigationToolbarObserver(
        var activity: AppCompatActivity?,
        var toolbar: Toolbar?
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        toolbar?.setupWithNavController(
                activity?.findNavController(R.id.nav_host_fragment)!!,
                AppBarConfiguration(setOf(R.id.latestNewsFragment, R.id.offlineNewsFragment))
        )
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        // make current toolbar as active
        activity?.setSupportActionBar(toolbar)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)

        // release references on destroy
        activity = null
        toolbar = null
    }
}