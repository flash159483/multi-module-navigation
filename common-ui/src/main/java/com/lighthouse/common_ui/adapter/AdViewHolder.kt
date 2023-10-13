package com.lighthouse.common_ui.adapter

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import com.lighthouse.common_ui.databinding.NativeAdBinding

class AdViewHolder(
    private val binding: NativeAdBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        val context = binding.root.context
        val loader =
            AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110").forNativeAd {
                populateNativeAdView(it, binding.nativeAdView)
            }.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("TESTING ADS", adError.toString())
                }
            }).withNativeAdOptions(
                NativeAdOptions.Builder().build()
            ).build()
        val request = AdRequest.Builder().build()

        loader.loadAd(request)
    }

    private fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
        adView.headlineView = binding.primary
        adView.callToActionView = binding.cta
        adView.iconView = binding.icon
        adView.starRatingView = binding.ratingBar

        (adView.headlineView as TextView).text = nativeAd.headline

        if (nativeAd.callToAction == null) {
            adView.callToActionView!!.visibility = View.INVISIBLE
        } else {
            adView.callToActionView!!.visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }
        if (nativeAd.icon == null) {
            adView.iconView!!.visibility = View.GONE
        } else {
            (adView.iconView as ImageView).setImageDrawable(
                nativeAd.icon!!.drawable
            )
            adView.iconView!!.visibility = View.VISIBLE
        }
        if (nativeAd.starRating == null) {
            adView.starRatingView!!.visibility = View.INVISIBLE
        } else {
            (adView.starRatingView as RatingBar)
                .rating = nativeAd.starRating!!.toFloat()
            adView.starRatingView!!.visibility = View.VISIBLE
        }
        adView.setNativeAd(nativeAd)
    }
}