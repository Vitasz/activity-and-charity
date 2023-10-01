package com.monke.begit.ui.mainFeature.activityFeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.monke.begit.App
import com.monke.begit.R
import com.monke.begit.databinding.FragmentActivitiesBinding
import com.monke.begit.ui.mainFeature.MainFragment
import javax.inject.Inject

class ActivitiesFragment : Fragment() {

    @Inject
    lateinit var factory: ActivitiesViewModel.Factory

    private val viewModel: ActivitiesViewModel by viewModels { factory }

    private var binding: FragmentActivitiesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActivitiesBinding.inflate(inflater, container, false)
        (activity?.application as App).appComponent.inject(this)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnStartSport?.setOnClickListener {
            (parentFragment?.parentFragment as? MainFragment)
                ?.mainNavController?.navigate(R.id.action_mainFragment_to_selectActivityFragment)
        }

        val adapter = SportActivityRW(viewModel.activities)
        binding?.listSport?.adapter = adapter
        binding?.listSport?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }


}