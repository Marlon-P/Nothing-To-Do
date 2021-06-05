package com.gmail.hitoridevelop.nothingtodo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.gmail.hitoridevelop.nothingtodo.R
import com.gmail.hitoridevelop.nothingtodo.databinding.FragmentShowActivityBinding




class ShowActivityFragment : Fragment() {


    lateinit var binding: FragmentShowActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentShowActivityBinding.inflate(layoutInflater)

        val result = arguments?.getString("bundleKey")


        binding.textView.text = result.toString()

        binding.suggestNewActivityBtn.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_showActivityFragment_to_suggestActivityFragment)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return binding.root
    }



}