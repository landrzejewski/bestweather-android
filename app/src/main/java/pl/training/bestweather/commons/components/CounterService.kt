package pl.training.bestweather.commons.components

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.os.IBinder
import android.util.Log
import java.util.Timer
import java.util.TimerTask

class CounterService : Service() {

    private var counter = 0
    private lateinit var timer: Timer
    private lateinit var timerTask: TimerTask

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Log.d("###", "Example service on create ")
        start()
    }

    private fun start() {
        val channelId = "training"
        val channelName = "exampleService"
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE)
        channel.lightColor = Color.BLUE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
        val notificationBuilder = Notification.Builder(this, channelId)
        val notification = notificationBuilder.setOngoing(true)
            .setContentTitle("App is running in background")
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(2, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        startTimer()
        return START_STICKY
    }

    private fun startTimer() {
        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                Log.d("###", "Count " + counter++)
            }
        }
        timer.schedule(timerTask, 1_000, 1_000)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

}