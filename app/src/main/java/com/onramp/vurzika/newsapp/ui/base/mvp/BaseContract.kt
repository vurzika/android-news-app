package com.onramp.vurzika.newsapp.ui.base.mvp

class BaseContract {
    interface View {
    }

    interface Presenter<V : View> {
        fun onAttach(view: V)
        fun onViewCreated()
        fun onDestroy()
    }
}