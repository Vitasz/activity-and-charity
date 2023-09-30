package com.monke.begit

import android.app.Application
import com.monke.begit.di.DaggerAppComponent

class App: Application() {

    val appComponent = DaggerAppComponent.builder().context(this).build()
}