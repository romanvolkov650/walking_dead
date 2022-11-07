package com.volkov.myapplication

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.volkov.myapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import okhttp3.internal.immutableListOf
import org.joda.time.DateTime
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private val player by lazy { ExoPlayer.Builder(this).build() }

    private val model by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.playerView.player = player

        lifecycleScope.launchWhenCreated {
            model.state.collectLatest {
                player.setMediaItem(MediaItem.fromUri(it.src))
                player.prepare()
                player.playWhenReady = true
                player.seekTo(EpisodePref.timePosition)
                player.setPlaybackSpeed(1.15f)
            }
        }

        lifecycleScope.launchWhenCreated {
            model.episodeState.collectLatest {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launchWhenCreated {
            model
                .tickerFlow(period = 5_000)
                .map { DateTime.now() }
                .onEach {
                    EpisodePref.timePosition = player.currentPosition
                }
                .launchIn(lifecycleScope)
        }
    }

    override fun onPause() {
        super.onPause()
        player.pause()
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        when {
            event.onBackPressed() -> finish()
            event.onRightPressed() -> {
                player.seekTo(player.currentPosition + 10_000)
            }
            event.onLeftPressed() -> {
                player.seekTo(player.currentPosition - 10_000)
            }
            event.onEnterPressed() -> {
                if (player.isPlaying) player.pause() else player.playWhenReady = true
            }
            event.onUpPressed() -> {
                model.getPrevEpisode()
            }
            event.onDownPressed() -> {
                model.getNextEpisode()
            }
        }
        return super.dispatchKeyEvent(event)
    }
}