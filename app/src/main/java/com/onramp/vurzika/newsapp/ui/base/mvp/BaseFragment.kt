package com.onramp.vurzika.newsapp.ui.base.mvp

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

/**
 * MVP: Base fragment that automatically links presenter's and fragment's lifecycle events
 */
abstract class BaseFragment<T : BaseContract.View> : Fragment() {

    protected abstract fun getPresenter(): BaseContract.Presenter<T>

    override fun onAttach(context: Context) {
        super.onAttach(context)

        @Suppress("UNCHECKED_CAST")
        getPresenter().onAttach(this as T)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getPresenter().onViewCreated()
    }

    override fun onDestroy() {
        getPresenter().onDestroy()

        super.onDestroy()
    }
}