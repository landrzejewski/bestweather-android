package pl.training.bestweather

import android.app.ActivityManager
import android.content.ContentValues
import android.content.Intent
import android.content.Intent.ACTION_AIRPLANE_MODE_CHANGED
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import pl.training.bestweather.commons.components.AirplaneModeReceiver
import pl.training.bestweather.commons.components.UsersProvider
import pl.training.bestweather.commons.components.UsersProvider.Companion.CONTENT_URI
import pl.training.bestweather.databinding.ActivityMainBinding
import pl.training.bestweather.commons.components.ExampleService

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val airplaneReceiver = AirplaneModeReceiver()
    private lateinit var exampleService: ExampleService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        val bottomNavigationBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationBar.setupWithNavController(navController)

        // Components
        IntentFilter(ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(airplaneReceiver, it, RECEIVER_EXPORTED)
        }

        val values = ContentValues()
        values.put(UsersProvider.NAME_COLUMN, "Jan Kowalski")

        val uri = contentResolver.insert(CONTENT_URI, values)
        Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show()

        Log.i("###", uri.toString())
        contentResolver.query(Uri.parse("content://pl.training.bestweather.commons.components.UsersProvider/users"), null, null, null, null)?.let {
            while (it.moveToNext()) {
                val idColumnIndex = it.getColumnIndex(UsersProvider.ID_COLUMN)
                val nameColumnIndex = it.getColumnIndex(UsersProvider.NAME_COLUMN)
                val id = it.getString(idColumnIndex)
                val name = it.getString(nameColumnIndex)
                Log.i("###", "$id:$name")
            }
            it.close()
        }

        exampleService = ExampleService()
        val intent = Intent(this, exampleService::class.java)
        if (!isServiceRunning(ExampleService::class.java)) {
            startService(intent)
        }
    }

    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                Log.i("###", "Service is running")
                return true
            }
        }
        Log.i("###", "Service is not running")
        return false
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(airplaneReceiver)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.SettingsFragment -> {
                findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.SettingsFragment)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}