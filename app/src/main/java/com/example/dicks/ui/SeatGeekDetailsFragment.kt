package com.example.dicks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.abinav_dicks.R
import com.example.abinav_dicks.databinding.FragmentSeatGeekDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeatGeekDetailsFragment : Fragment() {
    lateinit var binding: FragmentSeatGeekDetailsBinding

    private val args: SeatGeekDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeatGeekDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        val itemSeatGeek = args.itemSeatGeek
        binding.apply {
            tvDetailsTitle.text = itemSeatGeek.title
            tvDetailsLocation.text = itemSeatGeek.location
            tvDetailsDate.text = itemSeatGeek.date.toReadableDate()
            Glide.with(imvDetailsLogo)
                .load(itemSeatGeek.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(imvDetailsLogo)
        }
    }
}