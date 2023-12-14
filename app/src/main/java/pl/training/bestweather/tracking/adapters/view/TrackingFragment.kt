package pl.training.bestweather.tracking.adapters.view

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import pl.training.bestweather.R
import pl.training.bestweather.databinding.FragmentTrackingBinding

// https://android-developers.googleblog.com/2017/11/moving-past-googleapiclient_21.html
class TrackingFragment : Fragment() {

    private lateinit var binding: FragmentTrackingBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentTrackingBinding.inflate(layoutInflater)
        fusedLocationClient = getFusedLocationProviderClient(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.trackingMap) as SupportMapFragment
        mapFragment.getMapAsync(onMapReady)
    }

    private val onMapReady = OnMapReadyCallback {
        map = it
        requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
    }

    private val requestPermissionLauncher = registerForActivityResult(RequestPermission()) { isGranted ->
        if (isGranted) {
            configureMap()
        } else {
            Log.i("###", "User denied location access")
        }
    }

    @SuppressLint("MissingPermission")
    private fun configureMap() {
        map.isMyLocationEnabled = true
        map.uiSettings.isCompassEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
               Log.i("###", it.toString())
            }
    }

}