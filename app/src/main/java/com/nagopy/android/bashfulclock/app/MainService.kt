package com.nagopy.android.bashfulclock.app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.nagopy.android.bashfulclock.OverlayClock
import com.nagopy.android.bashfulclock.R
import dagger.android.DaggerService
import timber.log.Timber
import javax.inject.Inject

class MainService : DaggerService() {
    override fun onBind(intent: Intent?): IBinder? = null

    @Inject
    lateinit var overlayClock: OverlayClock
    @Inject
    lateinit var notificationManager: NotificationManager

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Timber.d("onReceive action=%s", intent?.action)
            when (intent?.action) {
                Intent.ACTION_USER_PRESENT, ACTION_SHOW -> overlayClock.show()
                Intent.ACTION_SCREEN_OFF -> overlayClock.cancel()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate()")

        registerReceiver(receiver, IntentFilter(Intent.ACTION_USER_PRESENT).apply {
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(ACTION_SHOW)
        })

        val channelId =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createNotificationChannel()
                } else {
                    // If earlier version channel ID is not used
                    // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                    ""
                }

        var n = NotificationCompat.Builder(this, channelId)
                .setContentTitle(getText(R.string.app_name))
                .setSmallIcon(R.drawable.ic_stat_default)
                .setContentText(getText(R.string.notification_content_text))
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setContentIntent(PendingIntent.getActivity(this, 0,
                        Intent(this, SettingsActivity::class.java)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
                        PendingIntent.FLAG_UPDATE_CURRENT))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            n = n.setCategory(Notification.CATEGORY_SERVICE)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        }
        startForeground(1, n.build())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_SHOW) {
            overlayClock.show()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        overlayClock.cancel()
        super.onDestroy()
    }

    // https://stackoverflow.com/questions/47531742/startforeground-fail-after-upgrade-to-android-8-1
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): String {
        val id = NOTIFICATION_CHANNEL
        if (notificationManager.getNotificationChannel(id) == null) {
            val channelName = getText(R.string.notification_channel_name)
            val description = getString(R.string.notification_channel_description)

            val channel = NotificationChannel(id, channelName, NotificationManager.IMPORTANCE_NONE)
            channel.lightColor = Color.GREEN
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            channel.description = description
            notificationManager.createNotificationChannel(channel)
        }
        return id
    }

    companion object {
        private fun getIntent(context: Context) = Intent(context, MainService::class.java)

        fun start(context: Context) {
            Timber.d("startService")
            ContextCompat.startForegroundService(context, getIntent(context))
        }

        fun restart(context: Context) {
            context.stopService(getIntent(context))
            start(context)
        }

        fun show(context: Context) {
            context.sendBroadcast(Intent(ACTION_SHOW))
        }

        private const val NOTIFICATION_CHANNEL = "notificationChannel"
        private const val ACTION_SHOW = "com.nagopy.android.bashfulclock.app.MainService.ACTION_SHOW"
    }
}