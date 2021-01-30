package com.onramp.vurzika.newsapp.ui.settings.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.snackbar.Snackbar
import com.onramp.vurzika.newsapp.R
import com.onramp.vurzika.newsapp.ui.settings.SettingsContract
import com.onramp.vurzika.newsapp.ui.settings.presenter.SettingsPresenter
import javax.inject.Inject

class SettingsFragment @Inject constructor(
        private val presenter: SettingsPresenter
) : PreferenceFragmentCompat(), SettingsContract.View {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_settings, rootKey)
    }

    // MVP

    override fun notifyAutomaticNewsUpdatesCheckScheduled() {
        Snackbar.make(requireView(), getString(R.string.message_automatic_news_updates_check_scheduled), Snackbar.LENGTH_LONG).show()
    }

    override fun notifyAutomaticNewsUpdatesCheckCancelled() {
        Snackbar.make(requireView(), getString(R.string.message_automatic_news_updates_check_cancelled), Snackbar.LENGTH_LONG).show()
    }

    // MVP: Generic Events Handling

    override fun onAttach(context: Context) {
        super.onAttach(context)

        presenter.onAttach(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.onViewCreated()
    }

    override fun onDestroy() {
        presenter.onDestroy()

        super.onDestroy()
    }
}