package com.onramp.vurzika.newsapp.ui.base.mvp

abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {
    protected var view: V? = null

    override fun onAttach(view: V) {
        this.view = view
    }

    override fun onViewCreated() {
    }

    override fun onDestroy() {
        view = null
    }
}