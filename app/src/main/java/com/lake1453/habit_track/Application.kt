package com.lake1453.habit_track

import android.app.Application
import android.util.Log
import androidx.core.provider.FontRequest
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //adding Emojis
        val config = BundledEmojiCompatConfig(this)
        EmojiCompat.init(config)
    }
}