package com.onramp.vurzika.newsapp.ui.base.mvp

/**
 * MVP: Base contract for defining View / Presenter interaction
 */
class BaseContract {

    interface View

    interface Presenter<V : View> {
        fun onAttach(view: V)
        fun onViewCreated()
        fun onDestroy()
    }
}