package pl.training.bestweather.commons.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

class Restarter : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("###", "Service tried to stop")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(Intent(context, ExampleService::class.java))
        } else {
            context.startService(Intent(context, ExampleService::class.java))
        }
    }

}