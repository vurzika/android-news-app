package com.onramp.vurzika.newsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.onramp.vurzika.newsapp.databinding.FragmentNewsListBinding

class NewsListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentNewsListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_list, container, false)

        return binding.root
    }
}