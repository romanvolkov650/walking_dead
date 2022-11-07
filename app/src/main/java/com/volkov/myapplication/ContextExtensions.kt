package com.volkov.myapplication

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.os.SystemClock
import android.util.DisplayMetrics
import android.view.KeyEvent
import android.view.WindowManager

fun KeyEvent.onBackPressed() =
    this.action == KeyEvent.ACTION_UP && (SystemClock.uptimeMillis() - this.downTime) < 350 && (this.keyCode == KeyEvent.KEYCODE_BACK || this.keyCode == KeyEvent.KEYCODE_DEL)

fun KeyEvent.onLongBackPressed() =
    this.action == KeyEvent.ACTION_DOWN && this.isLongPress && (this.keyCode == KeyEvent.KEYCODE_BACK || this.keyCode == KeyEvent.KEYCODE_DEL)

fun KeyEvent.onLeftPressed() =
    this.action == KeyEvent.ACTION_DOWN && (this.keyCode == KeyEvent.KEYCODE_DPAD_LEFT)

fun KeyEvent.onRightPressed() =
    this.action == KeyEvent.ACTION_DOWN && (this.keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)

fun KeyEvent.onPlayPause() =
    this.action == KeyEvent.ACTION_DOWN && (this.keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE)

fun KeyEvent.onUpPressed() =
    this.action == KeyEvent.ACTION_DOWN && this.keyCode == KeyEvent.KEYCODE_DPAD_UP

fun KeyEvent.onDownPressed() =
    this.action == KeyEvent.ACTION_DOWN && this.keyCode == KeyEvent.KEYCODE_DPAD_DOWN

fun KeyEvent.onPageUp() =
    this.action == KeyEvent.ACTION_DOWN && this.keyCode == KeyEvent.KEYCODE_PAGE_UP

fun KeyEvent.onPageDown() =
    this.action == KeyEvent.ACTION_DOWN && this.keyCode == KeyEvent.KEYCODE_PAGE_DOWN

fun KeyEvent.onEnterPressed() =
    this.action == KeyEvent.ACTION_UP && (SystemClock.uptimeMillis() - this.downTime) < 350 && (this.keyCode == KeyEvent.KEYCODE_DPAD_CENTER || this.keyCode == KeyEvent.KEYCODE_ENTER)

fun KeyEvent.onLongEnterPressed() =
    this.action == KeyEvent.ACTION_DOWN && this.isLongPress && (this.keyCode == KeyEvent.KEYCODE_DPAD_CENTER || this.keyCode == KeyEvent.KEYCODE_ENTER)

fun Context.screenWidth(): Int {
    val dm = DisplayMetrics()
    (this.getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(dm)
    return dm.widthPixels
}

fun Context.screenHeight(): Int {
    val dm = DisplayMetrics()
    (this.getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(dm)
    return dm.heightPixels
}