package com.onramp.vurzika.newsapp.ui.base

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

/**
 * This file provides set of binding adapters used by data-binding
 */

@BindingAdapter("dividerOffset")
fun setDividerOffset(view: View, dimen: Float) {
    // custom binding adapter to convert "float" dimension to "int" offsets
    view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
        marginStart = dimen.toInt()
    }
}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    url?.let {
        Picasso.get()
                .load(it)
                .fit()
                .centerInside()
                .into(imageView)
    }
}