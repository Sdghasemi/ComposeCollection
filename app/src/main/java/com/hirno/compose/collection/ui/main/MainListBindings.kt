package com.hirno.compose.collection.ui.main

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hirno.compose.collection.model.collection.CollectionResponseModel
import com.hirno.compose.collection.utils.cast


/**
 * [BindingAdapter]s for the [CollectionResponseModel]s list.
 */
@BindingAdapter("app:model")
fun setModel(listView: RecyclerView, model: CollectionResponseModel?) {
    model?.let {
        (listView.adapter as MainAdapter).submitList(model.objects)
    }
}

@BindingAdapter("app:imageUrl")
fun loadImage(imageView: ImageView, url: String) {
//    Glide.with(imageView)
//        .load(url)
//        .placeholder(R.color.colorSurface)
//        .transition(DrawableTransitionOptions.withCrossFade())
//        .into(imageView)
}
@BindingAdapter("app:layout_constraintDimensionRatio")
fun setRatio(view: View, ratio: String) = with(view) {
    layoutParams.cast { it as ConstraintLayout.LayoutParams }?.apply {
        dimensionRatio = ratio
    }?.also {
        layoutParams = it
    }
}
@BindingAdapter("android:text")
fun setText(textView: TextView, text: Any?) = with(textView) {
    when (text) {
        is Int -> setText(text)
        is String? -> setText(text)
    }
}