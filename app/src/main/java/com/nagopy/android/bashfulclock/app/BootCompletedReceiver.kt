package com.nagopy.android.bashfulclock.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.nagopy.android.overlayviewmanager.OverlayViewManager
import dagger.android.AndroidInjection
import javax.inject.Inject

class BootCompletedReceiver : BroadcastReceiver() {

    @Inject
    lateinit var overlayViewManager: OverlayViewManager


    override fun onReceive(context: Context?, intent: Intent?) {
        AndroidInjection.inject(this, context)

        if (overlayViewManager.canDrawOverlays()) {
            MainService.start(context!!)
        }
    }

}
