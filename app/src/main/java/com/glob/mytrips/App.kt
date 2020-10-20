package com.glob.mytrips

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import com.glob.mytrips.app.DataInfoUser

class App: Application() {

    companion object {
        private val TAG = "${App::class.java.simpleName}"
        const val CHANNEL_ID = "ExampleServiceChannel"
//        companion object {
//            lateinit var instance: App
//                private set
//        }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        val userInfoSingleton = DataInfoUser.getInstance()
        Log.d(TAG, "onCreate: creating my Singleton: $userInfoSingleton")
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Example Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.let {
                it.createNotificationChannel(serviceChannel)
            }
        }
    }
}