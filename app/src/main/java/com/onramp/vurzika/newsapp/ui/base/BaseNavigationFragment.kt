package com.onramp.vurzika.newsapp.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.onramp.vurzika.newsapp.R
import com.onramp.vurzika.newsapp.ui.base.mvp.BaseContract
import com.onramp.vurzika.newsapp.ui.base.mvp.BaseFragment

/**
 * Fragment responsible for handling common logic for navigation component integration
 */
abstract class BaseNavigationFragment<T : BaseContract.View> : BaseFragment<T>() {

    /**
     * Provides reference to fragment's toolbar that should be used as main
     * toolbar for currently displayed fragment in navigation component
     *
     * This is required to correctly display toolbar specific elements such as options menu
     */
    protected abstract fun getToolbar(): Toolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // connect fragment's toolbar to navigation graph
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.latestNewsFragment, R.id.offlineNewsFragment))

        getToolbar()
                .setupWithNavController(navController, appBarConfiguration)
    }

    override fun onResume() {
        super.onResume()

        // use fragment's toolbar as activity's main toolbar to populate menu
        (requireActivity() as AppCompatActivity).setSupportActionBar(getToolbar())
    }
}