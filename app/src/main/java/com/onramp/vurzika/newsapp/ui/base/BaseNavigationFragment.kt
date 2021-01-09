package com.onramp.vurzika.newsapp.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.onramp.vurzika.newsapp.ui.base.mvp.BaseContract
import com.onramp.vurzika.newsapp.ui.base.mvp.BaseFragment

// Fragment responsible for handling navigation component integration
abstract class BaseNavigationFragment<T : BaseContract.View>() : BaseFragment<T>() {

    protected abstract fun getToolbar(): Toolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // connect fragment's toolbar to navigation graph
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        getToolbar()
                .setupWithNavController(navController, appBarConfiguration)
    }

    override fun onResume() {
        super.onResume()

        // Navigation: use fragment's toolbar as activity's main toolbar to populate menu
        (requireActivity() as AppCompatActivity).setSupportActionBar(getToolbar())
    }
}