package pl.training.bestweather.profile.adapters.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import pl.training.bestweather.commons.RoundedTransformation
import pl.training.bestweather.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.get()
            .load("https://www.kindpng.com/picc/m/3-36825_and-art-default-profile-picture-png-transparent-png.png")
            .transform(RoundedTransformation(480, 0))
            .into(binding.profilePhoto)
    }

}