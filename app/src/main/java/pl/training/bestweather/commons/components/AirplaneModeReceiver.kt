package pl.training.bestweather.commons.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AirplaneModeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val state = intent?.getBooleanExtra("state", false) ?: return
        if (state) {
            Log.i("###", "Airplane mode is on")
        } else {
            Log.i("###", "Airplane mode is off")
        }
    }

}