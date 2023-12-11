package pl.training.bestweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val tag = MainActivity::class.qualifiedName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button)
            .setOnClickListener(::sayHello)
        Log.i(tag, "### onCreate")
    }

    private fun sayHello(button: View) {
        Log.i(tag, "click")
        findViewById<TextView>(R.id.massage_text).text = getString(R.string.hello_android_message)
    }

    override fun onStart() {
        super.onStart()
        Log.i(tag, "### onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(tag, "### onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(tag, "### onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(tag, "### onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(tag, "### onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(tag, "### onRestart")
    }

}