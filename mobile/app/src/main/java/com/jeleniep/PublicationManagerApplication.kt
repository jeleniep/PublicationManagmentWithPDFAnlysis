package com.jeleniep

import android.app.Application
import android.content.Context

class PublicationManagerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        var appContext: Context? = null
            private set
    }
}