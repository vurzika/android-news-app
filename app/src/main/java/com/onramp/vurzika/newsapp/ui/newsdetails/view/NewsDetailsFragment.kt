package com.onramp.vurzika.newsapp.ui.newsdetails.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.onramp.vurzika.newsapp.R
import com.onramp.vurzika.newsapp.databinding.FragmentNewsDetailsBinding
import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.ui.base.BaseNavigationFragment
import com.onramp.vurzika.newsapp.ui.base.mvp.BaseContract
import com.onramp.vurzika.newsapp.ui.newsdetails.NewsDetailsContract
import com.onramp.vurzika.newsapp.ui.newsdetails.presenter.NewsDetailsPresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class NewsDetailsFragment : BaseNavigationFragment<NewsDetailsContract.View>(), NewsDetailsContract.View {

    private lateinit var binding: FragmentNewsDetailsBinding

    @Inject
    lateinit var presenter: NewsDetailsPresenter

    private val args: NewsDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false)

        presenter.newsArticleId = args.newsArticleId

        setHasOptionsMenu(true)

        binding.fab.setOnClickListener {
            presenter.onSetFavoriteSelected()
        }

        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->

            (binding.fab.layoutParams as CoordinatorLayout.LayoutParams).anchorGravity =
                    if (binding.appBar.height + verticalOffset - binding.fab.height / 4 < binding.toolbar.height) {
                        Gravity.BOTTOM or Gravity.END
                    } else {
                        Gravity.TOP or Gravity.END
                    }
        })

        return binding.root
    }

    // MVP

    override fun getPresenter(): BaseContract.Presenter<NewsDetailsContract.View> {
        return presenter
    }

    override fun showLoadingIndicator() {
        binding.loadingIndicator.visibility = View.VISIBLE

        binding.fab.visibility = View.GONE
        setMenuVisibility(false)
    }

    override fun showArticle(newsArticle: NewsArticle) {
        binding.loadingIndicator.visibility = View.GONE

        setMenuVisibility(true)
        binding.fab.visibility = View.VISIBLE
        binding.newsArticle = newsArticle
    }

    override fun showError(errorMessage: String) {
        binding.loadingIndicator.visibility = View.GONE
        binding.appBar.setExpanded(false)

        binding.viewErrorMessage.visibility = View.VISIBLE
    }

    override fun notifyLinkCopiedToClipboard() {
        Snackbar.make(requireView(), getString(R.string.message_link_copied_to_clipboard), Snackbar.LENGTH_LONG).show()
    }

    override fun openLinkWithExternalBrowser(pageUri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, pageUri)

        startActivity(intent)
    }

    // Options Menu Configuration

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_news_details, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_copy_link -> {
                presenter.onLinkCopyActionSelected()
                true
            }
            R.id.action_open_with -> {
                presenter.onOpenWithActionSelected()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Navigation UI Configuration

    override fun getToolbar(): Toolbar {
        return binding.toolbar
    }
}