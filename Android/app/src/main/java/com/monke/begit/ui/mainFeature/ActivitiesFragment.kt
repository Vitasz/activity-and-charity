package com.monke.begit.ui.mainFeature

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.monke.begit.MainActivity
import com.monke.begit.R
import com.monke.begit.databinding.FragmentActivitiesBinding

class ActivitiesFragment : Fragment() {


    private lateinit var viewModel: ActivitiesViewModel

    private var binding: FragmentActivitiesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActivitiesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnStartSport?.setOnClickListener {
            (parentFragment?.parentFragment as? MainFragment)
                ?.mainNavController?.navigate(R.id.action_mainFragment_to_selectActivityFragment)
        }
    }


}