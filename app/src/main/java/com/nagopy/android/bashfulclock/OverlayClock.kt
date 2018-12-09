package com.nagopy.android.bashfulclock

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.*
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.core.widget.TextViewCompat
import com.nagopy.android.bashfulclock.databinding.OverlayClockBinding
import com.nagopy.android.overlayviewmanager.DraggableOnTouchListener
import com.nagopy.android.overlayviewmanager.OverlayView
import com.nagopy.android.overlayviewmanager.OverlayViewManager
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToLong

class OverlayClock @Inject constructor(private val userSettings: UserSettings
                                       , context: Context
                                       , overlayViewManager: OverlayViewManager
                                       , private val dateFormatter: DateFormatter) {

    private val binding: OverlayClockBinding = OverlayClockBinding.inflate(LayoutInflater.from(context)).apply {
        when (userSettings.textSize) {
            userSettings.textSizeSmall ->
                TextViewCompat.setTextAppearance(this.clockTextView, R.style.TextAppearance_AppCompat_Small)
            userSettings.textSizeMedium ->
                TextViewCompat.setTextAppearance(this.clockTextView, R.style.TextAppearance_AppCompat_Medium)
            userSettings.textSizeLarge ->
                TextViewCompat.setTextAppearance(this.clockTextView, R.style.TextAppearance_AppCompat_Large)
            userSettings.textSizeExtraLarge -> {
                TextViewCompat.setTextAppearance(this.clockTextView, R.style.TextAppearance_AppCompat_Large)
                // getTextSize = px
                // setTextSize = sp(default)
                // this.clockTextView.textSize *= 1.1f NG!
                this.clockTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.clockTextView.textSize * 1.1f)
            }
        }
        this.clockTextView.setTextColor(Color.WHITE)
    }
    private val clockView: OverlayView<View> = overlayViewManager.newOverlayView(binding.root).apply {
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
        setAlpha(0.8f)
        setGravity(Gravity.TOP)
        setTouchable(true)
        setDraggable(true, object : DraggableOnTouchListener<View>(this) {
            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(view: View?, ev: MotionEvent?): Boolean {
                val ret = super.onTouch(view, ev)
                when (ev?.actionMasked) {
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        reserveFadeout()
                        saveYPosition()
                    }
                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> cancelFadeout()
                }
                return ret
            }
        })
    }

    private val alphaFadeout: Animation = AlphaAnimation(1.0f, 0.0f).apply {
        duration = 1000
        fillAfter = true
    }

    fun show() {
        Timber.v("show")

        clockView.y = userSettings.y
        clockView.show()
        startTimer()
        reserveFadeout()
    }

    fun cancel() {
        Timber.v("cancel")

        clockView.hide()
        cancelTimer()
        cancelFadeout()
    }


    private var timerJob: Job? = null
    private fun startTimer() {
        Timber.v("startTimer")
        if (timerJob != null) {
            Timber.v("Already started")
            return
        }

        timerJob = GlobalScope.launch(Dispatchers.Main) {
            while (isActive) {
                Timber.d("Timer job")
                binding.clockTextView.text = dateFormatter.format(Date())
                delay(1000)
            }
        }
    }

    private fun cancelTimer() {
        Timber.v("cancelTimer")
        timerJob?.cancel()
        timerJob = null
    }

    private var fadeoutJob: Job? = null
    private fun reserveFadeout() {
        Timber.v("reserveFadeout")

        if (!userSettings.fadeOut) {
            return
        }

        fadeoutJob = GlobalScope.launch(Dispatchers.Main) {
            delay((userSettings.duration * 1000L).roundToLong())
            if (isActive) {
                binding.clockTextView.startAnimation(alphaFadeout)
                delay(1000)
                if (isActive) {
                    cancelTimer()
                    clockView.hide()
                }
            }
        }
    }

    private fun cancelFadeout() {
        Timber.v("cancelFadeout")

        fadeoutJob?.let {
            it.cancel()
            binding.clockTextView.clearAnimation()
            fadeoutJob = null
        }
    }

    private fun saveYPosition() {
        userSettings.y = clockView.y
    }
}
