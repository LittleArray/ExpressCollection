package top.ffshaozi.expresscollection

import android.app.Application
import android.content.Context




// App.kt
class App : Application() {

    companion object {
        lateinit var instance: App
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}

