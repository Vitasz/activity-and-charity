package com.monke.begit.ui.mainFeature.activityFeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.monke.begit.App
import com.monke.begit.R
import com.monke.begit.databinding.FragmentSelectActivityBinding
import javax.inject.Inject

class SelectActivityFragment : Fragment() {


    @Inject
    lateinit var factory: SelectActivityViewModel.Factory
    private val viewModel: SelectActivityViewModel by viewModels { factory }

    private var binding: FragmentSelectActivityBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectActivityBinding.inflate(inflater, container, false)
        (activity?.application as? App)?.appComponent?.inject(this)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupActivitiesMenu()
        setupPlayButton()
    }


    private fun setupActivitiesMenu() {
        val menu = binding?.menu
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_sport_acivity,
            viewModel.sportActivities.map { it.name })
        (menu?.editText as? AutoCompleteTextView)?.setText(viewModel.selectedActivity.name)
        (menu?.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (menu?.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, index, l ->
            viewModel.selectedActivity = viewModel.sportActivities[index]
        }
    }

    private fun setupPlayButton() {
        binding?.btnStart?.setOnClickListener {
            viewModel.saveSportActivity()
            it.findNavController()
                .navigate(R.id.action_selectActivityFragment_to_trackActivityFragment)
        }
    }




}