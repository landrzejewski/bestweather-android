package pl.training.bestweather.profile.adapters.view

import android.Manifest.permission.CAMERA
import android.app.AlertDialog
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import pl.training.bestweather.commons.RoundedTransformation
import pl.training.bestweather.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        bind()
    }

    private fun init() {
        Picasso.get()
            .load("https://www.kindpng.com/picc/m/3-36825_and-art-default-profile-picture-png-transparent-png.png")
            .transform(RoundedTransformation(560, 0))
            .into(binding.profilePhoto)
    }

    private fun bind() {
        binding.profilePhoto.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val options = arrayOf("Take photo", "Choose from gallery", "Cancel")
        AlertDialog.Builder(requireContext()).setItems(options) { dialog, which ->
            when (which) {
                0 -> takePhoto()
                1 -> Log.i("###", "Gallery")
                else -> dialog.dismiss()
            }
        }
            .setTitle("Select option")
            .show()
    }

    private fun takePhoto() = if (allPermissionsGranted()) startCamera() else
        requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        checkSelfPermission(requireContext(), it) == PERMISSION_GRANTED
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && !it.value)
                    permissionGranted = false
            }
            if (!permissionGranted) {
                Toast.makeText(requireContext(), "Permission request denied", LENGTH_SHORT).show()
            } else {
                startCamera()
            }
        }

    private fun startCamera() {

    }

    companion object {

        private val REQUIRED_PERMISSIONS = mutableListOf(CAMERA).toTypedArray()

    }

}