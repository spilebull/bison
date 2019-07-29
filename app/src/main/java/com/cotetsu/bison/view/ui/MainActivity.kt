package com.cotetsu.bison.view.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.format.DateFormat
import com.cotetsu.bison.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.timeText
import java.util.Date

/**
 * 主処理
 *
 * @date 2019-07-29
 * @author spilebull
 */
class MainActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(timeTicker, IntentFilter(Intent.ACTION_TIME_TICK))
        updateDisplayTime()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(timeTicker)
    }

    private fun updateDisplayTime() {
        timeText.text = DateFormat.getTimeFormat(this).format(Date())
    }

    private val timeTicker = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Intent.ACTION_TIME_TICK) {
                updateDisplayTime()
            }
        }
    }
}
