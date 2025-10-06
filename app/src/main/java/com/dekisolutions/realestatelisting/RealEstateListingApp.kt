package com.dekisolutions.realestatelisting

import android.app.Application
import com.dekisolutions.realestatelisting.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RealEstateListingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RealEstateListingApp)
            modules(appModule)
        }
    }
}
