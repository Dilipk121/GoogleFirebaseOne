package com.example.googlefirebaseone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.googlefirebaseone.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding  get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //_binding should inflate here
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        binding.addBtn.setOnClickListener(){

            findNavController().navigate(R.id.action_homeFragment_to_addFragment)

        }



        return binding.root
    }


}