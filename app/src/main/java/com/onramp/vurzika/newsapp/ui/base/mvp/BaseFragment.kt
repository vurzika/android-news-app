package com.onramp.vurzika.newsapp.ui.base.mvp

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

/**
 * MVP: Base fragment that automatically links presenter's and fragment's lifecycle events
 */
abstract class BaseFragment<T : BaseContract.View> : Fragment() {

    protected lateinit var presenter: BaseContract.Presenter<T>

    protected abstract fun createPresenter(): BaseContract.Presenter<T>

    @Suppress("UNCHECKED_CAST")
    override fun onAttach(context: Context) {
        super.onAttach(context)

        presenter = createPresenter()
        presenter.onAttach(this as T)
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