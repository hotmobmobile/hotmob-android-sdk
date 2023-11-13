package com.hotmob.sdk.hotmobsdkshowcase

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaItem.AdsConfiguration
import androidx.media3.common.Player
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.ima.ImaAdsLoader
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.MediaSourceFactory
import com.hotmob.sdk.ad.HotmobAdDeepLinkListener
import com.hotmob.sdk.ad.HotmobAdEvent
import com.hotmob.sdk.ad.HotmobAdListener
import com.hotmob.sdk.hotmobsdkshowcase.databinding.FragmentVastShowcaseBinding
import com.hotmob.sdk.module.vast.HotmobVASTUrlBuilder


class VastShowcaseFragment : androidx.fragment.app.Fragment(), View.OnClickListener, HotmobAdListener, HotmobAdDeepLinkListener,
Player.Listener
{
    private var _binding: FragmentVastShowcaseBinding? = null
    private val binding get() = _binding!!
    private lateinit var adsLoader: ImaAdsLoader
    private lateinit var player: ExoPlayer;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVastShowcaseBinding.inflate(inflater, container, false)


        return binding.root
    }

    @SuppressLint("UnsafeOptInUsageError")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            player = ExoPlayer.Builder(it).build()
            adsLoader = ImaAdsLoader.Builder(it).build()
        }

    }

    private fun releasePlayer() {
        adsLoader.setPlayer(null)
        binding.videoView.player = null
        player.release()
//        player = null
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun initializePlayer() {

        context?.let {
            // Set up the factory for media sources, passing the ads loader and ad view providers.
            val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(it)
            val mediaSourceFactory: MediaSourceFactory = DefaultMediaSourceFactory(dataSourceFactory)
                .setAdsLoaderProvider { adsLoader }
                .setAdViewProvider(binding.videoView)

            // Create an ExoPlayer and set it as the player for content and ads.
            player = ExoPlayer.Builder(it).setMediaSourceFactory(mediaSourceFactory).build()

            binding.videoView.player = player
            adsLoader.setPlayer(player)

            var adTagUri = Uri.parse("")
            HotmobVASTUrlBuilder(it, "hongkongmovie_android_trailer", 1).build { vast ->
                println(vast)
                adTagUri = Uri.parse(vast)
           }
            // Create the MediaItem to play, specifying the content URI and ad tag URI.
            val contentUri = Uri.parse("https://cdn.hot-mob.com/video/2022/5360-3.mp4")
            val mediaItem = MediaItem.Builder()
                .setUri(contentUri)
                .setAdsConfiguration(AdsConfiguration.Builder(adTagUri).build())
                .build()

            // Prepare the content and ad to be played with the SimpleExoPlayer.
            player.setMediaItem(mediaItem)
            player.prepare()
        }



        // Set PlayWhenReady. If true, content and ads will autoplay.
        player.playWhenReady = true
    }
    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    override fun onDeepLink(deepLink: String) {
        TODO("Not yet implemented")
    }

    override fun onAdEvent(adEvent: HotmobAdEvent) {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        super.onStart();
        initializePlayer();
        binding.videoView.onResume()
    }

    override fun onResume() {
        super.onResume()
        initializePlayer();
        binding.videoView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.videoView.onPause()
        releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        binding.videoView.onPause()
        releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        adsLoader.release()
    }

}