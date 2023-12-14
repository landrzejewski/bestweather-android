package pl.training.bestweather.profile.adapters.view

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.provider.MediaStore.Images.Media.DATA
import android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
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

        Log.i("###", PreferenceManager.getDefaultSharedPreferences(requireContext())
            .getString("forecast_number_of_days", "Unknown").toString())
    }

    private fun init() {
        Picasso.get()
            .load("https://www.kindpng.com/picc/m/3-36825_and-art-default-profile-picture-png-transparent-png.png")
            .transform(RoundedTransformation(560, 0))
            .into(binding.profilePhoto)
    }

    private fun bind() {
        binding.profilePhoto.setOnClickListener {
            if (allPermissionsGranted()) showDialog() else requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
        }
    }

    private fun showDialog() {
        val options = arrayOf("Take photo", "Choose from gallery", "Cancel")
        AlertDialog.Builder(requireContext()).setItems(options) { dialog, which ->
            when (which) {
                0 -> startCamera()
                1 -> chooseFromGallery()
                else -> dialog.dismiss()
            }
        }
            .setTitle("Select option")
            .show()
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        checkSelfPermission(requireContext(), it) == PERMISSION_GRANTED
    }

    private val requestPermissionLauncher =
        registerForActivityResult(RequestMultiplePermissions()) { permissions ->
            val permissionGranted = permissions.entries.any { it.key in REQUIRED_PERMISSIONS && it.value }
            if (permissionGranted) {
                showDialog()
            } else {
                Toast.makeText(requireContext(), "Permission request denied", LENGTH_SHORT).show()
            }
        }

    private fun startCamera() {
        val takePictureIntent = Intent(ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    private fun chooseFromGallery() {
        val chooseFromGalleryIntent = Intent(ACTION_PICK, EXTERNAL_CONTENT_URI)
        startActivityForResult(chooseFromGalleryIntent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                if (resultCode == RESULT_OK && data != null) {
                    data.extras?.let {
                        val bitmap = it["data"] as Bitmap?
                        binding.profilePhoto.setImageBitmap(bitmap)
                    }
                }
            }

            REQUEST_IMAGE_PICK -> {
                data?.data?.let { uri ->
                    requireActivity().contentResolver.query(uri, arrayOf(DATA), null, null, null)
                        ?.let {
                            it.moveToFirst()
                            val columnIdx = it.getColumnIndex(DATA)
                            val path = it.getString(columnIdx)
                            val bitmap = BitmapFactory.decodeFile(path)
                            binding.profilePhoto.setImageBitmap(bitmap)
                            it.close()
                        }
                }
            }
        }
    }

    companion object {

        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_IMAGE_PICK = 2
        private val REQUIRED_PERMISSIONS = mutableListOf(READ_EXTERNAL_STORAGE, CAMERA).toTypedArray()

    }

}