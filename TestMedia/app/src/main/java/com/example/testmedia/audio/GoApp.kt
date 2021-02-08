package com.example.testmedia.audio

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.testmedia.audio.helpers.ThemeHelper

val goPreferences: GoPreferences by lazy {
    GoApp.prefs
}

class GoApp : Application() {

    companion object {
        lateinit var prefs: GoPreferences
    }

    override fun onCreate() {
        super.onCreate()
        prefs = GoPreferences(applicationContext)
        AppCompatDelegate.setDefaultNightMode(ThemeHelper.getDefaultNightMode(applicationContext))
    }
}
