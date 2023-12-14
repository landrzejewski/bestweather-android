package pl.training.bestweather.tracking.adapters.view

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.os.Looper.getMainLooper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.AndroidEntryPoint
import pl.training.bestweather.R
import pl.training.bestweather.databinding.FragmentTrackingBinding
import pl.training.bestweather.tracking.domain.Position

@AndroidEntryPoint
class TrackingFragment : Fragment() {

    private lateinit var binding: FragmentTrackingBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap
    private val viewModel: TrackingViewModel by activityViewModels()
    private val locationRequest = LocationRequest.Builder(1_000)
        .setPriority(PRIORITY_HIGH_ACCURACY)
        .setMinUpdateDistanceMeters(5F)
        .build()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentTrackingBinding.inflate(layoutInflater)
        fusedLocationClient = getFusedLocationProviderClient(requireActivity())
        viewModel.start()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.trackingMap) as SupportMapFragment
        mapFragment.getMapAsync(onMapReady)
    }

    override fun onResume() {
        super.onResume()
        requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(onLocationUpdate)
    }

    private val onMapReady = OnMapReadyCallback {
        map = it
    }

    private val onLocationUpdate =  object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let {
                moveCamera(it)
                val distance = if (viewModel.lastLocation == null) 0F else it.distanceTo(viewModel.lastLocation!!)
                val position = Position(it.longitude, it.latitude)
                viewModel.onLocationChange(position, it.speed, distance)
                drawRoute(it)
                updateStats()
                viewModel.lastLocation = it
            }
        }
    }

    private fun updateStats() {
        binding.duration.text = viewModel.duration
        binding.speed.text = viewModel.speed
        binding.pace.text = viewModel.pace
    }

    @SuppressLint("MissingPermission")
    private val requestPermissionLauncher = registerForActivityResult(RequestPermission()) { isGranted ->
        if (isGranted) {
            configureMap()
            fusedLocationClient.requestLocationUpdates(locationRequest, onLocationUpdate, getMainLooper())
        } else {
            Log.i("###", "User denied location access")
        }
    }

    @SuppressLint("MissingPermission")
    private fun configureMap() {
        map.isMyLocationEnabled = true
        map.uiSettings.isCompassEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(::moveCamera)
    }

    private fun moveCamera(location: Location) {
        val position = LatLng(location.latitude, location.longitude)
        val positionUpdate = newLatLngZoom(position, CAMERA_ZOOM)
        map.animateCamera(positionUpdate)
    }
    private fun drawRoute(location: Location) {
        viewModel.lastLocation?.let { lastLocation ->
            val options = PolylineOptions()
            options.color(R.color.main)
            options.width(10F)
            options.add(LatLng(location.latitude, location.longitude))
            options.add(LatLng(lastLocation.latitude, lastLocation.longitude))
            map.addPolyline(options)
        }
    }


    companion object {

        const val CAMERA_ZOOM = 16F

    }

}