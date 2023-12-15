package pl.training.bestweather

import android.content.ContentValues
import android.content.Intent
import android.content.Intent.ACTION_AIRPLANE_MODE_CHANGED
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment.*
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
import pl.training.bestweather.commons.components.CounterService
import pl.training.bestweather.commons.components.UsersProvider
import pl.training.bestweather.commons.components.UsersProvider.Companion.CONTENT_URI
import pl.training.bestweather.databinding.ActivityMainBinding
import java.io.File
import java.nio.file.Files

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val airplaneReceiver = AirplaneModeReceiver()

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

        val intent = Intent(this, CounterService::class.java)
        //startService(intent)

        // App storage
        // val file = File(this.filesDir, "data.txt")

        openFileOutput("data.txt", MODE_PRIVATE).use {
            it.write("To jest Android".toByteArray())
        }

        openFileInput("data.txt").bufferedReader().forEachLine {
            Log.i("###", "data.txt ($filesDir): $it")
        }

        // External storage
        if (externalStorageExists() && externalStorageIsWriteable()) {
            val path = File(getExternalFilesDir("Data"), "app_data.txt").toPath()
            Files.write(path, "To jest Android".toByteArray())
            Files.readAllLines(path).forEach {
                Log.i("###", "app_data.txt ($path): $it")
            }
        }

    }

    private fun externalStorageExists() = MEDIA_MOUNTED == getExternalStorageState()

    private fun externalStorageIsWriteable() = MEDIA_MOUNTED_READ_ONLY != getExternalStorageState()

    override fun onStop() {
        super.onStop()
        unregisterReceiver(airplaneReceiver)
        val intent = Intent(this, CounterService::class.java)
        stopService(intent)
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