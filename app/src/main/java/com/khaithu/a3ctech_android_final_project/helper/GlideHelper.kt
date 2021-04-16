package com.khaithu.a3ctech_android_final_project.helper

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation

object GlideHelper {
    fun loadImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).load(url).into(imageView)
    }

    fun loadImage(view: View, url: String, imageView: ImageView) {
        Glide.with(view).load(url).into(imageView)
    }

    fun loadAndBlurImage(context: Context, url: String, imageView: ImageView, radiusBlur: Int) {
        Glide.with(context).load(url)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(radiusBlur)))
            .into(imageView)
    }
}
