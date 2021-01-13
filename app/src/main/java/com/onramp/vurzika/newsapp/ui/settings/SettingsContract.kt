package com.onramp.vurzika.newsapp.ui.settings

import com.onramp.vurzika.newsapp.ui.base.mvp.BaseContract

class SettingsContract {

    interface View : BaseContract.View {
        fun notifyAutomaticNewsUpdatesCheckScheduled()

        fun notifyAutomaticNewsUpdatesCheckCancelled()
    }

    interface Presenter : BaseContract.Presenter<View>

}