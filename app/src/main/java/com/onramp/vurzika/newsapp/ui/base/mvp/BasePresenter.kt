package com.onramp.vurzika.newsapp.ui.base.mvp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * MVP: Base Presenter class with coroutines support
 */
abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V>, CoroutineScope {

    protected var view: V? = null

    // Coroutines Support (to run on main thread)
    // allows every presenter to launch coroutine functions on their scope

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    // Presenter Lifecycle

    override fun onAttach(view: V) {
        this.view = view
    }

    override fun onViewCreated() {
    }

    override fun onDestroy() {
        view = null

        // cancel any running jobs
        job.cancel()
    }
}