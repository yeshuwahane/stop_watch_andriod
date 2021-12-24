package com.alien.stopwatch

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alien.stopwatch.databinding.ActivityMainBinding
import java.lang.String
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var seconds: Int = 0
    private var is_running = false
    private var was_running = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds")
            is_running = savedInstanceState.getBoolean("running")
            was_running = savedInstanceState.getBoolean("wasRunning")

        }


        binding.timer.setOnClickListener {
            if (!is_running) {
                onClickStart(binding.timer)
                runTimer()
            } else {
                onClickStop(binding.timer)
            }
        }

        binding.resetButton.setOnClickListener {
            onClickReset(binding.timer)
        }
    }

    override fun onSaveInstanceState(
        savedInstanceState: Bundle
    ) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState
            .putInt("seconds", seconds)
        savedInstanceState
            .putBoolean("running", is_running)
        savedInstanceState
            .putBoolean("wasRunning", was_running)
    }

    override fun onPause() {
        super.onPause()
        was_running = is_running
        is_running = false
    }


    override fun onResume() {
        super.onResume()
        if (was_running) {
            is_running = true
        }
    }

    fun onClickStart(view: View?) {
        is_running = true
    }

    fun onClickStop(view: View?) {
        is_running = false
    }

    fun onClickReset(view: View?) {
        is_running = false
        seconds = 0
    }

    private fun runTimer() {

        val timeView = binding.timer
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                val hours: Int = seconds / 3600
                val minutes: Int = seconds % 3600 / 60
                val secs: Int = seconds % 60
                val time = String
                    .format(
                        Locale.getDefault(),
                        "%d:%02d:%02d", hours,
                        minutes, secs
                    )
                timeView.text = time


                if (is_running) {
                    seconds++
                }


                handler.postDelayed(this, 1000)
            }
        })
    }


}